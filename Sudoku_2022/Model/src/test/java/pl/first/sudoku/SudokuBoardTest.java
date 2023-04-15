package pl.first.sudoku;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuBoardTest {
    private SudokuBoard sudokuBoard;
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuBoard sudokuBoard3;
    private SudokuSolver solver;


    @BeforeEach
    public void Start() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
        sudokuBoard1 = new SudokuBoard(solver);
        sudokuBoard2 = new SudokuBoard(solver);
        sudokuBoard3 = new SudokuBoard(solver);
    }

    @Test
    public void setGetMethodsTest() {
        int a;
        sudokuBoard.set(0, 0, 3);
        assertEquals(3, sudokuBoard.get(0, 0));
        a = sudokuBoard.get(1, 0);
        sudokuBoard.set(1, 0, 12);
        assertEquals(a, sudokuBoard.get(1, 0));
        sudokuBoard.set(1, 0, -1);
        assertEquals(a, sudokuBoard.get(1, 0));
    }

    @Test
    public void isDifferent() {
        sudokuBoard.solveGame();
        sudokuBoard1.solveGame();
        boolean same = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard1.get(i, j) != sudokuBoard1.get(i, j)) {
                    same = false;
                }
            }
        }
        assertTrue(same);
    }

    @Test
    public void getRowTest() {
        sudokuBoard.solveGame();
        SudokuRow row;
        row = sudokuBoard.getRow(0);
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            assertEquals(row.sudokuFields.get(i).getFieldValue(), sudokuBoard.get(0, i));
        }

        assertTrue(row.verify());
    }

    @Test
    public void getColumnTest() {
        sudokuBoard.solveGame();
        SudokuColumn column;
        column = sudokuBoard.getColumn(0);
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            assertEquals(column.sudokuFields.get(i).getFieldValue(), sudokuBoard.get(i, 0));
        }
        assertTrue(column.verify());
    }

    @Test
    public void getBoxTest() {
        sudokuBoard.solveGame();

        SudokuBox box;
        box = sudokuBoard.getBox(0, 0);
        for (int i = 0; i < SudokuBoard.SIZE / 3; i++)
            for (int j = 0; j < SudokuBoard.SIZE / 3; j++) {
                assertEquals(box.sudokuFields.get(i * 3 + j).getFieldValue(), sudokuBoard.get(i, j));
            }

        assertTrue(box.verify());
    }

    @Test
    public void checkBoardTest() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                sudokuBoard.get_ref(i, j).removePropertyChangeListener(0);
            }
        sudokuBoard.setWithCheck(0, 0, 11);
        assertEquals(0, sudokuBoard.get(0, 0));
        sudokuBoard.setWithCheck(0, 0, 2);
        sudokuBoard.setWithCheck(0, 1, 2);
        assertEquals(0, sudokuBoard.get(0, 1));
        sudokuBoard.setWithCheck(1, 0, 2);
        assertEquals(0, sudokuBoard.get(1, 0));
        sudokuBoard.setWithCheck(0, 1, 1);
        sudokuBoard.setWithCheck(1, 0, 3);
        sudokuBoard.setWithCheck(1, 2, 2);
        assertEquals(0, sudokuBoard.get(1, 2));
    }

    @Test
    public void setBoardTest() {
        sudokuBoard.set(0, 0, 11);
        assertEquals(0, sudokuBoard.get(0, 0));
        sudokuBoard.set(0, 1, 2);
        assertEquals(2, sudokuBoard.get(0, 1));
    }

    @Test
    public void testToString() {
        ToStringVerifier.forClass(SudokuBoard.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .withIgnoredFields("sudokuSolver", "board", "listBundle", "log")
                .withFailOnExcludedFields(true)
                .verify();
    }

    @Test
    public void equalsTest() {
        assertTrue(sudokuBoard2.equals(sudokuBoard3)
                && sudokuBoard3.equals(sudokuBoard2));
        assertTrue(sudokuBoard2.equals(sudokuBoard2));
    }

    @Test
    public void notEqualsTest() {
        assertFalse(sudokuBoard.equals(null));
        assertFalse(sudokuBoard.equals(solver));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(sudokuBoard.hashCode(), sudokuBoard1.hashCode());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        sudokuBoard2 = sudokuBoard.clone();
        assertTrue(sudokuBoard.equals(sudokuBoard2)
                && sudokuBoard2.equals(sudokuBoard));
        sudokuBoard2.set(5, 5, 5);
        assertFalse(sudokuBoard2.get(5, 5) == sudokuBoard.get(5, 5));
        assertFalse(sudokuBoard.get(5, 5) == 5);

    }
}

