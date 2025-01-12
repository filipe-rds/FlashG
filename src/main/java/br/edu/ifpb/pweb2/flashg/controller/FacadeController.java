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

    @GetMapping(value = "/home")
    public ModelAndView home(ModelAndView mav) {
        mav.setViewName("home");
        return mav;
    }

    @GetMapping(value = "/seachPhotographers")
    public ModelAndView searchPhotographers(ModelAndView mav){
        mav.setViewName("application/findPhotographers");
        return mav;
    }

    
    @GetMapping(value = "/findPhotographers")
    public ModelAndView getAllPhotographerStartingWith(ModelAndView mav, @RequestParam("username") String username, HttpSession session){
        List<Photographer> Photographers = facadeService.findByUsernameStartingWith(username);
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.removePhotographerFromArray(Photographers,loggedPhotographer);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", Photographers);
        mav.addObject("resultados", "Resultados para: "+ username); 
        return mav;
    }

    @GetMapping(value = "/showProfile/{id}")
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id,  HttpSession session){
        mav.setViewName("application/showProfilePhotographer");
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        Photographer seguido = facadeService.findByIdPhotographer(id);
        String status  = facadeService.checkFollowStatus(loggedPhotographer, seguido);
        mav.addObject("status", status);
        mav.addObject("photographer", seguido);
        return mav;
    }

    @PostMapping(value = "/followAction")
    public ModelAndView followAction (ModelAndView mav , @RequestParam("id") Long id,HttpSession session){
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.handleFollowAction(loggedPhotographer.getId(), id);
        // Atualiza o objeto do fotografo logado
        Photographer updatedloggedPhotographer = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        // Atualiza a sessão com o novo status
        session.setAttribute("loggedPhotographer", updatedloggedPhotographer);
        mav.setViewName("redirect:/showProfile/"+id);
        //redirectAttributes.addFlashAttribute("status", "Deix");
        
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowing")
    public ModelAndView listAllFollowing(ModelAndView mav, HttpSession session){ 
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<Photographer> seguidos = facadeService.findAllFollowing(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @GetMapping(value = "/showAllPhotographers")
    public ModelAndView listAllPhotographer(ModelAndView mav,HttpSession session){
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<Photographer> Photographers = facadeService.findAllPhotographers();
        facadeService.removePhotographerFromArray(Photographers,loggedPhotographer);
        mav.addObject("Photographers", Photographers);
        mav.setViewName("application/showAllPhotographers");
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
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.uploadPhoto(loggedPhotographer.getId(),photo,file);
        mav.setViewName("redirect:/myPhotos");
        return mav;
    }

    @GetMapping(value = "/myPhotos")
    public ModelAndView showPhotographerPhotos(ModelAndView mav,HttpSession session) {
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<Photo> photos = facadeService.showPhotos(loggedPhotographer.getId());
        mav.setViewName("application/myPhotos");
        mav.addObject("photographer", loggedPhotographer);
        mav.addObject("photos", photos);
        return mav;
    }
}
