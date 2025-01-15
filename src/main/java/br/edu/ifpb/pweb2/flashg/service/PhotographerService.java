package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
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
        // Vai ser arrumado depois.
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Fotógrafo não encontrado"));
    }
    public Photographer update(Long id,Photographer updatedPhotographer) throws Exception{
        Photographer existPhotographer = repository.findById(id).orElseThrow(() -> new Exception("Photographer not found"));
        String name = updatedPhotographer.getFirstName();
        String email = existPhotographer.getEmail();

        if(name != null){
            if(name.isBlank() || name.length() < 2 || name.length() > 100){
                throw new Exception("Name is invalid");
            }
            existPhotographer.setFirstName(updatedPhotographer.getFirstName());
        }

        // verificar caso de problema ao atualizar o usuario com o mesmo email
        if(email != null){
            if(!email.isBlank() || email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    //Photographer photographerWithSameEmail = repository.findByEmail(email).orElseThrow(() -> new Exception("Photographer not found"));
                    if(repository.findByEmail(email).isEmpty()){
                        existPhotographer.setEmail(email);
                    }
                    else {
                        throw new Exception("Email already exists");
                    }
            }
            else {
                throw new Exception("Email is invalid");
            }
        }
        else {
            throw new Exception("Email is null");
        }
        return repository.save(existPhotographer);
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


    public Photographer readById(Long id)throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("Photographer not found"));
    }

    //codigo para ser otimizado depois
    public Photographer readByEmail(String email)throws Exception{
        return repository.findByEmail(email).orElseThrow(() -> new Exception("Photographer not found"));
    }


    public void delete(Long id)throws Exception{
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
    
    public void updatePhotographer(Photographer photographer) {
        repository.save(photographer);
    }


    //     if(photographer.isPresent()){
    //         if(photographer.get().isBlocked()){ {
    //             photographer.get().setBlocked(false);
    //         }
    //         else {
    //             photographer.get().setBlocked(true);
    //         }
    //     }

    // }


}
