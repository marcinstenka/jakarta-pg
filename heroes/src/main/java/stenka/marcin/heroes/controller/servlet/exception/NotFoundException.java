package stenka.marcin.heroes.controller.servlet.exception;

public class NotFoundException extends HttpRequestException {

    private static final int RESPONSE_CODE = 404;

    public NotFoundException() {
        super(RESPONSE_CODE);
    }

    public NotFoundException(String message) {
        super(message, RESPONSE_CODE);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, RESPONSE_CODE);
    }

    public NotFoundException(Throwable cause) {
        super(cause, RESPONSE_CODE);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, RESPONSE_CODE);
    }

}
