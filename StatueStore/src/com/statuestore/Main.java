package com.statuestore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("res/icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


}
