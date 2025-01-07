package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "photographer_id", nullable = false)
    private Photographer photographer;

    @OneToMany(mappedBy = "photo")
    private List<Comment> comments;

    @OneToMany(mappedBy = "photo")
    private List<Likee> likes;

    private String imageUrl;
    private byte[] imageData;
}