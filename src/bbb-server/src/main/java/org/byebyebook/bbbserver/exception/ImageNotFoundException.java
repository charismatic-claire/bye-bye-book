package org.byebyebook.bbbserver.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException() { super(); }

    public ImageNotFoundException(String message, Throwable cause) { super(message, cause); }

    public ImageNotFoundException(String message) { super(message); }

    public ImageNotFoundException(Throwable cause) { super(cause); }

}
