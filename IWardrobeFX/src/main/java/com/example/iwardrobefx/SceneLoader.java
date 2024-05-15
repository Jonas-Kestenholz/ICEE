package com.example.iwardrobefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void loadScene(String fxmlFileName, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlFileName));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}