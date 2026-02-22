package cha;

import java.io.IOException;

import cha.Cha;
import cha.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Cha cha = new Cha();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(400);
            fxmlLoader.<MainWindow>getController().setCha(cha); // inject the Cha instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
