package sabbaken.laba2.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import sabbaken.laba2.view.View;

public class ApplicationController extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        View view 	  = new View();
        mainStage = view.getStage();
        mainStage.show();
    }
}
