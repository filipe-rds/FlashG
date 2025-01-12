package br.edu.ifpb.pweb2.flashg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class FacadeController {

    @Autowired
    private FacadeService facadeService;

    private Photographer getLoggedPhotographer(HttpSession session) {
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        if (loggedPhotographer == null) {
            throw new IllegalStateException("Usuário não está logado.");
        }
        return loggedPhotographer;
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "redirect:/auth/signin";
    }

    @GetMapping(value = "")
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        mav.setViewName("home");
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
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findByUsernameStartingWith(username);
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.setViewName("application/findPhotographers");
        mav.addObject("photographers", photographers);
        mav.addObject("resultados", "Resultados para: " + username);
        return mav;
    }

    @GetMapping(value = "/showProfile/{id}")
    public ModelAndView showProfile(ModelAndView mav, @PathVariable("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        Photographer seguido = facadeService.findByIdPhotographer(id);
        String status = facadeService.checkFollowStatus(loggedPhotographer, seguido);
        mav.setViewName("application/showProfilePhotographer");
        mav.addObject("status", status);
        mav.addObject("photographer", seguido);
        return mav;
    }

    @GetMapping(value = "/myProfile")
    public ModelAndView myProfile(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        mav.setViewName("application/myProfilePhotographer");
        mav.addObject("photographer", loggedPhotographer);
        return mav;
    }

    @PostMapping(value = "/followAction")
    public ModelAndView followAction(ModelAndView mav, @RequestParam("id") Long id, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        facadeService.handleFollowAction(loggedPhotographer.getId(), id);
        Photographer updatedLoggedPhotographer = facadeService.findByIdPhotographer(loggedPhotographer.getId());
        session.setAttribute("loggedPhotographer", updatedLoggedPhotographer);
        mav.setViewName("redirect:/showProfile/" + id);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowing")
    public ModelAndView listAllFollowing(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        List<Photographer> seguidos = facadeService.findAllFollowing(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowing");
        mav.addObject("seguidos", seguidos);
        return mav;
    }

    @GetMapping(value = "/listPhotographerFollowers")
    public ModelAndView listAllFollowers(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        List<Photographer> seguidores = facadeService.findAllFollowers(loggedPhotographer.getId());
        mav.setViewName("application/listPhotographerFollowers");
        mav.addObject("seguidores", seguidores);
        return mav;
    }

    @GetMapping(value = "/showAllPhotographers")
    public ModelAndView listAllPhotographer(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = getLoggedPhotographer(session);
        List<Photographer> photographers = facadeService.findAllPhotographers();
        facadeService.removePhotographerFromArray(photographers, loggedPhotographer);
        mav.addObject("photographers", photographers);
        mav.setViewName("application/showAllPhotographers");
        return mav;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/signin";
    }
}

