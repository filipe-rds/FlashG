package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.dtos.LoginDTO;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.exception.EmailOrPasswordIsIncorrect;
import br.edu.ifpb.pweb2.flashg.exception.UsernameAlreadyExists;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private PhotographerRepository photographerRepository;

    private boolean isEmailAlreadyRegistered(String email) {
        return this.photographerRepository.findByEmail(email).isPresent();
    }

    private boolean isUsernameAlreadyRegistered(String username) throws UsernameAlreadyExists {
        return this.photographerRepository.findByUsername(username).isPresent();
    }

    public Photographer register(Photographer photographer) throws EmailAlreadyExists {
        if (isEmailAlreadyRegistered(photographer.getEmail())) {
            throw new EmailAlreadyExists();
        }

        if(isUsernameAlreadyRegistered(photographer.getUsername())){
            throw new UsernameAlreadyExists();
        }

        return this.photographerRepository.save(photographer);
    }

    public Photographer login(LoginDTO photographer) throws EmailOrPasswordIsIncorrect {
        Optional<Photographer> optionalPhotographer = this.photographerRepository.findByEmail(photographer.getEmail());

        if (optionalPhotographer.isPresent()) {
            if (optionalPhotographer.get().getPassword().equals(photographer.getPassword())) {
                return optionalPhotographer.get();
            } else {
                throw new EmailOrPasswordIsIncorrect();
            }
        } else {
            throw new EmailOrPasswordIsIncorrect();
        }
    }

}
