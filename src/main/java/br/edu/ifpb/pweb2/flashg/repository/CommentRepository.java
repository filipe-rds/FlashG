package br.edu.ifpb.pweb2.flashg.repository;

import br.edu.ifpb.pweb2.flashg.entity.Comment;
import br.edu.ifpb.pweb2.flashg.entity.Photo;
import br.edu.ifpb.pweb2.flashg.entity.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // Consulta para retornar todos os comentários de uma foto
    List<Comment> findByPhoto(Photo photo);

    // Consulta para retornar todos os comentários de um fotógrafo em uma foto
    List<Comment> findByPhotographerAndPhoto(Photographer photographer, Photo photo);

    // Consulta para retornar todos os comentários de uma foto excluindo um fotógrafo específico
    List<Comment> findByPhotoAndPhotographerNot(Photo photo, Photographer photographer);

}
