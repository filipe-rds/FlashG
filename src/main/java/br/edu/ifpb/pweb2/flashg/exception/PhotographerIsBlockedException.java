package br.edu.ifpb.pweb2.flashg.exception;

public class PhotographerIsBlockedException extends RuntimeException {

    public PhotographerIsBlockedException() {
        super("Usuário está bloqueado, entre em contato com o suporte!");
    }

    public PhotographerIsBlockedException(String message) {
        super(message);
    }
    
}
