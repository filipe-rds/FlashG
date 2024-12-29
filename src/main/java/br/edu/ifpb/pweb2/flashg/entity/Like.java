package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Like {

    @EmbeddedId
    private LikeId id;


    @ManyToOne
    @MapsId("photoId")
    private Photo photo;


    @ManyToOne
    @MapsId("photographerId")
    private Photographer photographer;
}
