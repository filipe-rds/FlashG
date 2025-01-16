package br.edu.ifpb.pweb2.flashg.exception;

public class EmailAlreadyExistsUpdate extends RuntimeException {

    private Long photographerId; // Campo para armazenar o ID do fotógrafo

    // Construtor com ID
    public EmailAlreadyExistsUpdate(Long photographerId) {
        super("E-mail já cadastrado, escolha outro!");
        this.photographerId = photographerId;
    }

    // Getter para o ID
    public Long getPhotographerId() {
        return photographerId;
    }
}
