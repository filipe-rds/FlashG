package br.edu.ifpb.pweb2.flashg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Photographer;


@Service
public class FacadeService {

    @Autowired
    private PhotographerService photographerService;
    
    public List<Photographer> findByUsernameStartingWith(String nome){

        return photographerService.findByUsernameStartingWith(nome);
        
    }
    
}
