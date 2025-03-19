package br.edu.ifpb.pweb2.flashg.controller;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.User;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.exception.UsernameAlreadyExists;
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
        Photographer photographer = new Photographer();
        photographer.setUser(new User());
        mav.addObject("photographer", photographer);
        mav.setViewName("auth/signup");
        return mav;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("photographer") Photographer photographer,
                               BindingResult result,
                               HttpSession session) throws EmailAlreadyExists, UsernameAlreadyExists {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        Photographer savedPhotographer = authService.register(photographer);
        session.setAttribute("loggedPhotographer", savedPhotographer);

        return "redirect:/auth/signin";
    }

    @GetMapping("/signin")
    public ModelAndView signIn(ModelAndView mav) {
        mav.setViewName("auth/signin");
        return mav;
    }


}
