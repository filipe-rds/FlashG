package br.edu.ifpb.pweb2.flashg.exception;

public class NotFoundPhotoException extends RuntimeException {

    public NotFoundPhotoException(){
        super("Foto não encontrada!");
    }
    public NotFoundPhotoException(String message) {
        super(message);
    }

}

// Exemplos de uso:
// 2 construtores, o primeiro com uma mensagem default e o outro pra personalizar a mensagem.
// Usar no service de PhotoService ou Fachada, no método de deletePhoto, pra lançar a exceção caso a foto não seja encontrada.
