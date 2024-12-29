package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("followerId")
    private Photographer follower;

    @ManyToOne
    @MapsId("followedId")
    private Photographer followed;

}
