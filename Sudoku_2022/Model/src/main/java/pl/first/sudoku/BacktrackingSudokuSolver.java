package pl.first.sudoku;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    @Override
    public void solve(final SudokuBoard board) {

        List<Integer> valueTable =
                Arrays.asList(new Integer[SudokuBoard.SIZE * SudokuBoard.SIZE]);
        valueTable.replaceAll(ignored -> 0);
        for (int i = 0; i < 81; i++) {

            boolean isValid = false;
            int row = i / 9;
            int column = i % 9;
            if (valueTable.get(i) == 0) {
                List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
                Collections.shuffle(numberList);
                valueTable.set(i, numberList.get(i % 9));
                int wart = valueTable.get(i);
                board.set(row, column, valueTable.get(i));
                do {
                    if (board.get(row, column) != 0) {
                        isValid = true;
                        break;
                    }
                    wart = wart % 9 + 1;
                    board.set(row, column, wart);
                } while (wart != valueTable.get(i));

            } else {
                int constValue = board.get(row, column);
                int wart = board.get(row, column);
                wart = wart % 9 + 1;
                board.set(row, column, wart);
                while (wart != valueTable.get(i)) {
                    if (board.get(row, column) != constValue) {
                        isValid = true;
                        break;
                    }
                    wart = wart % 9 + 1;
                    board.set(row, column, wart);
                }
            }
            if (!isValid) {
                valueTable.set(i, 0);
                board.set(row, column, 0);
                i -= 2;
            }
        }
    }
}