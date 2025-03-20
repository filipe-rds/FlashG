package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.User;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExists;
import br.edu.ifpb.pweb2.flashg.exception.UsernameAlreadyExists;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import br.edu.ifpb.pweb2.flashg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PhotographerRepository photographerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // ================ Registro ================
    @Transactional
    public Photographer register(Photographer photographer)
            throws EmailAlreadyExists, UsernameAlreadyExists {

        validateUserEmail(photographer.getUser().getEmail());
        validatePhotographerUsername(photographer.getUsername());

        // Codifica a senha do User
        photographer.getUser().setPassword(
                passwordEncoder.encode(photographer.getUser().getPassword())
        );

        photographer.setProfilePictureUrl("https://media.cdnandroid.com/60/1f/2a/ad/b6/imagen-dazz-cam-vintage-film-camera-retro-art-0ori.jpg");

        return photographerRepository.save(photographer);
    }


    // ================ Validações ================
    private void validateUserEmail(String email) throws EmailAlreadyExists {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new EmailAlreadyExists("E-mail já registrado: " + email);
        }
    }

    private void validatePhotographerUsername(String username) throws UsernameAlreadyExists {
        Optional<Photographer> photographer = photographerRepository.findByUsername(username);
        if (photographer.isPresent()) {
            throw new UsernameAlreadyExists("Username já existe: " + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

}
