package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.*;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PhotographerService {

    @Autowired
    private  PhotographerRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Photographer> findByUsernameStartingWith(String username) throws NotFoundAnyPhotograferWithName {

        List<Photographer> photographers = repository.findByUsernameIgnoreCaseStartingWith(username);
        if(photographers.isEmpty()){
            throw new NotFoundAnyPhotograferWithName("Nenhum fotógrafo encontrado com o nome de usuário: "+ username);
        }
        return photographers;
    }

    public Photographer findById(Long id){
        return repository.findById(id).orElseThrow(() -> new PhotographerNotFoundException("Fotógrafo não encontrado"));
    }

    public Page<Photographer> findAllPhotographers(Pageable page){
        return repository.findAllByOrderByIdAsc(page);
    }

    public List<Photographer> findAllFollowing(Long id){

        List<Photographer> photographers = repository.findAllFollowing(id);

        if(photographers.isEmpty()){
            throw new NotFoundAnyFollowing();
        }
        return photographers;
    }

    public List<Photographer> findAllFollowers(Long id){

        List<Photographer> photographers = repository.findAllFollowers(id);

        if(photographers.isEmpty()){
            throw new NotFoundAnyFollowers();
        }
        return photographers;
    }

    public void updatePhotographer(Photographer photographer)  {
        // Valida se o e-mail já está sendo usado por outro fotógrafo
        if (isEmailAlreadyRegistered(photographer.getUser().getEmail())
                && !isEmailOfPhotographer(photographer.getUser().getEmail(), photographer.getId())) {
            throw new EmailAlreadyExistsUpdate(photographer.getId());
        }

        // Verifica se o fotógrafo existe antes de realizar o update
        Photographer existingPhotographer = repository.findById(photographer.getId())
                .orElseThrow(() -> new PhotographerNotFoundException("Fotógrafo não encontrado"));

        // Guarda a referência ao usuário existente
        User existingUser = existingPhotographer.getUser();

        // Atualiza somente os campos que não são nulos
        copyNonNullProperties(photographer, existingPhotographer);

        // Agora atualiza os dados do usuário separadamente
        if (photographer.getUser() != null) {
            User updatedUser = photographer.getUser();
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            // Redefine o usuário existente atualizado
            existingPhotographer.setUser(existingUser);
        }

        // Persiste as alterações no banco de dados
        repository.save(existingPhotographer);
    }


    private boolean isEmailAlreadyRegistered(String email) {
        return repository.findByEmail(email).isPresent();
    }

    private boolean isEmailOfPhotographer(String email, Long id) {
        return repository.findPhotographerByIdAndEmail(id, email).isPresent();
    }


    public Photographer findByEmail(String email)throws Exception{
        return repository.findByEmail(email).orElseThrow(() -> new Exception("Fotógrafo não encontrado"));
    }

    public void deleteById(Long id)throws Exception{
        repository.deleteById(id);
    }

    public String checkBlockedStatus(Photographer photographer){
        return repository.isPhotographerEnabled(photographer.getId()) ? "Bloquear" : "Desbloquear";
    }

    public void handleBlockAction(Long id) {

        Optional<Photographer> photographerOptional = repository.findById(id);

        if (photographerOptional.isPresent()) {
            Photographer photographer = photographerOptional.get();
            boolean isEnabled = photographer.getUser().isEnabled();
            photographer.getUser().setEnabled(!isEnabled);
            repository.save(photographer);
        } else {
            throw new IllegalArgumentException("Photographer with ID " + id + " not found.");
        }
    }

    public List<Photo> findAllPhotos(Long id){
        return repository.findAllPhotos(id);
    }

    private void copyNonNullProperties(Photographer source, Photographer target) {
        // Guarda referência do usuário original
        User originalUser = target.getUser();

        // Guarda temporariamente os valores de foto para verificar se devemos restaurá-los
        byte[] originalProfilePicture = target.getProfilePicture();
        String originalProfilePictureUrl = target.getProfilePictureUrl();

        // Cria um array de nomes de propriedades a serem ignoradas
        String[] ignoreProperties = {"user", "photos", "comments", "following", "followers", "likes"};

        // Copia apenas propriedades não nulas exceto as ignoradas
        BeanUtils.copyProperties(source, target, ignoreProperties);

        // Restaura as fotos originais se novas não foram fornecidas
        if (source.getProfilePicture() == null) {
            target.setProfilePicture(originalProfilePicture);
        }

        if (source.getProfilePictureUrl() == null) {
            target.setProfilePictureUrl(originalProfilePictureUrl);
        }

        // Restaura o usuário original e atualiza apenas os campos específicos do usuário
        if (source.getUser() != null) {
            // Atualiza o email se fornecido
            if (source.getUser().getEmail() != null) {
                originalUser.setEmail(source.getUser().getEmail());
            }

            // Atualiza a senha se fornecida
            if (source.getUser().getPassword() != null && !source.getUser().getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(source.getUser().getPassword());
                originalUser.setPassword(encodedPassword);
            }
        }

        // Restaura a referência do usuário original
        target.setUser(originalUser);
    }

    // Retorna os nomes das propriedades nulas de um objeto.
    private String[] getNullPropertyNames(Object source) {
        // Cria um BeanWrapper para inspecionar as propriedades do objeto fonte
        final BeanWrapper src = new BeanWrapperImpl(source);

        // Obtém a lista de todas as propriedades do objeto
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // Filtra as propriedades que têm valor nulo e retorna seus nomes
        return Arrays.stream(pds)
                .filter(pd -> src.getPropertyValue(pd.getName()) == null) // Filtra as propriedades com valor nulo
                .map(java.beans.PropertyDescriptor::getName)             // Extrai o nome da propriedade
                .toArray(String[]::new);                                // Converte o stream em um array de strings
    }
}
