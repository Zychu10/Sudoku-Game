package pl.comp.viewproject;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.BacktrackingSudokuSolver;
import pl.first.sudoku.Dao;
import pl.first.sudoku.Difficulty;
import pl.first.sudoku.SudokuBoard;
import pl.first.sudoku.SudokuBoardDaoFactory;
import pl.first.sudoku.SudokuSolver;
import pl.first.sudoku.exceptions.BadFieldvalueException;
import pl.first.sudoku.exceptions.CantCloneException;
import pl.first.sudoku.exceptions.DaoExceptions;
import pl.first.sudoku.exceptions.LackOfMethodException;
import pl.first.sudoku.exceptions.LoadFileException;

public class SudokuBoardAppController implements Initializable {

    @FXML
    Button returnButton;
    @FXML
    GridPane tableBoard;

    String nameBase = null;
    SudokuBoard sudokuBoard;
    SudokuBoard player;

    Comunicate comunicate = new Comunicate();
    JavaBeanStringProperty [][] stringProperty = new JavaBeanStringProperty[9][9];

    Parent gamescreen;
    Scene gameScene;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuWindow.fxml"));
    private Difficulty level;
    private final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");
    SudokuBoardDaoFactory fdao = new SudokuBoardDaoFactory();

    public void setLevel(Difficulty myVar) {
        this.level = myVar;
        fillBoard(level);
        draw();
    }

    public void setNameBase(String name) {
        this.nameBase = name;
        if (nameBase != null) {
            try {
                Dao<SudokuBoard> baseSudokuDaoR = fdao.getDataBaseDao(nameBase);
                player = baseSudokuDaoR.read().clone();
            } catch (DaoExceptions | CantCloneException e) {
                log.error(e.getMessage());
            }
        }
        draw();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SudokuSolver solver = new BacktrackingSudokuSolver();
            player = new SudokuBoard(solver);
            player.solveGame();
            try {
                sudokuBoard = player.clone();
            } catch (CantCloneException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
            }
        }

    public void draw() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField currentField = (TextField) tableBoard.getChildren().get(i * 9 + j);
                if (!player.isEditableField(i, j)) {
                    currentField.setDisable(true);
                }
                try {
                    try {
                        JavaBeanStringPropertyBuilder builder =
                                JavaBeanStringPropertyBuilder.create();
                        stringProperty[i][j] = builder.bean(player.get_ref(i, j))
                                .name("Jajo").build();
                        currentField.textProperty().bindBidirectional(stringProperty[i][j]);
                    } catch (NoSuchMethodException e) {
                        throw new LackOfMethodException(listBundle
                                .getObject("_noMethodWarning").toString(),e);
                    }
                } catch (LackOfMethodException e) {
                    log.error(e.getMessage());
                }
                if (player.get(i,j) == 0) {
                    TextField field = (TextField) tableBoard.getChildren().get(i * 9 + j);
                    field.setText("");
                }

