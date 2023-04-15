package pl.comp.viewproject;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class Comunicate {
    public void messageBoxWinLose(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String saveBaseBox(String name, String message, String title) {
        TextInputDialog td = new TextInputDialog();
        td.setTitle(title);
        td.setContentText(name);
        td.setHeaderText(message);
        td.showAndWait();
        return td.getEditor().getText();

    }

    public void messageBoxWrongName(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
