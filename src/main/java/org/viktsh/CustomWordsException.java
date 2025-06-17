package org.viktsh;

/**
 * @author Viktor Shvidkiy
 */
public class CustomWordsException extends RuntimeException{
    public CustomWordsException() {
        super();
    }

    public CustomWordsException(String message) {
        super(message);
    }

    public CustomWordsException(String message, Throwable cause){
        super(message, cause);
    }
}
