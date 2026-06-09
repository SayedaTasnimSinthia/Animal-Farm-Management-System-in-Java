package com.example.animalfarm360.adminRelated_EmployeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.SalaryRecord;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManageSalary implements Initializable{
    @FXML
    private TableColumn<SalaryRecord, Number> amountColumn;

    @FXML
    private TextField employeeIdField;

    @FXML
    private TableColumn<SalaryRecord, String> monthColumn;

    @FXML
    private DatePicker monthPicker;

    @FXML
    private RadioButton paidRadio;

    @FXML
    private ToggleGroup paymentGroup;


    @FXML
    private TableView<SalaryRecord> salaryTable;

    @FXML
    private TableColumn<SalaryRecord, String> statusColumn;

    @FXML
    private Label message;

    @FXML
    private RadioButton unpaidRadio;



    private ObservableList<SalaryRecord> salaryList = FXCollections.observableArrayList();



    @FXML
    void saveSalary(ActionEvent event) {
        try {
            if (employeeIdField.getText().isEmpty() || monthPicker.getValue() == null) {
                showError("Please fill all fields!");
                return;
            }

            int employeeId = Integer.parseInt(employeeIdField.getText());
            LocalDate pickedDate = monthPicker.getValue();
            String monthYear = pickedDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            String status;
            if (paidRadio.isSelected()) status = "Paid";
            else if (unpaidRadio.isSelected()) status = "Unpaid";
            else {
                showError("Please select Paid/Unpaid!");
                return;
            }

            Connection connection = ConnectionSingleton.getConnection();

            PreparedStatement psEmployee = connection.prepareStatement("SELECT salary, doj FROM employee WHERE employeeId=?");
            psEmployee.setInt(1, employeeId);
            ResultSet rs = psEmployee.executeQuery();

            if (!rs.next()) {
                showError("Employee not found!");
                return;
            }

            double salaryAmount = rs.getDouble("salary");
            LocalDate dateOfJoining = rs.getDate("doj").toLocalDate();



            LocalDate firstOfMonth = pickedDate.withDayOfMonth(1);
            LocalDate joinMonth = dateOfJoining.withDayOfMonth(1);
            if (firstOfMonth.isBefore(joinMonth)) {
                showError("Cannot pay salary for months before joining (" + dateOfJoining + ")");
                return;
            }


            PreparedStatement psCheck = connection.prepareStatement("SELECT * FROM salary WHERE employeeId=? AND monthYear=?");
            psCheck.setInt(1, employeeId);
            psCheck.setString(2, monthYear);
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
                String existingStatus = rsCheck.getString("status");

                if (existingStatus.equals("Paid")) {
                    showError("Salary already marked as Paid for " + monthYear + ". Status cannot be changed.");
                    return;
                }

                else {
                    if (status.equals("Paid")) {
                        PreparedStatement psUpdate = connection.prepareStatement("UPDATE salary SET status=? WHERE employeeId=? AND monthYear=?");
                        psUpdate.setString(1, status);
                        psUpdate.setInt(2, employeeId);
                        psUpdate.setString(3, monthYear);
                        psUpdate.executeUpdate();
                    } else {
                        showError("Cannot mark Unpaid on an existing record.");
                        return;
                    }
                }
            } else {
                PreparedStatement psInsert = connection.prepareStatement(
                        "INSERT INTO salary (employeeId, monthYear, salaryAmount, status) VALUES (?, ?, ?, ?)");
                psInsert.setInt(1, employeeId);
                psInsert.setString(2, monthYear);
                psInsert.setDouble(3, salaryAmount);
                psInsert.setString(4, status);
                psInsert.executeUpdate();
            }

            showSuccess("Salary record saved successfully!");
            searchSalary(null);

            employeeIdField.clear();
            monthPicker.setValue(null);
            paymentGroup.selectToggle(null);

        } catch (NumberFormatException e) {
            showError("Invalid Employee ID!");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error while saving salary!");
        }
    }




    @FXML
    void searchSalary(ActionEvent event) {
        salaryList.clear();
        try {
            if (employeeIdField.getText().isEmpty()) {
                showError("Enter Employee ID to search.");
                return;
            }

            int employeeId = Integer.parseInt(employeeIdField.getText());

            Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM salary WHERE employeeId=? ORDER BY monthYear DESC");
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                salaryList.add(new SalaryRecord(
                        rs.getString("monthYear"),
                        rs.getDouble("salaryAmount"),
                        rs.getString("status")
                ));
            }

            if (salaryList.isEmpty()) showError("No salary records found.");
            else showSuccess("Salary records loaded.");

        } catch (NumberFormatException e) {
            showError("Invalid Employee ID!");
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error while loading salary records!");
        }
    }

    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("employeeManagement");
    }

    private void showError(String msg) {
        message.setText(msg);
        message.setStyle("-fx-text-fill: red;");
    }

    private void showSuccess(String msg) {
        message.setText(msg);
        message.setStyle("-fx-text-fill: green;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMonthYear()));
        amountColumn.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getSalaryAmount()));
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        salaryTable.setItems(salaryList);
    }
}
