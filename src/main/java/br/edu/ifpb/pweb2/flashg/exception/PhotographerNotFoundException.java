package br.edu.ifpb.pweb2.flashg.exception;

public class PhotographerNotFoundException extends Exception {

    public PhotographerNotFoundException() {
        super("Photographer not found");
    }

    public PhotographerNotFoundException(String message) {
        super(message);
    }
    
}
