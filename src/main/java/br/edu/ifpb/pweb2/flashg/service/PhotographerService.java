package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotographerService {

    @Autowired
    private  PhotographerRepository repository;

    public Photographer create(Photographer photographer) throws Exception{
        if(repository.findByEmail(photographer.getEmail()).isPresent()){
            throw new Exception("Email already exists");
        }
        return repository.save(photographer);
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

    public List<Photographer> readAll(){
        return repository.findAll();
    }

    public Photographer readById(Long id)throws Exception{
        return repository.findById(id).orElseThrow(() -> new Exception("Photographer not found"));
    }

    //codigo para ser otimizado depois
    public Photographer readByEmail(String email)throws Exception{
        return repository.findByEmail(email).orElseThrow(() -> new Exception("Photographer not found"));
    }

//    public List<Photographer> readByName(String name)throws Exception{
//        return repository.findByName(name);
//    }

    public void delete(Long id)throws Exception{
        repository.deleteById(id);
    }


}
