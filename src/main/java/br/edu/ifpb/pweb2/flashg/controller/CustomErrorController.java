package br.edu.ifpb.pweb2.flashg.controller;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(WebRequest request, Model model) {
        // Obtém os atributos de erro a partir do WebRequest
        var errorAttributes = this.errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());

        // Passa os atributos de erro para o modelo
        model.addAttribute("status", errorAttributes.get("status"));
        model.addAttribute("error", errorAttributes.get("error"));
        model.addAttribute("message", errorAttributes.get("message"));

        // Retorna o template de erro
        return "error"; // Template: error.html
    }

    public String getErrorPath() {
        return "/error";
    }
}
