package pl.first.sudoku;

import pl.first.sudoku.exceptions.DaoExceptions;

public interface Dao<T> extends AutoCloseable {

     T read() throws DaoExceptions;

     void write(T obj) throws DaoExceptions;

     @Override
     void close() throws DaoExceptions;
}
