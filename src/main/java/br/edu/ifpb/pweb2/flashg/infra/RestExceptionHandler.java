package br.edu.ifpb.pweb2.flashg.infra;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifpb.pweb2.flashg.exception.NotFoundPhotoException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NotFoundPhotoException.class)
    private ModelAndView handlerNotFoundPhotoException( NotFoundPhotoException ex){

        return new ModelAndView("error/404");

    }
    
}


// Aqui eh responsável por tratar todos os erros que ocorrem na aplicação. Como a gente ta usando View,
// vamos retornar um ModelAndView com a página de erro 404. Se fosse uma API, retornaria um ResponseEntity.
// Se for um erro crítico, a gente pode direncionar pra uma pagina de erro default, mas se for um erro específico,
// a gente pode tratar de outra forma, como por exemplo, mostrar uma mensagem no canto inferior, dizendo que não existe
// a foto que o usuário está tentando acessar.