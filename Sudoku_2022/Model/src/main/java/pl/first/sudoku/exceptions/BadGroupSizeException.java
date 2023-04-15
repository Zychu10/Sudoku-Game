package pl.first.sudoku.exceptions;

public class BadGroupSizeException extends IllegalArgumentException {
    public BadGroupSizeException(final String errorMessage) {
        super(errorMessage);
    }
}
