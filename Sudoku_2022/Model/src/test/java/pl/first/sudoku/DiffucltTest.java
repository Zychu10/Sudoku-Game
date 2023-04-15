package pl.first.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

    public class DiffucltTest {

        @Test
        public void amountTest() {
            Difficulty difficulty1 = Difficulty.EASY;
            Difficulty difficulty2 = Difficulty.MEDIUM;
            Difficulty difficulty3 = Difficulty.HARD;
            assertEquals(20, difficulty1.getValue());
            assertEquals(40, difficulty2.getValue());
            assertEquals(60, difficulty3.getValue());
        }
    }

