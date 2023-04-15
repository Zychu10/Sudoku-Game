package pl.first.sudoku;

import nl.jqno.equalsverifier.internal.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.CantCloseException;
import pl.first.sudoku.exceptions.LoadFileException;
import pl.first.sudoku.exceptions.WriteToFileException;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class FileSudokuBoardDaoTest {

    SudokuSolver solver1;
    SudokuBoard sBoard1;
    SudokuBoard sBoard2;
    SudokuField Field1;
    String sFile1 = "test1";
    String sFile2 = "test2";
    String sFile3 = "test3";
    SudokuBoardDaoFactory fDao1;

    @BeforeEach
    public void start()
    {
        solver1 = new BacktrackingSudokuSolver();
        sBoard1 = new SudokuBoard(solver1);
        fDao1 = new SudokuBoardDaoFactory();
        Field1 = new SudokuField();
    }

    @Test
    public void writeReadTest() {
        try (Dao<SudokuBoard> fileSudokuDao = fDao1.getFileDao(sFile2)) {
            fileSudokuDao.write(sBoard1);
            sBoard2 = fileSudokuDao.read();
            assertEquals(sBoard1, sBoard2);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    @Test
    public void readException() {
        assertThrows(LoadFileException.class, () -> {
            Dao<SudokuBoard> dao1 = fDao1.getFileDao(sFile1);
            sBoard2 = dao1.read();
        });
    }
    @Test
    public void writeException() {
        assertThrows(WriteToFileException.class, () -> {
            Dao<SudokuBoard> dao1 = fDao1.getFileDao("?");
            dao1.write(sBoard1);
        });
    }
    @Test
    public void closeException() {
        assertThrows(CantCloseException.class, () -> {
            Dao<SudokuBoard> dao1 = new FileSudokuBoardDao("plik");
            dao1.close();
        });
    }

}
