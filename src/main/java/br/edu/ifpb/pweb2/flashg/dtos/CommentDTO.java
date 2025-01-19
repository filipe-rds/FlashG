package br.edu.ifpb.pweb2.flashg.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String imageUrl;
    private String commentText;
    private String createdAt;
    private String photographerName;
    private Integer numberOfComments;

    // Construtores, Getters e Setters
}