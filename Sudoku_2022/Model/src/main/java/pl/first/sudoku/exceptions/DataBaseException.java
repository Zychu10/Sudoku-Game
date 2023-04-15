package pl.first.sudoku.exceptions;

public class DataBaseException extends DaoExceptions {
    public DataBaseException(String errorMessage, Throwable cause) {
        super(errorMessage,cause);
    }
}
