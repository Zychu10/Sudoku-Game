package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class SudokuBoardDaoFactoryTest {

    @Test
    public void getDataBaseDao() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        assertNotNull(factory.getDataBaseDao("testfactory1"));
    }

    @Test
    public void getFileDao() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        assertNotNull(factory.getFileDao("testfactory2"));
    }
}
