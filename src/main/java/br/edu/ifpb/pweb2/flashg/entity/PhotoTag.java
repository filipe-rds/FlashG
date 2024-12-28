package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PhotoTag {

    @EmbeddedId
    private PhotoTagId id;  

    @ManyToOne
    @MapsId("photoId")
    private Photo photo;

    @ManyToOne
    @MapsId("tagId") 
    private Tag tag;
}
