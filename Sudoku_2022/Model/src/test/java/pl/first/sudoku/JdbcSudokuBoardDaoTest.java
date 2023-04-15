package pl.first.sudoku;

import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.DaoExceptions;
import pl.first.sudoku.exceptions.DataBaseException;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {


    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudoku= new SudokuBoard(solver);
    SudokuBoardDaoFactory fDao1 = new SudokuBoardDaoFactory();
    JdbcSudokuBoardDao base = fDao1.getDataBaseDao("nazwa");
    JdbcSudokuBoardDao base2 = fDao1.getDataBaseDao("nazwa2");


    @Test
    public void createTableTest() {
        try {
            assertTrue(base.createTables());
        } catch (Exception e) {

        }
    }

    @Test
    public void createTableException() {
        assertThrows(DataBaseException.class, () -> {
            JdbcSudokuBoardDao base3 = new JdbcSudokuBoardDao("?");
            base3.insertSudokuBoard(sudoku, "sudoku4");
            base3.insertSudokuBoard(sudoku, "sudoku4");
            assertTrue(base3.deleteName("sudoku4"));
        });
    }

    @Test
    public void insertSudoku() {
        try {
            sudoku.solveGame();
            assertTrue(base.insertSudokuBoard(sudoku, "sudoku1"));
            sudoku.solveGame();
            assertTrue(base.insertSudokuBoard(sudoku, "sudoku2"));
            sudoku.solveGame();
            assertTrue(base2.insertSudokuBoard(sudoku, "sudoku3"));
            assertTrue(base.deleteName("sudoku1"));
            assertTrue(base.deleteName("sudoku2"));
            assertTrue(base2.deleteName("sudoku3"));
        } catch (DataBaseException e) {
        }
    }
    @Test
    public void readWriteTest() {
        SudokuBoard readBoard;

        try (JdbcSudokuBoardDao baseSudokuDao = fDao1.getDataBaseDao("sudoku6")) {
            sudoku.solveGame();
            baseSudokuDao.write(sudoku);
            readBoard = baseSudokuDao.read();
            assertNotSame(readBoard, sudoku);
            assertTrue(sudoku.equals(readBoard));
            baseSudokuDao.close();
            assertTrue(baseSudokuDao.deleteName("sudoku6"));
        } catch (DaoExceptions e) {

        }
    }

    @Test
    public void readException() {
        Dao<SudokuBoard> baseSudokuDao = fDao1.getDataBaseDao("testowanazwaniema");
        assertThrows(DataBaseException.class, ()->{
            baseSudokuDao.read();
        });
    }

    @Test
    public void writeException() {
        Dao<SudokuBoard> baseSudokuDao = fDao1.getDataBaseDao("testowanazwaniema");
        assertThrows(DataBaseException.class, ()->{
            baseSudokuDao.write(null);
        });
    }

    @Test
    public void nameFromBaseTest() {
        try {
            assertNotNull(base.nameFromBase());
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transactionFalse() {
        try (JdbcSudokuBoardDao base4 = fDao1.getDataBaseDao("base4")) {
            int number = base4.nameFromBase().size();
            sudoku.get_ref(1,1).removePropertyChangeListener(0);
            sudoku.set(1,1,11);
            assertEquals(sudoku.get(1,1),11);
            base4.write(sudoku);
            assertEquals(number,base4.nameFromBase().size());
            assertFalse(base4.nameFromBase().contains("base4"));
        } catch (DaoExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }

    @Test
    public void transactionTrue() {
        try (JdbcSudokuBoardDao base5 = fDao1.getDataBaseDao("base5")) {
            int number = base5.nameFromBase().size();
            sudoku.get_ref(1,1).removePropertyChangeListener(0);
            sudoku.set(1,1,8);
            assertEquals(sudoku.get(1,1),8);
            base5.write(sudoku);
            assertEquals(number+1,base5.nameFromBase().size());
            assertTrue(base5.nameFromBase().contains("base5"));
            assertTrue(base5.deleteName("base5"));
        } catch (DaoExceptions daoExceptions) {
            daoExceptions.printStackTrace();
        }
    }
    @Test
    public void delete() {
        try (JdbcSudokuBoardDao base6 = fDao1.getDataBaseDao("base6")) {
            assertThrows(DataBaseException.class, ()->{
                base6.deleteName("niematakiejnazwy");
            });
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

}
