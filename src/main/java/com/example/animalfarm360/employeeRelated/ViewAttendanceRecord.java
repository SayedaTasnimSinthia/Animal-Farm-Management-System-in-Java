package com.example.animalfarm360.employeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Attendance;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewAttendanceRecord implements Initializable {

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<Attendance, LocalDate> dateColumn;

    @FXML
    private TableColumn<Attendance, String> statusColumn;

    @FXML
    private DatePicker filterDatePicker;

    @FXML
    private Button filterButton;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    private ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();



    @FXML
    void filterByDate(ActionEvent event) {
        LocalDate selectedDate = filterDatePicker.getValue();
        loadAttendance(selectedDate);
    }

    private void loadAttendance(LocalDate filterDate) {
        attendanceList.clear();
        try {Connection conn = ConnectionSingleton.getConnection();

            String sql = "SELECT date, attendanceStatus FROM attendance WHERE employeeId = ?";
            if (filterDate != null) {
                sql += " AND date = ?";
            }
            sql += " ORDER BY date DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, com.example.animalfarm360.controller.EmployeeLogin.loggedInEmployeeId);
            if (filterDate != null) {
                ps.setDate(2, Date.valueOf(filterDate));
            }

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                found = true;
                LocalDate date = rs.getDate("date").toLocalDate();
                String status = rs.getString("attendanceStatus");
                attendanceList.add(new Attendance(date, status));
            }

            if (!found) {
                messageLabel.setText("No attendance records found.");
                messageLabel.setStyle("-fx-text-fill: red;");
            } else {
                messageLabel.setText("Records loaded.");
                messageLabel.setStyle("-fx-text-fill: green;");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Database error occurred.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        HelloApplication.changeScene("employeeLogin");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(c -> new SimpleObjectProperty(c.getValue().getDate()));
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        attendanceTable.setItems(attendanceList);

        loadAttendance(null);
    }
}
