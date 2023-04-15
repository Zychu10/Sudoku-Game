package pl.first.sudoku.exceptions;

public class CantCloneException extends CloneNotSupportedException {
    public CantCloneException(String errorMessage) {
        super(errorMessage);
    }
}
