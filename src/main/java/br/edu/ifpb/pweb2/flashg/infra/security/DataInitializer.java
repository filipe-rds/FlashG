package br.edu.ifpb.pweb2.flashg.infra.security;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.entity.User;
import br.edu.ifpb.pweb2.flashg.entity.UserRole;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import br.edu.ifpb.pweb2.flashg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        // Verifica se o fotógrafo admin já existe
        if (userRepository.findByEmail("admin@flashg.com").isEmpty()) {
            // Cria o User (admin)
            User adminUser = new User();
            adminUser.setEmail("admin@flashg.com");
            adminUser.setPassword(passwordEncoder.encode("admin1234")); // Codifica a senha
            adminUser.setRole(UserRole.ADMIN); // Define a role como ADMIN

            // Cria o Photographer (admin)
            Photographer adminPhotographer = new Photographer();
            adminPhotographer.setUser(adminUser); // Associa o User ao Photographer
            adminPhotographer.setUsername("admin.flashg"); // Define um username
            adminPhotographer.setFirstName("Admin");
            adminPhotographer.setLastName("FlashG");
            adminPhotographer.setProfilePictureUrl("https://media.cdnandroid.com/60/1f/2a/ad/b6/imagen-dazz-cam-vintage-film-camera-retro-art-0ori.jpg"); // URL da foto de perfil

            // Salva o Photographer e User no banco de dados
            photographerRepository.save(adminPhotographer);

            System.out.println("Fotógrafo admin criado com sucesso!");
        } else {
            System.out.println("Fotógrafo admin já existe.");
        }
    }
}
