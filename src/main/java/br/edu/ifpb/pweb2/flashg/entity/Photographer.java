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

    @Column(unique = true, nullable = false)
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

    private byte[] profilePicture;

    @Transient
    private String profilePictureUrl;

    @Column(nullable = false)
    private boolean acceptsFollowers = true;

    @Column(nullable = false)
    private boolean isAdmin = false;

    @Column(nullable = false)
    private boolean isBlocked = false;

    @OneToMany(mappedBy = "photographer", fetch = FetchType.EAGER)
    private List<Photo> photos;

    @OneToMany(mappedBy = "photographer", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(mappedBy = "follower", fetch = FetchType.EAGER)
    private List<Follow> following;

    @OneToMany(mappedBy = "followed", fetch = FetchType.EAGER)
    private List<Follow> followers;

}