package pl.first.sudoku;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class SudokuFieldValueChangeListener implements PropertyChangeListener, Serializable {

    private final SudokuBoard board;

    public SudokuFieldValueChangeListener(SudokuBoard board) {
        this.board = board;
    }

    @Override //This method gets called when a bound property is changed.
    public void propertyChange(PropertyChangeEvent evt) {
        SudokuField pole = (SudokuField) evt.getSource();
        int x = -1;
        int y = -1;
        boolean check = false;
        check:
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                if (board.get_ref(i,j) == pole) {
                    x = i;
                    y = j;
                    check = true;
                    break check;
                }
            }
        }

        if (check) {
            boolean isRight;
            isRight = (int) evt.getNewValue() >= 0 && (int) evt.getNewValue() <= 9
                    && board.getRow(x).verify() && board.getColumn(y).verify()
                    && board.getBox(x, y).verify();

            if (!isRight) {
                pole.setFieldValue((int) evt.getOldValue());
            }
        }

    }
}
