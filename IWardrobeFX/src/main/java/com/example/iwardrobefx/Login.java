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
        public MenuItem Back;
        @FXML
        private Label ticketCountLabel;

        private int ticketCount = 0;

        @FXML
        private void initialize() {
            updateTicketCountLabel();
        }

        // Metode til at opdatere ticket count og label
        public void incrementTicketCount() {
            ticketCount++;
            updateTicketCountLabel();
        }

        private void updateTicketCountLabel() {
            ticketCountLabel.setText(Integer.toString(ticketCount));
        }
    }





