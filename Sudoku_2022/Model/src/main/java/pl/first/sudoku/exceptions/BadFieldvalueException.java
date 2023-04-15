package pl.first.sudoku.exceptions;

public class BadFieldvalueException extends Exception {
    public BadFieldvalueException(final String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
