package br.edu.ifpb.pweb2.flashg.exception;

public class NoSessionException extends RuntimeException {
    public NoSessionException(String message) {
        super(message);
    }

    public NoSessionException() {
        super("Não há sessão válida!");
    }
}
