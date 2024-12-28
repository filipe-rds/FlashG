package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Consulta para retornar todos os comentários de uma foto, ordenados pela data de envio mais recente
    List<Comment> findByPhotoOrderByCreatedAtDesc(Photo photo);

    // Consulta para retornar todos os comentários de um fotógrafo em uma foto, ordenados pela data de envio mais recente
    List<Comment> findByPhotographerAndPhotoOrderByCreatedAtDesc(Photographer photographer, Photo photo);

    // Consulta para retornar todos os comentários de uma foto excluindo um fotógrafo específico, ordenados pela data de envio mais recente
    List<Comment> findByPhotoAndPhotographerNotOrderByCreatedAtDesc(Photo photo, Photographer photographer);
}

