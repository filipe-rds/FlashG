package br.edu.ifpb.pweb2.flashg.exception;

public class UsernameAlreadyExists extends RuntimeException {

    public UsernameAlreadyExists(){
        super("Username já cadastrado!");
    }
    public UsernameAlreadyExists(String message) {
        super(message);
    }
    
}
