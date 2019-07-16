package com.statuestore.helpers;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class Stages {

    private Stage stage;

    public Stages() {}

    public Stages(String path, String title) throws Exception {
        openStage(path, title);
    }

    public void openStage(String path, String title) {

        try {
            stage = new Stage();
            stage.getIcons().add(new Image("res/icon.png"));
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Scene scene = new Scene(parent);

            stage.setTitle(title);
            stage.setScene(scene);
            stage.setOnCloseRequest(e -> stage = null);
            stage.setOnHiding(e -> stage = null);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public boolean isOpen() {
        if(stage == null)
            return false;
        else
            return true;
    }

    public void closeStageFrom(javafx.scene.layout.Pane pane) {
        pane.getScene().getWindow().hide();
    }

    public Stage getStage() {
        return stage;
    }
}
