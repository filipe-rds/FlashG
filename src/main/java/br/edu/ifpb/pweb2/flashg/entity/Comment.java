package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "text", length = 255)
    private String commentText;

    @Column(nullable = false, name = "date_created")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "photographer_id")
    private Photographer photographer;

    @ManyToOne
    @JoinColumn(nullable = false, name = "photo_id")
    private Photo photo;
}
