package com.heinsmith.paradox.commands;

/**
 * Created by somejavadev on 2017/03/19.
 */
public class InvalidCommandIndexException extends RuntimeException {

    public InvalidCommandIndexException() {
        super();
    }

    public InvalidCommandIndexException(String message) {
        super(message);
    }

    public InvalidCommandIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandIndexException(Throwable cause) {
        super(cause);
    }

    protected InvalidCommandIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
