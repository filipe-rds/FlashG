package br.edu.ifpb.pweb2.flashg.model;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
public class Photographer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id
    private string name;
    @Column(unique = true)
    private string email;

}


