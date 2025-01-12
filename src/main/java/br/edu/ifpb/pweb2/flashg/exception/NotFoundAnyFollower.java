package br.edu.ifpb.pweb2.flashg.exception;

public class NotFoundAnyFollower extends RuntimeException {
    public NotFoundAnyFollower(String message) {
        super(message);
    }
    public NotFoundAnyFollower() {
        super("Você não possui seguidores!");
    }
}
