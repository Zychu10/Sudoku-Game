package pl.first.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuBoxTest {
    SudokuField[] sudokuFields2;
    SudokuBox BoxCloned;
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
    public void cloneTestBox() throws CloneNotSupportedException {
        SudokuBox box = new SudokuBox(sudokuFields2);
        BoxCloned = box.clone();

        assertTrue(box.equals(BoxCloned)
                && BoxCloned.equals(box));

    }

    @Test
    public void cloneChangeValueTest() throws CloneNotSupportedException {
        SudokuBox box = new SudokuBox(sudokuFields2);
        BoxCloned = box.clone();
        BoxCloned.sudokuFields.set(5, field);
        assertTrue(BoxCloned.sudokuFields.get(5) != box.sudokuFields.get(5));

    }
}
