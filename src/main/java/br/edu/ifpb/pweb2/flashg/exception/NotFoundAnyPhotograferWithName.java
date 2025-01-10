package br.edu.ifpb.pweb2.flashg.exception;

public class NotFoundAnyPhotograferWithName extends RuntimeException {

    public NotFoundAnyPhotograferWithName(){
        super("Nenhum fotógrafo com esse username foi encontrado!");
    }
    public NotFoundAnyPhotograferWithName(String message) {
        super(message);
    }
    
}
