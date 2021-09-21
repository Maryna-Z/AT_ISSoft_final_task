package exceptions;

public class NoSuchFileException extends RuntimeException {

    public NoSuchFileException() {

    }

    public NoSuchFileException(String message, Throwable t) {
        super(message, t);
    }
}
