package pl.first.sudoku;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.first.sudoku.exceptions.BadGroupSizeException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldGroupTest {

    private SudokuField[] field;
    private SudokuBoard sudokuBoard;
    private SudokuSolver solver;
    private SudokuField[] fields;
    private SudokuColumn column;

    private SudokuRow makeObjectWithValidList() {
        SudokuBoard bor = new SudokuBoard(new BacktrackingSudokuSolver());
        return bor.getRow(1);
    }

    @BeforeEach
    public void Start() {
        field = new SudokuField[SudokuFieldGroup.SIZE];
        fields = new SudokuField[10];
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        for (int i = 0; i < SudokuFieldGroup.SIZE; i++) {
            field[i] = new SudokuField();
        }
        for (int i = 0; i < 10; i++) {
            fields[i] = new SudokuField();
        }
    }

    @Test
    public void SudokuFieldGroupGetterTest() {
        SudokuRow Row = new SudokuRow(field);
        assertEquals(Row.sudokuFields.get(0).getFieldValue(), 0);
    }

    @Test
    public void equalsTest() {
        SudokuRow sudokuRow = makeObjectWithValidList();
        SudokuRow sudokuRowSecond = makeObjectWithValidList();

        assertTrue(sudokuRow.equals(sudokuRowSecond) && sudokuRowSecond.equals(sudokuRow));
    }

    @Test
    public void hashCodeTest() {
        SudokuRow sudokuRow = makeObjectWithValidList();
        SudokuRow sudokuRowSecond = makeObjectWithValidList();

        assertEquals(sudokuRow.hashCode(), sudokuRowSecond.hashCode());
    }

    @Test
    public void toStringTest() {
        field = new SudokuField[9];
        SudokuFieldGroup row = new SudokuRow(field);

        System.out.println(row);
        ToStringVerifier.forClass(SudokuRow.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .withIgnoredFields("listBundle", "log")
                .verify();

        ToStringVerifier.forClass(SudokuColumn.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .withIgnoredFields("listBundle", "log")
                .verify();

        ToStringVerifier.forClass(SudokuBox.class)
                .withClassName(NameStyle.SIMPLE_NAME)
                .withIgnoredFields("listBundle", "log")
                .verify();
    }

    @Test
    public void badGroupSizeExceptionTest() {
        assertThrows(BadGroupSizeException.class, () -> {
            column = new SudokuColumn(fields);
        });
    }

}
