package POLO2;

public class CustomException extends Throwable {
    private final String message;

    public CustomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
