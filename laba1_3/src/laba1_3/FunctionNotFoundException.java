package laba1_3;

public class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }

    public FunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
