package com.example.animalfarm360.controller;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeLogin {

    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Button buttonViewAttandance;

    @FXML
    private Button buttonViewDetails;

    @FXML
    private Button buttonViewSalary;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label review;

    @FXML
    private TextField usernameField;

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
    void LoginEmployee(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();


        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password.");
            return;
        }






        try {
            Connection connection = ConnectionSingleton.getConnection();

            String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);


            ResultSet rs = ps.executeQuery();


            if (rs.next()) {
                loggedInEmployeeId = rs.getInt("employeeId");
                EmployeeLogin.loggedInEmployeeId = rs.getInt("employeeId");
                messageLabel.setText("LOGIN SUCCESSFUL");
                messageLabel.setStyle("-fx-text-fill: #299a3f");


            } else {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Database connection error.");
        }





        buttonViewSalary.setVisible(true);
        buttonViewDetails.setVisible(true);
        buttonViewAttandance.setVisible(true);
    }

    @FXML
    public void initialize() {


        loginCombo.setItems(FXCollections.observableArrayList(
                "Buyer",
                "Employee",
                "Admin"
        ));
        buttonViewSalary.setVisible(false);
        buttonViewDetails.setVisible(false);
        buttonViewAttandance.setVisible(false);

    }


    public static int loggedInEmployeeId;

    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review");
    }

    @FXML
    void ViewAttandence(ActionEvent event) {
HelloApplication.changeScene("viewAttendanceRecord");
    }

    @FXML
    void ViewDetails(ActionEvent event) {
        HelloApplication.changeScene("viewDetailsInfo");
    }

    @FXML
    void ViewSalary(ActionEvent event) {
        HelloApplication.changeScene("viewSalaryHistory");
    }

}
