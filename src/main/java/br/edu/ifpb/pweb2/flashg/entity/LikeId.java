package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LikeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long photoId;
    private Long photographerId;
}
