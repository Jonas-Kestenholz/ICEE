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
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Optional;


public class Login {

        @FXML
        private Button setMaxTicketsButton;

        @FXML
        private Label maxTickets;

        @FXML
        private Label ticketCountLabel;
        private int maxTicketValue = 300;

        @FXML
        private Label ticketCountTotallabel;



    public void updateTicketCountTotalLabel(int ticketCount,int ticketCountTotal) {
        ticketCountTotallabel.setText("Antal billetter: " + (ticketCount + ticketCountTotal));
    }
        @FXML
        private void initialize() {
            initMaxTicketsButton();
        }
        private void updateMaxTicketsLabel() {
        maxTickets.setText("Maksimalt billetter: " + maxTicketValue);
    }
        // Metode til at opdatere ticket count og label
        public void updateTicketCountLabel(int ticketCount) {
            ticketCountLabel.setText("Antal billetter: " + ticketCount);
        }
        @FXML
        private void handleSetMaxTickets() {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Indstil maksimalt antal billetter");
            dialog.setHeaderText("Indtast det maksimale antal billetter:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(maxTickets -> {
                try {
                    int newMaxTicketsValue = Integer.parseInt(maxTickets);
                    int incrementValue = newMaxTicketsValue - maxTicketValue;
                    incrementMaxTickets(incrementValue);
                } catch (NumberFormatException e) {
                    System.out.println("Ugyldigt antal billetter!");
                }
            });
        }
    public void incrementMaxTickets(int incrementValue) {
        maxTicketValue += incrementValue;
        updateMaxTicketsLabel(); // Opdater labelteksten
    }
    // Metode til at initialisere knappen
    private void initMaxTicketsButton() {
        setMaxTicketsButton.setOnAction(event -> handleSetMaxTickets());
    }
}





