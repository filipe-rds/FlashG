package br.edu.ifpb.pweb2.flashg.service;

import br.edu.ifpb.pweb2.flashg.entity.Tag;
import br.edu.ifpb.pweb2.flashg.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository repository;

    public Tag GetTag(String name){
        return repository.findByTagName(name).orElseThrow(()->new RuntimeException("Tag not found"));
    }

    public List<Tag> GetAllTags(){
        return repository.findAll();
    }

    public Tag GetTagById(Integer id){
        return repository.findById(id).orElseThrow(()->new RuntimeException("Tag not found"));
    }

    public List<Tag> GetTagsAlike(String name){
        return repository.findByTagNameContainingIgnoreCase(name);
    }

    public void DeleteTag(Integer id){
        repository.deleteById(id);
    }

    public Tag AddTag(Tag tag){
        return repository.save(tag);
    }

}
