package com.example.iwardrobefx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


public class MainScreenController {

    private Login login;
    public Button getTicket;

    @FXML
    private PasswordField passwordField;

    public Button createTicket;




    @FXML
    public void showPasswordField() {
        passwordField.setVisible(true);
    }


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
                //stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Forkert adgangskode!");
        }
    }



    public void getItem() {
        // Laver en dialog til input af brugerens ticket nr.
        int nr = 0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Angiv dit garderobe nr");
        dialog.setContentText("Angiv dit garderobe nr");

        // Viser dialogen og venter på brugerens input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(number -> {
            try {
                int inputNumber = Integer.parseInt(number);
                System.out.println("Det indtastede tal er: " + inputNumber);
                // Her skal vi kalde vores metode der gør noget ved dette nummer(Findet det osv osv)

            } catch (NumberFormatException e) {
                System.err.println("Indtast venligst et gyldigt tal.");
            }
        });
    }

    public void createTicket() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Opret Ticket");
        dialog.setHeaderText("Indtast informationerne for at oprette en ticket");

        // Baggrundsfarve på vores dialog.
        dialog.getDialogPane().setStyle("-fx-background-color: #737373;"); // Sæt en lyseblå baggrundsfarve

        // Opretter vores knapper ok og cancel
        ButtonType createButtonType = new ButtonType("Opret", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Placerer vores inputs i et grid.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField();
        firstName.setPromptText("Fornavn");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Telefonnummer");

        grid.add(new Label("Fornavn:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Telefonnummer:"), 0, 1);
        grid.add(phoneNumber, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Starter med fokus på navne feltet.
        Platform.runLater(firstName::requestFocus);

        // Laver vores data om til en array af data
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                return new String[]{firstName.getText(), phoneNumber.getText()};
            }
            return null;
        });

        // Venter på det indtastet resultat.
        Optional<String[]> result = dialog.showAndWait();

        result.ifPresent(names -> {
            String fName = names[0];
            String phone = names[1];


            String customerID = generateCustomerNumber(fName, phone);
            if (!fName.isEmpty() && !phone.isEmpty()) {
                Customer customer = new Customer(customerID, fName, phone, 0); // Standard ticket nummer indtil det genereres af ticketHandler
                TicketHandler.ticketGeneration(customer);
            }
        });

    }

    // Metoder der laver vores customer nummer
    public static String generateCustomerNumber(String fName, String phone) {
        //Tager 2 første bogstaver fra vores navn og sidste 4 cifre/bogstaver fra vores telefonnummer
        String firstNamePart = fName.substring(0, 2).toLowerCase(); // Sikre at alt står med småt.
        String phonePart = phone.substring(phone.length() - 4).toLowerCase();
        return firstNamePart + phonePart;
    }
}


