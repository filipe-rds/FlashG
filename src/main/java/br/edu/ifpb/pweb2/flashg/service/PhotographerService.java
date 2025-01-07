package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyFollowing;
import br.edu.ifpb.pweb2.flashg.exception.NotFoundAnyPhotograferWithName;
import br.edu.ifpb.pweb2.flashg.repository.PhotographerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotographerService {

    @Autowired
    private  PhotographerRepository repository;

    public List<Photographer> findByUsernameStartingWith(String username){

        List<Photographer> photographers = repository.findByUsernameStartingWith(username);
        if(photographers.isEmpty()){
            throw new NotFoundAnyPhotograferWithName("Nenhum fotógrafo encontrado com o nome de usuário: "+ username);
        }
        return photographers;
    }

    public Photographer findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Fotógrafo não encontrado"));
    }

    public List<Photographer> findAll(){
        return repository.findAll();
    }

    public List<Photographer> findAllFollowing(Long id){

        List<Photographer> photographers = repository.findAllFollowing(id);

        if(photographers.isEmpty()){
            throw new NotFoundAnyFollowing("Você não segue ninguém!");
        }
        return photographers;
    }
}
