package pl.first.sudoku;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.exceptions.DataBaseException;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:sudokuBase.db";
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
    private final Logger log = LoggerFactory.getLogger(SudokuField.class);

    private String name1;

    public JdbcSudokuBoardDao(String name1) {
        this.name1 = name1;
        try {
            try {
                Class.forName(JdbcSudokuBoardDao.DRIVER);
            } catch (ClassNotFoundException e) {
                throw new DataBaseException(listBundle.getObject("_notClassWarning").toString(),e);
            }
            createTables();
        } catch (DataBaseException e) {
            log.error(e.getMessage());
        }

    }

    public boolean createTables() throws DataBaseException {
        String createSudokuBoards = "CREATE TABLE IF NOT EXISTS sudokuBoards "
                + "(board_id INTEGER PRIMARY KEY , name varchar(255), UNIQUE (name))";
        String createSudokuFields = "CREATE TABLE IF NOT EXISTS sudokuFields (pos_x INTEGER,"
                + " pos_y INTEGER, "
                + "field_value INTEGER, isEditable BOOLEAN, "
                + "board_id INTEGER, CONSTRAINT fk_boardID FOREIGN KEY (board_id)"
                + "REFERENCES sudokuBoards(board_id))";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stat = conn.createStatement()) {
            stat.execute(createSudokuBoards);
            stat.execute(createSudokuFields);
        } catch (SQLException e) {
            throw new DataBaseException(listBundle.getObject("_createTableWarning").toString(),e);
        }
        return true;
    }


    public boolean insertSudokuBoard(SudokuBoard board, String name) throws DataBaseException {
        String startTransaction = "BEGIN TRANSACTION";
        String rollbackTransaction = "ROLLBACK";
        String commitTransaction = "COMMIT";
        try {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stat = conn.createStatement();
             PreparedStatement prepStmt = conn.prepareStatement(
                     "insert into sudokuBoards values (?, ?);");
             PreparedStatement prepStmt2 = conn.prepareStatement(
                     "insert into sudokuFields values (?, ?, ?, ?,?);")) {
            stat.execute(startTransaction);
            prepStmt.setString(2, name);
            prepStmt.execute();

            ResultSet result = stat.executeQuery("SELECT MAX(rowid) FROM sudokuBoards");
            boolean correct = true;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (board.get(i,j) > 9 || board.get(i,j) < 0) {
                        correct = false;
                    }
                        prepStmt2.setInt(1, i);
                        prepStmt2.setInt(2, j);
                        prepStmt2.setInt(3, board.get_ref(i, j).getFieldValue());
                        prepStmt2.setBoolean(4, board.get_ref(i, j).isEditable());
                        prepStmt2.setInt(5, result.getInt(1));
                        prepStmt2.execute();
                }
            }
            if (correct) {
                stat.execute(commitTransaction);
            } else {
                stat.execute(rollbackTransaction);
            }
        } catch (SQLException e) {
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stat = conn.createStatement()) {
                stat.execute(rollbackTransaction);
            } catch (SQLException ex) {
                throw new DataBaseException(listBundle
                        .getObject("_insertFieldWarning").toString(), ex);
            }
            throw new DataBaseException(listBundle
                    .getObject("_insertFieldWarning").toString(), e);
        }
        } catch (DataBaseException e) {
            throw e;
        }
        return true;
    }

    public ArrayList<String> nameFromBase() throws DataBaseException {
        ArrayList<String> names = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stat = conn.createStatement()) {
            String query = "SELECT " + "name FROM sudokuBoards";
            ResultSet readBoard = stat.executeQuery(query);

              while (readBoard.next()) {
                names.add(readBoard.getString(1));
      }
        } catch (SQLException e) {
            throw new DataBaseException(listBundle.getObject("_nameFromBaseWarning").toString(),e);
        }
        return  names;
    }


    @Override
    public SudokuBoard read() throws DataBaseException {
        SudokuBoard newBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String query = "SELECT " + "pos_x, pos_y, field_value, isEditable"
                + " FROM sudokuFields WHERE board_id=(SELECT board_id "
                + "FROM sudokuBoards WHERE name='" + name1 + "') ";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stat = conn.createStatement();
             ResultSet readBoard = stat.executeQuery(query)) {
            if (!readBoard.next()) {
            throw new SQLException();
            }
            int x;
            int y;
            do {
                x = readBoard.getInt(1);
                y = readBoard.getInt(2);
                newBoard.get_ref(x,y).removePropertyChangeListener(0);
                newBoard.set(x,y,readBoard.getInt(3));
                if (readBoard.getBoolean(4)) {
                    newBoard.setEditableField(x,y);
                }
                newBoard.get_ref(x,y)
                        .addPropertyChangeListener(new SudokuFieldValueChangeListener(newBoard));
            } while (readBoard.next());

        } catch (SQLException e) {
            throw new DataBaseException(listBundle.getObject("_loadBaseWarning").toString(),e);
        }
        return newBoard;
    }

    public boolean deleteName(String nameSudoku) throws DataBaseException {

        String selectName = "select board_id from sudokuBoards where name = '" + nameSudoku + "';";
        String deleteFields = "delete  from sudokuFields where board_id = ?;";
        String deleteBoard = "delete  from sudokuBoards where board_id =?;";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement prepStmt = conn.prepareStatement(selectName)) {

            ResultSet setName = prepStmt.executeQuery();

            PreparedStatement prepStmt1 = conn.prepareStatement(deleteFields);
            PreparedStatement prepStmt2 = conn.prepareStatement(deleteBoard);

            prepStmt1.setInt(1,setName.getInt(1));
            prepStmt2.setInt(1,setName.getInt(1));

            prepStmt1.execute();
            prepStmt2.execute();

            setName.close();

        } catch (SQLException e) {
            throw new DataBaseException(listBundle.getObject("_deleteBaseWarning").toString(),e);
        }
        return true;
    }

    @Override
    public void write(SudokuBoard obj) throws DataBaseException {
        try {
            if (obj == null) {
                throw new DataBaseException(listBundle
                        .getObject("_writeWarning").toString(), new SQLException());
            }
            insertSudokuBoard(obj, name1);
        } catch (DataBaseException e) {
            throw e;
        }
    }


    @Override
    public void close() throws DataBaseException {

    }
}

