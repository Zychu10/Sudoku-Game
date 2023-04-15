package pl.first.sudoku.exceptions;

public class LoadFileException extends DaoExceptions {
    public LoadFileException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
