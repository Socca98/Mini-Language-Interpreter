package model.exceptions;

public class InexistentSymbolException extends RuntimeException {
    public InexistentSymbolException(String message) {
        super(message);
    }
}
