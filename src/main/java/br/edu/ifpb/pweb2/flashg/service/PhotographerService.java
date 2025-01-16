package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.EmailAlreadyExistsUpdate;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyFollowers;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyFollowing;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyPhotograferWithName;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotographerService {

    @Autowired
    private  PhotographerRepository repository;

    public List<Photographer> findByUsernameStartingWith(String username) throws NotFoundAnyPhotograferWithName {

        List<Photographer> photographers = repository.findByUsernameIgnoreCaseStartingWith(username);
        if(photographers.isEmpty()){
            throw new NotFoundAnyPhotograferWithName("Nenhum fotógrafo encontrado com o nome de usuário: "+ username);
        }
        return photographers;
    }

    public Photographer findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Fotógrafo não encontrado"));
    }

    public List<Photographer> findAllPhotographers(){
        return repository.findAllByOrderByIdAsc();
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

    public void updatePhotographer(Photographer photographer) {
        if(isEmailAlreadyRegistered(photographer.getEmail()) && !isEmailOfPhotographer(photographer.getEmail(), photographer.getId())){
            throw new EmailAlreadyExistsUpdate(photographer.getId());
        }
        repository.save(photographer);
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
        return repository.isPhotographerBlocked(photographer.getId()) ? "Desbloquear" : "Bloquear";
    }  
    
    public void handleBlockAction(Long id) {

        Optional<Photographer> photographerOptional = repository.findById(id);
        
        if (photographerOptional.isPresent()) {
            Photographer photographer = photographerOptional.get();
            boolean isBlocked = photographer.isBlocked(); 
            photographer.setBlocked(!isBlocked);
            repository.save(photographer);
        } else {
            throw new IllegalArgumentException("Photographer with ID " + id + " not found.");
        }
    }

    public List<Photo> findAllPhotos(Long id){
        return repository.findAllPhotos(id);
    }



}
