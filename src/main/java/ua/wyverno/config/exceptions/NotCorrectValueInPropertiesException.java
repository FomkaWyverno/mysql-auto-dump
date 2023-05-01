package ua.wyverno.config.exceptions;

public class NotCorrectValueInPropertiesException extends ConfigException {
    public NotCorrectValueInPropertiesException() {
    }

    public NotCorrectValueInPropertiesException(String message) {
        super(message);
    }

    public NotCorrectValueInPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotCorrectValueInPropertiesException(Throwable cause) {
        super(cause);
    }

    public NotCorrectValueInPropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
