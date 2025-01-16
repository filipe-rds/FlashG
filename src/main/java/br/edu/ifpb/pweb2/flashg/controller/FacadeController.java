package br.edu.ifpb.pweb2.flashg.controller;

import java.util.List;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class FacadeController {

    @Autowired
    private FacadeService facadeService;

    @GetMapping(value = "")
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        mav.setViewName("redirect:/home");
        return mav;
    }

    @GetMapping(value = "/home")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("home");
        return mav;
    }

    @GetMapping(value = "/searchPhotographers")
    public ModelAndView searchPhotographers(ModelAndView mav) {
        mav.setViewName("application/findPhotographers");
        return mav;
    }

    @GetMapping(value = "/findPhotographers")
    public ModelAndView getAllPhotographerStartingWith(ModelAndView mav, @RequestParam("username") String username, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findByUsernameStartingWith(username);
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", photographers);
        mav.addObject("resultados", "Resultados para: " + username);
        return mav;
    }

    @GetMapping(value = "/showProfile/{id}")
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        Photographer searchedPhotographer = facadeService.findByIdPhotographer(id);
        String status = facadeService.checkFollowStatus(loggedPhotographer, searchedPhotographer);
        List<Photo> photos = facadeService.showPhotos(searchedPhotographer.getId());
        mav.setViewName("application/showProfilePhotographer");
        mav.addObject("status", status);
        mav.addObject("photographer", searchedPhotographer);
        mav.addObject("photos", photos);
        return mav;
    }

    @GetMapping(value = "/myProfile")
    public ModelAndView myProfile(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photo> photos = facadeService.showPhotos(loggedPhotographer.getId());
        Photographer myProfile = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        session.setAttribute("loggedPhotographer", myProfile);
        mav.setViewName("application/myProfilePhotographer");
        mav.addObject("photographer", myProfile);
        mav.addObject("photos", photos);
        return mav;
    }

    @PostMapping(value = "/followAction")
    public ModelAndView followAction(ModelAndView mav, @RequestParam("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        facadeService.handleFollowAction(loggedPhotographer.getId(), id);
        Photographer updatedLoggedPhotographer = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        session.setAttribute("loggedPhotographer", updatedLoggedPhotographer);
        mav.setViewName("redirect:/showProfile/" + id);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowing")
    public ModelAndView listAllFollowing(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> seguidos = facadeService.findAllFollowing(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowers")
    public ModelAndView listAllFollowers(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> seguidores = facadeService.findAllFollowers(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowers");
        mav.addObject("seguidores", seguidores);
        return mav;
    }

    @GetMapping(value = "/showAllPhotographers")
    public ModelAndView listAllPhotographer(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findAllPhotographers();
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.addObject("photographers", photographers);
        mav.setViewName("application/showAllPhotographers");
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        facadeService.logoutPhotographer(session);
        mav.setViewName("redirect:/auth/signin");
        return mav;
    } 

    @PostMapping(value = "/blockAction")
    public ModelAndView blockAction(ModelAndView mav, @RequestParam("id") Long id) {
        //facadeService.handleBlockAction(loggedPhotographer.getId(), id);
        //session.setAttribute("loggedPhotographer", updatedLoggedPhotographer);
        facadeService.handleBlockAction(id);
        mav.setViewName("redirect:/showAllPhotographers");
        return mav;
    }

    @GetMapping(value = "/uploadPhotos")
    public ModelAndView uploadPage(ModelAndView mav) {
        mav.addObject("photo", new Photo());
        mav.setViewName("application/uploadPhotos");
        return mav;
    }

    @PostMapping(value = "/uploadPhotos")
    public ModelAndView handleFileUpload(ModelAndView mav, HttpSession session, @RequestParam("image") MultipartFile file, @ModelAttribute("photo")Photo photo) throws Exception {
        if (file.isEmpty()) {
            mav.setViewName("application/uploadPhotos");
            return mav;
        }
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
        facadeService.uploadPhoto(loggedPhotographer.getId(),photo,file);
        mav.setViewName("redirect:/myProfile");
        return mav;
    }

}

