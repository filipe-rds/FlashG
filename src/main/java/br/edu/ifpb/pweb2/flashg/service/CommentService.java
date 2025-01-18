package br.edu.ifpb.pweb2.flashg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;


    public void save(Comment comment){
        repository.save(comment);
    }

    public List<Comment> findByPhotoOrderByCreatedAtDesc(Photo photo){
        return repository.findByPhotoOrderByCreatedAtDesc(photo);
    }

}