                currentField.textProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            try {
                                if (!(Integer.parseInt(newValue) >= 0
                                        && Integer.parseInt(newValue) <= 9)) {
                                         currentField.setText(oldValue);
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                currentField.setText(oldValue);
                                throw new BadFieldvalueException(listBundle
                                        .getObject("_wrongFieldValue").toString(), e);
                            }
                        } catch (BadFieldvalueException e) {
                            if (log.isErrorEnabled()) {
                                log.error(e.getMessage());
                            }
                        }
                });
            }
        }
    }

    public void fillBoard(Difficulty myvar) {
        int licznik;
        int i;
        int j;
        Random rand = new Random();
        licznik = myvar.getValue();
        if (myvar == Difficulty.EASY) {
            while (licznik > 0) {
                i = rand.nextInt(9);
                j = rand.nextInt(9);
                if (player.get(i,j) != 0) {
                    player.setEditableField(i,j);
                    player.set(i,j,0);
                    licznik--;
                }
            }
        } else if (myvar == Difficulty.MEDIUM) {
            while (licznik > 0) {
                i = rand.nextInt(9);
                j = rand.nextInt(9);
                if (player.get(i,j) != 0) {
                    player.setEditableField(i,j);
                    player.set(i,j,0);
                    licznik--;
                }
            }
        } else if (myvar == Difficulty.HARD) {
            while (licznik > 0) {
                i = rand.nextInt(9);
                j = rand.nextInt(9);
                if (player.get(i,j) != 0) {
                    player.setEditableField(i,j);
                    player.set(i,j,0);
                    licznik--;
                }
            }
        }
    }

    public void pressSaveButton() {
        try {
            Dao<SudokuBoard> emptyDao = fdao.getFileDao("EmptyDao");
            Dao<SudokuBoard> fullDao = fdao.getFileDao("FullDao");
            emptyDao.write(player);
            fullDao.write(sudokuBoard);
        } catch (DaoExceptions e) {
            if (log.isErrorEnabled()) {
           log.error(e.getMessage());
            }
        }
    }

    public void pressReadButton(ActionEvent ae) {

        try {
            Dao<SudokuBoard> emptyDao = fdao.getFileDao("EmptyDao");
            Dao<SudokuBoard> fullDao = fdao.getFileDao("FullDao");
            player = emptyDao.read();
            sudokuBoard = fullDao.read();
        } catch (DaoExceptions e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
        }
        draw();
    }

    public void pressSaveBase() {
        String tekst = comunicate.saveBaseBox(ResourceBundle
                        .getBundle("lang.App").getString("saveBaseName"),
                ResourceBundle.getBundle("lang.App")
                        .getString("saveBaseMessage"),
                ResourceBundle.getBundle("lang.App").getString("saveBaseTitle"));
        try {
            Dao<SudokuBoard> baseSudokuDao = fdao.getDataBaseDao(tekst);
            baseSudokuDao.write(player);
        } catch (DaoExceptions ex) {
            if (tekst != "") {
                comunicate.messageBoxWrongName(ResourceBundle
                                .getBundle("lang.App").getString("saveBaseWrongNameTitle"),
                        ResourceBundle.getBundle("lang.App")
                                .getString("saveBaseWrongNameMessage"),
                        Alert.AlertType.INFORMATION);
            }
            log.error(ex.getMessage());
        }
    }

    public void pressCheckButton() {
        boolean win = true;
        for (int i = 0; i < SudokuBoard.SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SIZE; j++) {
                TextField currentField = (TextField) tableBoard.getChildren().get(i * 9 + j);
                if (player.get(i,j) != sudokuBoard.get(i,j)) {
                    win = false;
                    currentField.setStyle("-fx-control-inner-background: #ff0000;");
                } else {
                    currentField.setStyle("-fx-control-inner-background: #ffffff;");
                }
            }
        }
        if (win) {
            comunicate.messageBoxWinLose(ResourceBundle
                            .getBundle("lang.App").getString("checkWinTitle"),
                    ResourceBundle.getBundle("lang.App")
                            .getString("checkWinMessage"), Alert.AlertType.INFORMATION);
        } else {
            comunicate.messageBoxWinLose(ResourceBundle
                            .getBundle("lang.App").getString("checkLoseTitle"),
                    ResourceBundle.getBundle("lang.App")
                            .getString("checkLoseMessage"), Alert.AlertType.INFORMATION);
        }
    }

    public void pressButtonReturn(ActionEvent ae) {
        try {
            Dao<SudokuBoard> emptyDao = fdao.getFileDao("EmptyDao");
            Dao<SudokuBoard> fullDao = fdao.getFileDao("FullDao");
            emptyDao.close();
            fullDao.close();
        } catch (DaoExceptions e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        }
        try {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(ResourceBundle
                        .getBundle("lang.App", new Locale(MenuController.getLanguage())));
                gamescreen = loader.load(getClass().getResourceAsStream("MenuWindow.fxml"));
                gameScene = new Scene(gamescreen, 760, 560);
                Stage appStage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
                appStage.setScene(gameScene);
                appStage.show();
            } catch (IOException e) {
                throw new LoadFileException(listBundle.getObject("_loadWarning").toString(), e);
            }
        } catch (LoadFileException e) {
            log.error(e.getMessage());
        }
    }

}
