package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String commentText;

    @Column(nullable = false) 
    private LocalDateTime createdAt;

    //@JsonManagedReference
    @ManyToOne
    @JoinColumn(nullable = false)
    private Photographer photographer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Photo photo;
}
