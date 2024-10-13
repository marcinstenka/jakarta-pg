package stenka.marcin.heroes.controller.servlet.exception;

public class AlreadyExistsException extends HttpRequestException {

    private static final int RESPONSE_CODE = 409;

    public AlreadyExistsException() {
        super(RESPONSE_CODE);
    }

    public AlreadyExistsException(String message) {
        super(message, RESPONSE_CODE);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause, RESPONSE_CODE);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause, RESPONSE_CODE);
    }

    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, RESPONSE_CODE);
    }

}
