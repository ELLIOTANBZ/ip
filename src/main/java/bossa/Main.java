package bossa;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Bossa bossa = new Bossa("data/tasks.txt");

    
    @Override
    public void start(Stage stage) {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            
            // builds everything but returns the ROOT UI node created FROM MainWindow.fxml (AnchorPane), not the controller (MainWindow)
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap); // Setting the scene to be our AnchorPane

            stage.setScene(scene); // Setting the stage to show our scene

            fxmlLoader.<MainWindow>getController().setBossa(bossa);  // inject the Bossa instance
            stage.show(); // Render the stage.
        
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

}


