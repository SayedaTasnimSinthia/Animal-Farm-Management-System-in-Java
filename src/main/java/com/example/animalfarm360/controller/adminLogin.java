package com.example.animalfarm360.controller;
import com.example.animalfarm360.HelloApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class adminLogin {
    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private Button buttonMageCustomer;

    @FXML
    private Button buttonManageAnimals;

    @FXML
    private Button buttonManageEmployees;


    @FXML
    private Button buttonManageVisits;




    @FXML
    private Label review;

    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review");
    }


    @FXML
    public void initialize() {


        loginCombo.setItems(FXCollections.observableArrayList(
                "Buyer",
                "Employee",
                "Admin"
        ));

        buttonManageEmployees.setVisible(false);
        buttonManageAnimals.setVisible(false);
        buttonManageVisits.setVisible(false);
        buttonMageCustomer.setVisible(false);
    }


    @FXML
    void About(MouseEvent event) {
        HelloApplication.changeScene("about");
    }



    @FXML
    void Booking(MouseEvent event) {
       HelloApplication.changeScene("bookVisit");
    }

    @FXML
    void ContactInfo(MouseEvent event) {
       HelloApplication.changeScene("contact");
    }

    @FXML
    void Home(MouseEvent event) {
    HelloApplication.changeScene("home");
    }



    @FXML
    void Login(ActionEvent event) {
        String choice = loginCombo.getValue();
        if (choice == null) return;

        switch (choice) {
            case "Buyer":
               HelloApplication.changeScene("buyerLogin");
                break;

            case "Admin":
              HelloApplication.changeScene("adminLogin");
                break;

            case "Employee":
                HelloApplication.changeScene("employeeLogin");
                break;

        }


        loginCombo.setValue(null);
    }


    private final Map<String, String> adminAccounts = new HashMap<>() {{
        put("a", "a");
        put("b", "b");
        put("c", "c");
        put("d", "d");
        put("e", "e");
    }};


    @FXML
    public void LoginAdmin(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (adminAccounts.containsKey(email) && adminAccounts.get(email).equals(password)) {

            messageLabel.setText("LOGIN SUCCESSFUL");
            messageLabel.setStyle("-fx-text-fill: #299a3f");



            buttonManageEmployees.setVisible(true);
            buttonManageAnimals.setVisible(true);
         buttonMageCustomer.setVisible(true);
            buttonManageVisits.setVisible(true);

        }

        else {
            messageLabel.setText("INVALID EMAIL OR PASSWORD");
            messageLabel.setStyle("-fx-text-fill: red");
        }
    }


    @FXML
    void ManageAnimals(ActionEvent event) {
        HelloApplication.changeScene("animalManagement");
    }

    @FXML
    void ManageCutomer(ActionEvent event) {
        HelloApplication.changeScene("confirmOrder");
    }

    @FXML
    void ManageEmployees(ActionEvent event) {
HelloApplication.changeScene("employeeManagement");
    }



    @FXML
    void ManageVisits(ActionEvent event) {
HelloApplication.changeScene("manageVisit");
    }







}
