package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowId  implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long followedId;
    private Long followerId;

}

