package pl.first.sudoku.exceptions;

public class WriteToFileException extends DaoExceptions {
    public WriteToFileException(String errorMessage, Throwable cause) {
        super(errorMessage,cause);
    }
}
