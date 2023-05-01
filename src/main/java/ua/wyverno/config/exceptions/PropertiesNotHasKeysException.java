package ua.wyverno.config.exceptions;

public class PropertiesNotHasKeysException extends ConfigException {
    public PropertiesNotHasKeysException() {
    }

    public PropertiesNotHasKeysException(String message) {
        super(message);
    }

    public PropertiesNotHasKeysException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesNotHasKeysException(Throwable cause) {
        super(cause);
    }

    public PropertiesNotHasKeysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
