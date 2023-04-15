package pl.first.sudoku;


public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String fileName)  {
        return new FileSudokuBoardDao(fileName);
    }

    public JdbcSudokuBoardDao getDataBaseDao(String filename) {
            return new JdbcSudokuBoardDao(filename);
    }
}
