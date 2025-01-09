package br.edu.ifpb.pweb2.flashg.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.service.PhotographerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;

@Controller
@RequestMapping("/facade")
public class FacadeController {

    @Autowired
    private FacadeService facadeService;

    @RequestMapping(value = "/seachPhotographers", method = RequestMethod.GET)
    public ModelAndView searchPhotographers(ModelAndView mav){
        mav.setViewName("application/findPhotographers");
        return mav;
    }


    @RequestMapping(value = "/findPhotographers", method = RequestMethod.GET)
    public ModelAndView getAllPhotographerStartingWith(ModelAndView mav, @RequestParam("username") String username){
        List<Photographer> Photographers = facadeService.findByUsernameStartingWith(username);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", Photographers);
        mav.addObject("resultados", "Resultados para: "+ username); 
        return mav;
    }

    @RequestMapping(value = "/showProfile/{id}", method = RequestMethod.GET)
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id){
        mav.setViewName("application/showProfilePhotographer");
        Photographer photographer = facadeService.findByIdPhotographer(id);
        mav.addObject("photographer", photographer);
        return mav;
    }

    // @RequestMapping(value = "/listPhotographerFollowing", method = RequestMethod.GET)
    // public ModelAndView listPhotographerFollowing(ModelAndView mav){
    //     mav.setViewName("application/listPhotographerFollowing");
    //     List<Photographer> seguidos = facadeService.findAllFollowing(1L);
    //     mav.addObject("seguidos", seguidos);
    //     return mav;
    // }

    @RequestMapping(value = "/follow/{id}/{id2}", method = RequestMethod.POST)
    public ModelAndView follow (ModelAndView mav , @PathVariable("id") Long id, @PathVariable("id2") Long id2){
        facadeService.follow(id, id2);
        mav.setViewName("application/listPhotographerFollowing");
        List<Photographer> photographes = facadeService.findAll();
        mav.addObject("photographers", photographes);
        return mav;
    }

    @RequestMapping(value = "/listPhotographerFollowing/{id}", method = RequestMethod.GET)
    public ModelAndView listAllFollowing(ModelAndView mav, @PathVariable("id") Long id ){ 
        List<Photographer> seguidos = facadeService.findAllFollowing(id);
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadPage(ModelAndView mav) {
        mav.addObject("photo", new Photo());
        mav.setViewName("application/uploadPhotos");
        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(ModelAndView mav, HttpSession session, @RequestParam("image") MultipartFile file, @ModelAttribute("photo")Photo photo) throws Exception {
        if (file.isEmpty()) {
            mav.setViewName("application/uploadPhotos");
            return mav;
        }
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.uploadPhoto(loggedPhotographer.getId(),photo,file);
        mav.setViewName("application/uploadPhotos");
        return mav;
    }

    @RequestMapping(value = "/myPhotos", method = RequestMethod.GET)
    public ModelAndView showPhotographerPhotos(ModelAndView mav,HttpSession session) {
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<String> photos = facadeService.showPhotos(loggedPhotographer.getId());
        mav.setViewName("application/myPhotos");
        mav.addObject("photographer", loggedPhotographer);
        mav.addObject("photos", photos);
        return mav;
    }

}
