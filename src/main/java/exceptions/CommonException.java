package exceptions;

public class CommonException extends RuntimeException{

    public CommonException(String message, Throwable t) {
        super(message, t);
    }

    public CommonException(String message){
        super(message);
    }
}
