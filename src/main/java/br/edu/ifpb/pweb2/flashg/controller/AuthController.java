package br.edu.ifpb.pweb2.flashg.controller;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private PhotographerRepository photographerRepository;

    @GetMapping("/signup")
    public ModelAndView signUp(ModelAndView mav) {
        mav.addObject("photographer", new Photographer());
        mav.setViewName("auth/signup");
        return mav;
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("photographer") Photographer photographer,
                               BindingResult result,
                               HttpSession session,
                               Model model) {
        if (result.hasErrors()) {
            return "auth/signup";
        }

        if (this.photographerRepository.findByEmail(photographer.getEmail()).isPresent()) {
            model.addAttribute("error", "Email já cadastrado");
            return "auth/signup";
        }

        Photographer savedPhotographer = this.photographerRepository.save(photographer);
        session.setAttribute("loggedPhotographer", savedPhotographer);

        return "redirect:/testando/save";
    }

    @GetMapping("/signin")
    public ModelAndView signIn(ModelAndView mav) {
        mav.setViewName("auth/signin");
        return mav;
    }
}
