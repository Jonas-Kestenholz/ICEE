package com.example.iwardrobefx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;


    public class Login {
        @FXML
        private PasswordField passwordField;
        private SceneLoader SceneLoader;
        private Scene previousScene;
        @FXML
        private TextField ticketCountField;
        private Scene currentScene;
        private int ticketCount = 0;
        @FXML
        private Spinner<Integer> maxTicketSpinner;

        private int maxTicketCount;


        // Metode til at skifte fra login.fxml til mainscreen.fxml
        @FXML
        public void switchToAnotherScene() {
            String correctPassword = "1234"; // Ændre til din faktiske adgangskode
            String enteredPassword = passwordField.getText();

            if (enteredPassword.equals(correctPassword)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                    Parent root = loader.load();


                    Scene scene = new Scene(root);
                    Stage stage = (Stage) passwordField.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Forkert adgangskode!");
            }
        }
    }





