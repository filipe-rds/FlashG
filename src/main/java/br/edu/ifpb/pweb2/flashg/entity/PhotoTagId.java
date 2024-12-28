package br.edu.ifpb.pweb2.flashg.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class PhotoTagId implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long photoId;
    private Long tagId;


}
