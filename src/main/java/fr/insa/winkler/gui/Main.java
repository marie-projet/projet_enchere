
package fr.insa.winkler.gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author marie et théo
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene sc = new Scene(new MainPane(stage),780,600);
        stage.setScene(sc);
        stage.setTitle("Vente aux enchères");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

