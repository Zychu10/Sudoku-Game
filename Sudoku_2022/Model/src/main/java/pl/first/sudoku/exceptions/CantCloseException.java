package pl.first.sudoku.exceptions;

public class CantCloseException extends DaoExceptions {
    public CantCloseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
