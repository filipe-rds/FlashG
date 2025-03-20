package br.edu.ifpb.pweb2.flashg.infra.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessage = "Ocorreu um erro ao tentar fazer login.";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "E-mail ou senha incorretos!";
        } else if (exception instanceof AccountStatusException) {
            errorMessage = "Conta bloqueada. Entre em contato com o suporte!";
        }

        response.sendRedirect("/FlashG/auth/signin?error=" + errorMessage);
    }
}

