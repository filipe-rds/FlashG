package br.edu.ifpb.pweb2.flashg.exception;

public class NotFoundAnyFollowing extends RuntimeException {

    public NotFoundAnyFollowing(String message) {
        super(message);
    }

    public NotFoundAnyFollowing() {
        super("Você não segue ninguém!");
    }
    
}
