package pl.comp.viewproject;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    /* modify the method declaration to throw generic Exception (in case any of the steps fail) */
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("lang.App", new Locale("pl")));
        /* load layout.fxml from file and assign it to a scene root object */

        Parent root = loader.load(getClass().getResourceAsStream("MenuWindow.fxml"));


        /* assign the root to a new scene and define its dimensions */
        Scene scene = new Scene(root, 760, 560);

        /* set the title of the stage (window) */
        primaryStage.setTitle("Sudoku");
        /* set the scene of the stage to our newly created from the layout scene */
        primaryStage.setScene(scene);
        /* show the stage */
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}