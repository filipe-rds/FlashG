package br.edu.ifpb.pweb2.flashg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/testando")
public class TestandoController {

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView save(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/save2", method = RequestMethod.GET)
    public ModelAndView save1(ModelAndView mav) {
        mav.setViewName("index2");
        return mav;
    }
    
}

