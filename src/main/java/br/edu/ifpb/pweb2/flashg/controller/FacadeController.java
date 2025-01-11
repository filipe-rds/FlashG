package br.edu.ifpb.pweb2.flashg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifpb.pweb2.flashg.entity.Follow;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;
import jakarta.servlet.http.HttpSession;

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
    public ModelAndView getAllPhotographerStartingWith(ModelAndView mav, @RequestParam("username") String username, HttpSession session){
        List<Photographer> Photographers = facadeService.findByUsernameStartingWith(username);
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.removePhotographerFromArray(Photographers,loggedPhotographer);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", Photographers);
        mav.addObject("resultados", "Resultados para: "+ username); 
        return mav;
    }

    @RequestMapping(value = "/showProfile/{id}", method = RequestMethod.GET)
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id,  HttpSession session){
        mav.setViewName("application/showProfilePhotographer");
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        Photographer seguido = facadeService.findByIdPhotographer(id);
        String status  = facadeService.checkFollowStatus(loggedPhotographer, seguido);
        mav.addObject("status", status);
        mav.addObject("photographer", seguido);
        return mav;
    }

    // @RequestMapping(value = "/listPhotographerFollowing", method = RequestMethod.GET)
    // public ModelAndView listPhotographerFollowing(ModelAndView mav){
    //     mav.setViewName("application/listPhotographerFollowing");
    //     List<Photographer> seguidos = facadeService.findAllFollowing(1L);
    //     mav.addObject("seguidos", seguidos);
    //     return mav;
    // }

    @RequestMapping(value = "/followAction", method = RequestMethod.POST)
    public ModelAndView followAction (ModelAndView mav , @RequestParam("id") Long id,HttpSession session){
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        facadeService.handleFollowAction(loggedPhotographer.getId(), id);
        // Atualiza o objeto do fotografo logado
        Photographer updatedloggedPhotographer = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        // Atualiza a sessão com o novo status
        session.setAttribute("loggedPhotographer", updatedloggedPhotographer);
        mav.setViewName("redirect:/facade/showProfile/"+id);
        //redirectAttributes.addFlashAttribute("status", "Deix");
        
        return mav;
    }

    @RequestMapping(value = "/listPhotographerFollowing", method = RequestMethod.GET)
    public ModelAndView listAllFollowing(ModelAndView mav, HttpSession session){ 
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<Photographer> seguidos = facadeService.findAllFollowing(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @RequestMapping(value = "/showAllPhotographers", method = RequestMethod.GET)
    public ModelAndView listAllPhotographer(ModelAndView mav,HttpSession session){
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        List<Photographer> Photographers = facadeService.findAllPhotographers();
        facadeService.removePhotographerFromArray(Photographers,loggedPhotographer);
        mav.addObject("Photographers", Photographers);
        mav.setViewName("application/showAllPhotographers");
        return mav;

    }

}
