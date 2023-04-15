package pl.first.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuRowTest {

    SudokuField[] sudokuFields2;
    SudokuRow RowCloned;
    SudokuField field;

    @BeforeEach
    public void setUp (){
        sudokuFields2 = new SudokuField[9];
        for(int i=0;i<9;i++){
            sudokuFields2[i] = new SudokuField();
            sudokuFields2[i].setFieldValue(i+1);
        }
        field = new SudokuField();
        field.setFieldValue(5);
    }

    @Test
    public void cloneTestRow() throws CloneNotSupportedException {
        SudokuRow Row2 = new SudokuRow(sudokuFields2);
        RowCloned = Row2.clone();

        assertTrue(Row2.equals(RowCloned)
                    && RowCloned.equals(Row2));

    }

    @Test
    public void cloneChangeValueTest() throws CloneNotSupportedException {
        SudokuRow Row2 = new SudokuRow(sudokuFields2);
        RowCloned = Row2.clone();
        RowCloned.sudokuFields.set(5,field);
        assertTrue(RowCloned.sudokuFields.get(5) != Row2.sudokuFields.get(5));

    }

}
