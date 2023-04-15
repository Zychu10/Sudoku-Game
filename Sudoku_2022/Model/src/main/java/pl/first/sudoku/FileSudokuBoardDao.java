package pl.first.sudoku;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import pl.first.sudoku.exceptions.CantCloseException;
import pl.first.sudoku.exceptions.LoadFileException;
import pl.first.sudoku.exceptions.WriteToFileException;



public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String file;

    public FileSudokuBoardDao(String file) {
        this.file = file + ".txt";
    }

    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    @Override
    public SudokuBoard read() throws LoadFileException {
        SudokuBoard obj = null;
        try {
            try (ObjectInputStream objectIn =
                         new ObjectInputStream(new FileInputStream(this.file))) {
                obj = (SudokuBoard) objectIn.readObject();

            } catch (IOException | ClassNotFoundException e) {
                throw new LoadFileException(listBundle.getObject("_loadWarning").toString(), e);
            }
        } catch (LoadFileException e) {
            throw e;
        }
        return obj;
    }


    @Override
    public void write(SudokuBoard obj) throws WriteToFileException {
        try {
            try (ObjectOutputStream objectOut =
                         new ObjectOutputStream(new FileOutputStream(file))) {
                objectOut.writeObject(obj);
            } catch (IOException e) {
                throw new WriteToFileException(listBundle.getObject("_writeWarning").toString(),e);
            }
        } catch (WriteToFileException e) {
            throw e;
        }
    }

    @Override
    public void close() throws CantCloseException {
        try {
            this.read();
        } catch (LoadFileException e) {
            throw new CantCloseException(listBundle.getObject("_closeWarning").toString(),e);
        }
        File file = new File(this.file);
        file.delete();
    }
}
