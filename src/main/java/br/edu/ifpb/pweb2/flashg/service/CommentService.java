package br.edu.ifpb.pweb2.flashg.service;

import java.util.List;

import br.edu.ifpb.pweb2.flashg.entity.CommentProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;


    public void save(Comment comment) {
        repository.save(comment);
    }

    public List<Comment> findByPhotoOrderByCreatedAtDesc(Photo photo) {
        return repository.findByPhotoOrderByCreatedAtDesc(photo);
    }

    public List<CommentProjection> findByPhotoOrderByCreatedAtAsc(Long photoId) {
        return repository.findCommentTextAndCreatedAtByPhotoIdOrderByCreatedAtAsc(photoId);
    }

    public Comment findCommentById(Long commentId) {
        return repository.findById(commentId).orElse(null);
    }

    public void updateComment(Comment comment) {
        repository.save(comment);
    }

    public void deleteComment(Long commentId) {
        repository.deleteById(commentId);
    }

}