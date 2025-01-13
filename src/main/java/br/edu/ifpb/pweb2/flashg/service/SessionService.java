package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.NoSessionException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    // Temporário (será substituído com spring security!)
    public Photographer getLoggedPhotographer(HttpSession session) throws NoSessionException {
        Photographer loggedPhotographer = (Photographer) session.getAttribute("loggedPhotographer");
        if (loggedPhotographer == null) {
            throw new NoSessionException("Não há sessão válida!");
        }
        return loggedPhotographer;
    }

    public void logoutPhotographer(HttpSession session) {
        session.removeAttribute("loggedPhotographer");
    }
}
