package com.example.animalfarm360.buyerRelated;


import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BuyerRegistration {

    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private Label review;

    @FXML
    private TextField nameField, emailField, phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextArea addressField;


    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review");
    }

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    public void initialize() {


        loginCombo.setItems(FXCollections.observableArrayList(
                "Buyer",
                "Employee",
                "Admin"
        ));
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



    @FXML
    void registerBuyer(ActionEvent event) {
        try {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();


            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                showAlert("Please fill in all required fields!", Alert.AlertType.ERROR);
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                showAlert("Invalid email format! Must contain '@' and '.'", Alert.AlertType.ERROR);
                emailField.requestFocus();
                return;
            }


            if (phoneField.getText().isEmpty()) {
                showAlert("Phone Number is required!", Alert.AlertType.ERROR);
                phoneField.requestFocus(); return;
            }
            if (phoneField.getText().length() != 11 || !phoneField.getText().startsWith("01")) {
                showAlert("Phone number must be 11 digits and start with 01!", Alert.AlertType.ERROR);
                phoneField.requestFocus(); return;
            }
            try { Long.parseLong(phoneField.getText()); }
            catch (NumberFormatException e) {
                showAlert("Phone number must contain only digits!", Alert.AlertType.ERROR);
                phoneField.requestFocus(); return;
            }
            String phoneNumber = phoneField.getText();



            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO buyers (name, email, phone, password) VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);

            ps.executeUpdate();

            showAlert("Registration successful! Please login.", Alert.AlertType.INFORMATION);
            HelloApplication.changeScene("buyerLogin");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error registering buyer: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void goToLogin(ActionEvent event) {
        HelloApplication.changeScene("buyerLogin");
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
