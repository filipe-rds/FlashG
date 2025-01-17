package br.edu.ifpb.pweb2.flashg.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String commentText;
    private LocalDateTime createdAt;
    private String photographerName;
    private Integer quantidadeComentario;

    // Construtores, Getters e Setters
}
