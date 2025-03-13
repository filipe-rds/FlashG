package br.edu.ifpb.pweb2.flashg.infra.handler;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.User;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private PhotographerRepository photographerRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        // Obtém o User autenticado
        User user = (User) authentication.getPrincipal();

        // Busca o Photographer vinculado ao User
        Photographer photographer = photographerRepository.findByUser(user)
                .orElseThrow(() -> new UsernameNotFoundException("Fotógrafo não encontrado para o usuário com email: " + user.getEmail()));

        // Armazena o Photographer na sessão
        HttpSession session = request.getSession();
        session.setAttribute("loggedPhotographer", photographer);

        // Redireciona para a página inicial
        response.sendRedirect("/FlashG/home");
    }
}