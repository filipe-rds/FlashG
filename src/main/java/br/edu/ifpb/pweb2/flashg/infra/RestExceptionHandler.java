package br.edu.ifpb.pweb2.flashg.infra;

import br.edu.ifpb.pweb2.flashg.dtos.LoginDTO;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.exception.EmailOrPasswordIsIncorrect;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyFollowing;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyPhotograferWithName;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundPhotoException;
import br.edu.ifpb.pweb2.flashg.exception.UsernameAlreadyExists;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundPhotoException.class)
    private ModelAndView handlerNotFoundPhotoException(NotFoundPhotoException ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/404");
        return mav;
    }

    @ExceptionHandler(NotFoundAnyPhotograferWithName.class)
    private ModelAndView handlerNotFoundAnyPhotografer(NotFoundAnyPhotograferWithName ex) {
        // Exibe a mensagem de erro e redireciona para a página de "findPhotographers"
        ModelAndView mav = new ModelAndView("application/findPhotographers");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(NotFoundAnyFollowing.class)
    private ModelAndView handlerNotFoundAnyFollowing(NotFoundAnyFollowing ex) {
        // Exibe a mensagem de erro e redireciona para a página de "listPhotographerFollowing"
        ModelAndView mav = new ModelAndView("application/listPhotographerFollowing");
        //ModelAndView mav = new ModelAndView("application/findPhotographers2");
        mav.addObject("errorMessage", ex.getMessage());
        return mav;
    }

    
    @ExceptionHandler(EmailAlreadyExists.class)
    private ModelAndView handlerEmailAlreadyExists(EmailAlreadyExists ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/signup");
        mav.addObject("photographer", new Photographer());
        mav.addObject("error", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(EmailOrPasswordIsIncorrect.class)
    private ModelAndView handlerEmailOrPasswordIsIncorrect(EmailOrPasswordIsIncorrect ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/signin");
        mav.addObject("photographer", new LoginDTO());
        mav.addObject("error", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    private ModelAndView handlerUsernameAlreadyExists(UsernameAlreadyExists ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/signup");
        mav.addObject("photographer", new Photographer());
        mav.addObject("error", ex.getMessage());
        return mav;
    }

}


// Aqui eh responsável por tratar todos os erros que ocorrem na aplicação. Como a gente ta usando View,
// vamos retornar um ModelAndView com a página de erro 404. Se fosse uma API, retornaria um ResponseEntity.
// Se for um erro crítico, a gente pode direncionar pra uma pagina de erro default, mas se for um erro específico,
// a gente pode tratar de outra forma, como por exemplo, mostrar uma mensagem no canto inferior, dizendo que não existe
// a foto que o usuário está tentando acessar.