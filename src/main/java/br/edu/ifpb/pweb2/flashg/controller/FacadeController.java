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
}
