
package pl.first.sudoku;


import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.exceptions.CantCloneException;



public class SudokuBoard implements Serializable, Cloneable {

    private SudokuSolver sudokuSolver;
    public static final int SIZE = 9;
    private final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    private SudokuField[][] board = new SudokuField[SIZE][SIZE];
    //nasza tablica jest teraz dwu wymiarowa tablica obiektow typu SudokuField

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = new SudokuField();
                board[i][j].addPropertyChangeListener(new SudokuFieldValueChangeListener(this));
            }
        }
    }

    public void setEditableField(int x, int y) {
        board[x][y].setEditable();
    }

    public boolean isEditableField(int x,int y) {
        return board[x][y].isEditable();
    }

    public final int get(int x, int y) {
        return board[x][y].getFieldValue();
    }

    public final void setWithCheck(int x, int y, int value) {
            board[x][y].setFieldValue(value);
                if (!checkBoard() || value > 9 || value < 0) {
                    board[x][y].setFieldValue(0);
                }
    }

    public final void set(int x, int y, int value) {
            board[x][y].setFieldValue(value);
    }

    public final void solveGame() {
        sudokuSolver.solve(this);
    }

    public SudokuRow getRow(int y) {
        SudokuField[] sudokuR = new SudokuField[SudokuFieldGroup.SIZE];

        for (int i = 0; i < SIZE; i++) {
            sudokuR[i] = new SudokuField();
            sudokuR[i].setFieldValue(get(y,i));
        }

        return new SudokuRow(sudokuR);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] sudokuC = new SudokuField[SudokuFieldGroup.SIZE];

        for (int i = 0; i < SIZE; i++) {
            sudokuC[i] = new SudokuField();
            sudokuC[i].setFieldValue(get(i,x));
        }
        return new SudokuColumn(sudokuC);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] boxFields = new SudokuField[9];
        int boxXBegin = x - x % 3;
        int boxYBegin = y - y % 3;
        for (int i = 0; i < SIZE; i++) {
            boxFields[i] = new SudokuField();
        }
        int index = 0;
        for (int i = boxXBegin; i < boxXBegin + 3; i++) {
            for (int j = boxYBegin; j < boxYBegin + 3; j++) {
                boxFields[index].setFieldValue(get(i,j));
                ++index;
            }
        }
        return new SudokuBox(boxFields);
    }

    private boolean checkBoard() {

        SudokuRow row;
        for (int i = 0; i < SIZE; i++) {
            row = getRow(i);
            if (!row.verify()) {
                return false;
            }
        }

                SudokuColumn column;
                for (int i = 0; i < SIZE; i++) {
                    column = getColumn(i);
                    if (!column.verify()) {
                        return false;
                    }

                }

                SudokuBox box;
                for (int i = 0; i < SIZE / 3; i++) {
                    for (int j = 0; j < SIZE / 3; j++) {
                        box = getBox(i, j);
                        if (!box.verify()) {
                            return false;
                        }
                    }
                }
        return true;
     }

    public SudokuField get_ref(int x, int y) {
        return board[x][y];
     }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17,
                37).append(board).toHashCode();
    }

    @Override
    public SudokuBoard clone() throws CantCloneException {
        try {
            try {
                SudokuBoard suBoard = new SudokuBoard(sudokuSolver);
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        suBoard.board[i][j] = this.board[i][j].clone();
                    }
                }
                return suBoard;
            } catch (CloneNotSupportedException e) {
                throw new CantCloneException(listBundle.getObject("_cloneWarning").toString());
            }
        } catch (CantCloneException e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
            throw e;
        }
    }
}
