package ru.ifmo.ctddev.larionov.bach.exception;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:56
 */
public class ClassifierRuntimeException extends RuntimeException {

    public ClassifierRuntimeException() {
    }

    public ClassifierRuntimeException(String message) {
        super(message);
    }

    public ClassifierRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
