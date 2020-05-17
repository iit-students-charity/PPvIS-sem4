package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import view.*;

public class Main extends Application {

    private Scene scene;
    private GraphicGroup graphicGroup;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = getWindow();
        primaryStage.setTitle("Plotter");
        scene = new Scene(root, 1280, 720);

        setSceneKeyPressEvents();
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
