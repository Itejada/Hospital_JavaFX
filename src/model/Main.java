package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("\\..\\sample\\sample.fxml"));
        primaryStage.setTitle("Hospital");
        primaryStage.setScene(new Scene(root, 800, 750));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("\\..\\sample\\miestilo.css").toExternalForm());
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
