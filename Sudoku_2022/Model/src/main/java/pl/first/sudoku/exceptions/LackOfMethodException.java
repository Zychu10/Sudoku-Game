package pl.first.sudoku.exceptions;

public class LackOfMethodException extends Exception {
    public LackOfMethodException(String errorMessage, Throwable cause) {
        super(errorMessage,cause);
    }
}
