package skhanal5.BioSearcher;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage primaryStage = null;
    
    @Override
    public void start(Stage stage) throws IOException {
        Dimension screenRes= Toolkit.getDefaultToolkit().getScreenSize();
		Parent root = FXMLLoader.load(App.class.getResource("primary.fxml"));
		stage.setTitle("biosearcher");
		stage.setScene(new Scene(root, 1280, 720));
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(new Image(App.class.getResourceAsStream("icon.png")));
		primaryStage = stage;
		primaryStage.show();
		primaryStage.setX(screenRes.width/2-(primaryStage.getWidth()/2));
		primaryStage.setY(screenRes.height/2-(primaryStage.getHeight()/2));
        
    }
    public static void main(String[] args) {
        launch(args);
    }

}