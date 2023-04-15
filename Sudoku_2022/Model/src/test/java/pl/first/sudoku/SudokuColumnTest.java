package pl.first.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuColumnTest {
    SudokuField[] sudokuFields2;
    SudokuColumn columnCloned;
    SudokuField field;

    @BeforeEach
    public void setUp() {
        sudokuFields2 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            sudokuFields2[i] = new SudokuField();
            sudokuFields2[i].setFieldValue(i + 1);
        }
        field = new SudokuField();
        field.setFieldValue(5);
    }

    @Test
    public void cloneTestColumn() throws CloneNotSupportedException {
        SudokuColumn column = new SudokuColumn(sudokuFields2);
        columnCloned = column.clone();

        assertTrue(column.equals(columnCloned)
                && columnCloned.equals(column));

    }

    @Test
    public void cloneChangeValueTest() throws CloneNotSupportedException {
        SudokuColumn column = new SudokuColumn(sudokuFields2);
        columnCloned = column.clone();
        columnCloned.sudokuFields.set(5, field);
        assertTrue(columnCloned.sudokuFields.get(5) != column.sudokuFields.get(5));

    }
}
