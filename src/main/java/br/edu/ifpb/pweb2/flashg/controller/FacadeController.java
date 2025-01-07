package br.edu.ifpb.pweb2.flashg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
