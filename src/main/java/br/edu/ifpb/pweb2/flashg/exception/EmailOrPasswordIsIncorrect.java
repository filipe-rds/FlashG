package br.edu.ifpb.pweb2.flashg.exception;

public class EmailOrPasswordIsIncorrect extends RuntimeException {
    public EmailOrPasswordIsIncorrect(String message) {
        super(message);
    }

    public EmailOrPasswordIsIncorrect() {
        super("Email ou senha incorretos");
    }
}
