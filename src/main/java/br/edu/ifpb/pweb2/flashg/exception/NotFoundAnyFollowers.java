package br.edu.ifpb.pweb2.flashg.exception;

public class NotFoundAnyFollowers extends RuntimeException {
    public NotFoundAnyFollowers(String message) {
        super(message);
    }
    public NotFoundAnyFollowers() {
        super("Você não possui seguidores!");
    }
}
