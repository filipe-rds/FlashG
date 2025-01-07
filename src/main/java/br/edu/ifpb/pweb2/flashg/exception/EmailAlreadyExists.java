package br.edu.ifpb.pweb2.flashg.exception;

public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists(String message) {
        super(message);
    }

    public EmailAlreadyExists() {
        super("Email já cadastrado");
    }
}
