package br.edu.ifpb.pweb2.flashg.entity;

import java.time.LocalDateTime;

public interface CommentProjection {
    String getCommentText();
    LocalDateTime getCreatedAt();
}