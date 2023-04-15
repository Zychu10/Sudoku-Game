package pl.first.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest {
    private SudokuBoard sudokuBoard;
    private SudokuSolver solver;

    @BeforeEach
    public void setUp() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);

    }

    private boolean checkRows(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int h = j + 1; h < 9; h++) {
                    if (board.get(i, j) == board.get(i, h)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean checkColumns(SudokuBoard board) {
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                for (int h2 = i + 1; h2 < 9; h2++) {
                    if (board.get(i, j) == board.get(h2, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkSmallSquares(SudokuBoard board) {
        for (int K = 0; K < 3; K++) {
            for (int k = 0; k < 3; k++) {
                for (int c = 0; c < 9; c++) {
                    for (int c2 = c + 1; c2 < 9; c2++) {
                        if (board.get(K * 3 + (c / 3), (k * 3 + (c % 3))) ==
                                board.get(K * 3 + (c2 / 3), (k * 3 + (c2 % 3)))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Test
    public void solveTest() {
        solver.solve(sudokuBoard);
        assertTrue(checkRows(sudokuBoard));
        assertTrue(checkColumns(sudokuBoard));
        assertTrue(checkSmallSquares(sudokuBoard));
    }

    @Test
    public void solveRepeatTest() {
        SudokuBoard obj1 = new SudokuBoard(solver);
        SudokuBoard obj2 = new SudokuBoard(solver);
        solver.solve(obj1);
        solver.solve(obj2);

        assertNotEquals(obj1, obj2);
    }

}

