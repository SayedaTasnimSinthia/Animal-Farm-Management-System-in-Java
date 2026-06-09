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
import java.sql.ResultSet;

public class BuyerLogin{
    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private TextField emailField;

    @FXML
    private Label home;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

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
                "Admin"));
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
    void loginBuyer(ActionEvent event) {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                showAlert("Enter email and password!", Alert.AlertType.ERROR);
                return;
            }

            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name FROM buyers WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                HelloApplication.changeScene("productsForBuyerToSee");
            }
            else {
                showAlert("Invalid login!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Login error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void goToRegister(ActionEvent event) {
        HelloApplication.changeScene("buyerRegistration");
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
