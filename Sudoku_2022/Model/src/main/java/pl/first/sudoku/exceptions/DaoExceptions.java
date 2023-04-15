package pl.first.sudoku.exceptions;

public class DaoExceptions extends Exception {
    public DaoExceptions(String errorMessage, Throwable cause) {
        super(errorMessage,cause);
    }
}
