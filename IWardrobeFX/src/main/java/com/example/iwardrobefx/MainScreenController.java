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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainScreenController {


    public int ticketCounter;
    private Login loginController;
    public Button getTicket;

    @FXML
    private PasswordField passwordField;

    public Button createTicket;






    public void setLoginController(Login loginController) {
        this.loginController = loginController;
    }

    @FXML
    public void showPasswordField() {
        passwordField.setVisible(true);
    }

    @FXML
    private void openFAQTabsWindow() {
        TabPane tabPane = new TabPane();

        Tab generalTab = new Tab("Generelt");
        Tab accountTab = new Tab("Brugerkonto");

        VBox generalContent = new VBox();
        Label generalQuestion1 = new Label("Spørgsmål 1: Var det her en fed opgave?");
        TextArea generalAnswer1 = new TextArea("Svar 1: Meget spændende, uhhhh.");

        generalAnswer1.setWrapText(true);
        generalAnswer1.setEditable(false);

        generalContent.getChildren().addAll(generalQuestion1, generalAnswer1);

        generalTab.setContent(generalContent);

        VBox accountContent = new VBox();
        Label accountQuestion1 = new Label("Spørgsmål 1: Hvordan ændrer jeg min adgangskode?");
        TextArea accountAnswer1 = new TextArea("Svar 1: Sut dig selv");

        accountAnswer1.setWrapText(true);
        accountAnswer1.setEditable(false);

        accountContent.getChildren().addAll(accountQuestion1, accountAnswer1);

        accountTab.setContent(accountContent);

        tabPane.getTabs().addAll(generalTab, accountTab);

        Scene scene = new Scene(tabPane, 400, 300);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("FAQ");
        stage.show();
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
                Stage newStage = new Stage(); // Opret en ny Stage
                newStage.setScene(scene);
                newStage.show();

                Login loginController = loader.getController();
                setLoginController(loginController);
                passwordField.setVisible(false);
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
        ticketCounter++;
        if (loginController != null) {
            loginController.updateTicketCountLabel(ticketCounter);
        }

    }

    // Metoder der laver vores customer nummer
    public static String generateCustomerNumber(String fName, String phone) {
        //Tager 2 første bogstaver fra vores navn og sidste 4 cifre/bogstaver fra vores telefonnummer
        String firstNamePart = fName.substring(0, 2).toLowerCase(); // Sikre at alt står med småt.
        String phonePart = phone.substring(phone.length() - 4).toLowerCase();
        return firstNamePart + phonePart;
    }
}


