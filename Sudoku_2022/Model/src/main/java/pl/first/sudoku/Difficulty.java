package pl.first.sudoku;

public enum Difficulty {

    EASY(20),

    MEDIUM(40),

    HARD(60);
    private int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
