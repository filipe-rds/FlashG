package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Photographer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    private String lastName;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false, length = 64)
    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @Column(nullable = false)
    private boolean acceptsFollowers;

    @OneToMany(mappedBy = "photographer")
    private List<Photo> photos;

    @OneToMany(mappedBy = "photographer")
    private List<Comment> comments;

    @OneToMany(mappedBy = "follower")
    private List<Follow> following;

    @OneToMany(mappedBy = "followed")
    private List<Follow> followers;

}