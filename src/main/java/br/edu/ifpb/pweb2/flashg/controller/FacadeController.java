package br.edu.ifpb.pweb2.flashg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.service.FacadeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class FacadeController {

    @Autowired
    private FacadeService facadeService;

    @GetMapping(value = "")
    public ModelAndView index(ModelAndView mav, HttpSession session) {
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);
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
        Photographer seguido = facadeService.findByIdPhotographer(id);
        String status = facadeService.checkFollowStatus(loggedPhotographer, seguido);
        mav.setViewName("application/showProfilePhotographer");
        mav.addObject("status", status);
        mav.addObject("photographer", seguido);
        return mav;
    }

    @GetMapping(value = "/myProfile")
    public ModelAndView myProfile( HttpSession session) {
        ModelAndView mav = new ModelAndView();
        Photographer logged = facadeService.getLoggedPhotographer(session);
        //Photographer logged = (Photographer) session.getAttribute("loggedPhotographer");
        Photographer loggedPhotographer = facadeService.findByIdPhotographer(logged.getId());
        mav.setViewName("application/myProfilePhotographer");
        session.setAttribute("loggedPhotographer", loggedPhotographer);
        mav.addObject("photographer", loggedPhotographer);
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

    @GetMapping(value="/showEditProfilePhotographer/{id}")
    public ModelAndView editProfilePhotographer(ModelAndView mav, @PathVariable("id") Long id) {
        Photographer photographer = facadeService.findByIdPhotographer(id);
        mav.addObject("photographer", photographer);
        mav.setViewName("application/editProfilePhotographer");
        return mav;
    }

    @PostMapping(value="/editProfilePhotographer")
    public ModelAndView editProfilePhotographer(@Valid @ModelAttribute Photographer photographer, BindingResult result,HttpSession session , RedirectAttributes redirectAttributes) throws EmailAlreadyExists {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav.addObject("photographer", photographer);
            mav.setViewName("application/editProfilePhotographer");
            return mav;
        }

        // Faz o update do cara
        facadeService.updatePhotographer(photographer);

        // Resgata o fotógrafo que acabou de realizar o update
        Photographer updatedLoggedPhotographer = facadeService.findByIdPhotographer(photographer.getId());

        // Resgata o fotógrafo da sessão
        Photographer loggedPhotographer = facadeService.getLoggedPhotographer(session);

        // Resgata o fotógrafo da sessão atualizado com o update
        Photographer updatedLoggedPhotographerSession = facadeService.findByIdPhotographer(loggedPhotographer.getId());

        // Atualiza o fotógrafo da sessão
        session.setAttribute("loggedPhotographer", updatedLoggedPhotographerSession);

        //mav.addObject("photographer", updatedLoggedPhotographer);
        redirectAttributes.addFlashAttribute("success", "Perfil atualizado com sucesso!");

        long id = updatedLoggedPhotographer.getId();
        mav.setViewName("redirect:/showEditProfilePhotographer/" + id );
        
        return mav;
    }




}

