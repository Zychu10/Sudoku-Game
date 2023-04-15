package pl.comp.viewproject;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.sudoku.Difficulty;
import pl.first.sudoku.JdbcSudokuBoardDao;
import pl.first.sudoku.SudokuBoard;
import pl.first.sudoku.exceptions.DataBaseException;
import pl.first.sudoku.exceptions.LoadFileException;

public class MenuController implements Initializable {

    private static String language = "pl";
    @FXML
    Button easyButton;
    @FXML
    Button mediumButton;
    @FXML
    Button hardButton;
    @FXML
    Label difficultyLevel;
    @FXML
    Label welcomeText;
    @FXML
    Button exitButton;

    String nameBase = null;
    public Label authorsLabel;
    Parent gamescreen;
    Scene gameScene;
    JdbcSudokuBoardDao baza = new JdbcSudokuBoardDao("Baza");

    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    private final Logger log = LoggerFactory.getLogger(SudokuBoard.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("pl.first.sudoku.Language");

    public static String getLanguage() {
        return language;
    }

    public void pressButtonEasy(ActionEvent ae)  {
        try {
            loadSudoku(ae, Difficulty.EASY);
        } catch (LoadFileException e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
        }
    }


    public void pressButtonMedium(ActionEvent ae)  {
        try {
            loadSudoku(ae, Difficulty.MEDIUM);
        } catch (LoadFileException e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
        }
    }

    public void pressButtonHard(ActionEvent ae)  {
        try {
            loadSudoku(ae, Difficulty.HARD);
        } catch (LoadFileException e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
        }
    }

    public void loadSudoku(ActionEvent event, Difficulty level) throws LoadFileException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("lang.App", new Locale(language)));
            gamescreen = loader.load(getClass().getResourceAsStream("SudokuBoardWindow.fxml"));
            SudokuBoardAppController sudokuBoardAppController = loader.getController();
            sudokuBoardAppController.setLevel(level);

            gameScene = new Scene(gamescreen, 720, 480);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.show();
        } catch (IOException e) {
            throw new LoadFileException(listBundle.getObject("_loadWarning").toString(),e);
        }
    }

    public void pressCloseButton(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.close();
    }

    public void pressComboBox(ActionEvent event) {
        for (int i = 0; i < comboBox2.getItems().size(); i++) {

            if (comboBox2.getSelectionModel().isSelected(i)) {
                nameBase = comboBox2.getItems().get(i);
            }
        }
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("lang.App", new Locale(language)));
            try {
                gamescreen = loader.load(getClass().getResourceAsStream("SudokuBoardWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            SudokuBoardAppController sudokuBoardAppController = loader.getController();
            sudokuBoardAppController.setNameBase(nameBase);
            gameScene = new Scene(gamescreen, 720, 480);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(gameScene);
            appStage.show();
        }



    @FXML
    public void chooseLanguage(ActionEvent event) {
        try {
            try {
                if (comboBox1.getSelectionModel().isSelected(1)) {
                    Locale.setDefault(new Locale("pl"));
                    language = "pl";
                }
                if (comboBox1.getSelectionModel().isSelected(0)) {
                    Locale.setDefault(new Locale("en"));
                    language = "en";
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setResources(ResourceBundle.getBundle("lang.App", new Locale(language)));
                gamescreen = loader.load(getClass().getResourceAsStream("MenuWindow.fxml"));

                gameScene = new Scene(gamescreen, 760, 560);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(gameScene);
                appStage.show();

            } catch (IOException e) {
                throw new LoadFileException(listBundle.getObject("_loadWarning").toString(),e);
            }
        } catch (LoadFileException e) {
            if (log.isErrorEnabled()) {
            log.error(e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBox1.getItems().addAll(
                "English",
                "Polish"
        );
        try {
            comboBox2.getItems().addAll(
                    baza.nameFromBase()
            );
        } catch (DataBaseException e) {
            log.error(e.getMessage());
        }
        AuthorsBundle authorsBundle = new AuthorsBundle();
        Object[][] authors = authorsBundle.getContents();
        String message = resourceBundle.getString("authorsMessageText") + "\n";
        for (Object[] author : authors) {
            message += author[1] + "\n";
        }
        authorsLabel.textProperty().set(message);
    }

}
