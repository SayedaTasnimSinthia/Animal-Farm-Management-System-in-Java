package com.example.animalfarm360.adminRelated_EmployeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Attendance;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class ManageAttendance implements Initializable {
    @FXML
    private RadioButton absentRadio;

    @FXML
    private ToggleGroup attendanceGroup;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField employeeIdField;

    @FXML
    private RadioButton lateRadio;


    @FXML
    private RadioButton presentRadio;

    @FXML
    private Label message;


    @FXML
    private TableView<Attendance> attendanceTable;


    @FXML
    private TableColumn<Attendance, String> statusColumn;

    @FXML
    private TableColumn<Attendance, LocalDate> dateColumn;


    @FXML
    void saveAttendance(ActionEvent event) {
        try {
            if (employeeIdField.getText().isEmpty()  || datePicker.getValue() == null) {
                message.setText("Please fill all fields!");
                message.setStyle("-fx-text-fill: red;");
                return;
            }

            int employeeId = Integer.parseInt(employeeIdField.getText());
            LocalDate date = datePicker.getValue();

            if (date.isAfter(LocalDate.now())) {
                message.setText("Date cannot be in the future!");
                message.setStyle("-fx-text-fill: red;");
                return;
            }


            String status;
            if (presentRadio.isSelected()) status = "Present";
            else if (absentRadio.isSelected()) status = "Absent";
            else if (lateRadio.isSelected()) status = "Late";
            else {
                message.setText("Please select status!");
                message.setStyle("-fx-text-fill: red;");
                return;
            }


            try {
                Connection connection = ConnectionSingleton.getConnection();
                PreparedStatement  ps = connection.prepareStatement("SELECT * FROM employee WHERE employeeId=?");
                ps.setInt(1, employeeId);
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    message.setText("Employee not found!");
                    return;
                }


                PreparedStatement ps1 = connection.prepareStatement("INSERT INTO attendance (employeeId, attendanceStatus, date) VALUES (?, ?, ?)");
                ps1.setInt(1, employeeId);
                ps1.setString(2, status);
                ps1.setDate(3, Date.valueOf(date));
                ps1.executeUpdate();

                message.setText("Attendance saved successfully!");
                message.setStyle("-fx-text-fill: green;");
                clearFields();
            } catch(SQLException e) {
                    e.printStackTrace();
                message.setText("Error saving attendance!");
                message.setStyle("-fx-text-fill: red;");

            }

        } catch (Exception e) {
            e.printStackTrace();
            message.setText("Unexpected error occurred! Please try again!");
            message.setStyle("-fx-text-fill: red;");
        }
    }
    private void clearFields() {
        employeeIdField.clear();
        datePicker.setValue(null);
        attendanceGroup.selectToggle(null);
    }

    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("employeeManagement");
    }

    @FXML
    void searchAttendance(ActionEvent event) {
        attendanceTable.getItems().clear();

        int employeeId;
        try {
            employeeId = Integer.parseInt(employeeIdField.getText());
        } catch (NumberFormatException e) {
            message.setText("Invalid Employee ID!");
            message.setStyle("-fx-text-fill: red;");
            return;
        }

        String sql = "SELECT date, attendanceStatus FROM attendance WHERE employeeId = ? ORDER BY date DESC";

        try {

            Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                LocalDate date = rs.getDate("date").toLocalDate();
                String status = rs.getString("attendanceStatus");

                Attendance record = new Attendance(date, status);
                attendanceTable.getItems().add(record);
            }

            if (!found) {
                message.setText("No attendance records found.");
                message.setStyle("-fx-text-fill: red;");
            } else {
                message.setText("Records loaded.");
                message.setStyle("-fx-text-fill: green;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            message.setText("Error loading attendance!");
            message.setStyle("-fx-text-fill: red;");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getDate()));
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

    }
}
