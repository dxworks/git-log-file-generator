package org.dxworks.exception;

public class EmptyAttributesListException extends Exception {
    public EmptyAttributesListException(String s) {
        super(s);
    }

    public EmptyAttributesListException() {
        super();
    }
}
