package com.example.animalfarm360.controller;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Booking;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BookVisit{

    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private DatePicker date;

    @FXML
    private TextField emailField;

    @FXML
    private Label home;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea notesField;

    @FXML
    private TextField phoneField;

    @FXML
    private Label review;

    @FXML
    private ComboBox<String> time;

    @FXML
    private Spinner<Integer> visitorSpinner;



    @FXML
    void bookVisit(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        LocalDate d = date.getValue();
        String t = time.getValue();
        int visitors = visitorSpinner.getValue();
        String notes = notesField.getText();

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || d == null || t == null) {
            showAlert("Please fill all required fields.");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            showAlert("Invalid email format! Must contain '@' and '.'");
            emailField.requestFocus();
            return;
        }

        int atIndex = email.indexOf("@");
        int lastDotIndex = email.lastIndexOf(".");

        if (atIndex <= 0 || lastDotIndex <= atIndex + 1 || lastDotIndex >= email.length() - 1) {
            showAlert("Invalid email format!");
            emailField.requestFocus();
            return;
        }
        if (phoneField.getText().length() != 11 || !phoneField.getText().startsWith("01")) {
            showAlert("Phone number must be 11 digits and start with 01!");
            phoneField.requestFocus(); return;
        }

        String checkQuery = "SELECT COUNT(*) FROM bookings WHERE booking_date=? AND booking_time=? AND contacted=TRUE AND approved=TRUE";
        String insertQuery = "INSERT INTO bookings (name,email,phone,booking_date,booking_time,visitors,notes) VALUES (?,?,?,?,?,?,?)";

        try {Connection connection = ConnectionSingleton.getConnection();
             PreparedStatement ps = connection.prepareStatement(checkQuery);

            ps.setDate(1, Date.valueOf(d));
            ps.setString(2, t);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                showAlert("This slot has already been confirmed and contacted. Please select another time.");
                return;
            }


            try {
                PreparedStatement prepareStatement = connection.prepareStatement(insertQuery) ;
                prepareStatement.setString(1, name);
                prepareStatement.setString(2, email);
                prepareStatement.setString(3, phone);
                prepareStatement.setDate(4, Date.valueOf(d));
                prepareStatement.setString(5, t);
                prepareStatement.setInt(6, visitors);
                prepareStatement.setString(7, notes);
                prepareStatement.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error connecting to database.");
            }

            showAlert("Booking successful! We will contact you soon to confirm your visit.");
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error connecting to database.");
        }
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        date.setValue(null);
        time.setValue(null);
        notesField.clear();
        visitorSpinner.getValueFactory().setValue(1);
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        time.setItems(FXCollections.observableArrayList("10:00","11:30","13:00","14:30"));
        visitorSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));
        loginCombo.setItems(FXCollections.observableArrayList("Buyer","Employee","Admin"));

        date.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) return;
                if (date.getDayOfWeek() == DayOfWeek.FRIDAY || date.isBefore(LocalDate.now())
                        || date.isAfter(LocalDate.now().plusYears(1))) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }
    @FXML
    void Home(MouseEvent event) {
        HelloApplication.changeScene("home"); }

    @FXML
    void About(MouseEvent event) {
        HelloApplication.changeScene("about"); }

    @FXML
    void Booking(MouseEvent event) {
        HelloApplication.changeScene("bookVisit"); }

    @FXML
    void ContactInfo(MouseEvent event) {
        HelloApplication.changeScene("contact"); }

    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review"); }

    @FXML
    void Login(ActionEvent event) {
        String choice = loginCombo.getValue();
        if(choice == null) return;
        switch(choice) {
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


}
