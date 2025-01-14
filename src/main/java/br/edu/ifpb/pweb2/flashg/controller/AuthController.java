package br.edu.ifpb.pweb2.flashg.controller;

import br.edu.ifpb.pweb2.flashg.dtos.LoginDTO;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.exception.EmailOrPasswordIsIncorrect;
import br.edu.ifpb.pweb2.flashg.exception.PhotographerIsBlockedException;
import br.edu.ifpb.pweb2.flashg.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public ModelAndView signUp(ModelAndView mav) {
        mav.addObject("photographer", new Photographer());
        mav.setViewName("auth/signup");
        return mav;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("photographer") Photographer photographer,
                               BindingResult result,
                               HttpSession session) throws EmailAlreadyExists {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        Photographer savedPhotographer = authService.register(photographer);
        session.setAttribute("loggedPhotographer", savedPhotographer);

        return "redirect:/home";
    }

    @GetMapping("/signin")
    public ModelAndView signIn(ModelAndView mav) {
        mav.addObject("photographer", new LoginDTO());
        mav.setViewName("auth/signin");
        return mav;
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("photographer") LoginDTO photographer,
                        BindingResult result,
                        HttpSession session) throws EmailOrPasswordIsIncorrect, PhotographerIsBlockedException {
        if (result.hasErrors()) {
            return "auth/signin";
        }

        Photographer loggedPhotographer = authService.login(photographer);
        session.setAttribute("loggedPhotographer", loggedPhotographer);
        return "redirect:/home";
    }
}
