package br.edu.ifpb.pweb2.flashg.service;

import java.io.FileNotFoundException;
import java.util.List;

import br.edu.ifpb.pweb2.flashg.entity.CommentProjection;
import br.edu.ifpb.pweb2.flashg.entity.PDFGenerator;
import jakarta.annotation.Resource;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;
    private PDFGenerator pdf;


    public void save(Comment comment) {
        repository.save(comment);
    }

    public List<Comment> findByPhotoOrderByCreatedAtDesc(Photo photo) {
        return repository.findByPhotoOrderByCreatedAtDesc(photo);
    }

    public List<CommentProjection> findByPhotoOrderByCreatedAtAsc(Long photoId) {
        return repository.findCommentTextAndCreatedAtByPhotoIdOrderByCreatedAtAsc(photoId);
    }

    public ResponseEntity<Resource> generatePDF(List<CommentProjection> comments) throws FileNotFoundException {
        pdf = new PDFGenerator(comments);
        pdf.gerarCabecalho();
        pdf.gerarCorpo();
        pdf.gerarRodape();
        return pdf.imprimir();
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