package com.example.animalfarm360.adminRelated_EmployeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.EmployeeInfo;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ViewAllEmployeeInformation implements Initializable {
    @FXML
    private TextField addressField;

    @FXML
    private TableColumn<EmployeeInfo, String> columnDepartment;

    @FXML
    private TableColumn<EmployeeInfo, String> columnEmployeeType;

    @FXML
    private TableColumn<EmployeeInfo, Number> columnId;

    @FXML
    private TableColumn<EmployeeInfo, String> columnName;

    @FXML
    private TableColumn<EmployeeInfo, String> columnPhone;

    @FXML
    private TableColumn<EmployeeInfo, String> columnPosition;

    @FXML
    private TableColumn<EmployeeInfo, String> columnStatus;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField dobField;

    @FXML
    private TextField dojField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField emergencyNameField;

    @FXML
    private TextField emergencyPhoneField;

    @FXML
    private TextField employeeIdField;

    @FXML
    private ImageView employeeImageView;

    @FXML
    private TableView<EmployeeInfo> employeeTable;

    @FXML
    private TextField employeeTypeField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField usernameField;

    private ObservableList<EmployeeInfo> employeeList = FXCollections.observableArrayList();


    private void loadEmployees() {
        employeeList.clear();
        try {
            Connection conn = ConnectionSingleton.getConnection();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM employee");

            while (rs.next()) {
                employeeList.add(new EmployeeInfo(
                        rs.getString("name"),
                        rs.getDate("dob").toString(),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getInt("employeeId"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getDate("doj").toString(),
                        rs.getString("employmentStatus"),
                        rs.getString("employeetype"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDouble("salary"),
                        rs.getString("emergencyName"),
                        rs.getString("emergencyPhone"),
                        rs.getString("image")));}

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void RowClick(MouseEvent event) {
        EmployeeInfo emp = employeeTable.getSelectionModel().getSelectedItem();
        if (emp != null) {
            nameField.setText(emp.getName());
            employeeIdField.setText(String.valueOf(emp.getEmployeeId()));
            departmentField.setText(emp.getDepartment());
            positionField.setText(emp.getPosition());
            phoneField.setText(emp.getPhoneNumber());
            statusField.setText(emp.getEmploymentStatus());
            employeeTypeField.setText(emp.getEmployeetype());
            emailField.setText(emp.getEmail());
            addressField.setText(emp.getAddress());
            dobField.setText(emp.getDateOfBirth());
            dojField.setText(emp.getDateOfJoining());
            genderField.setText(emp.getGender());
            usernameField.setText(emp.getUsername());
            passwordField.setText(emp.getPassword());
            salaryField.setText(String.valueOf(emp.getSalary()));
            emergencyNameField.setText(emp.getEmergencyContactName());
            emergencyPhoneField.setText(emp.getEmergencyContactPhone());

            if (emp.getImage() != null && !emp.getImage().isEmpty()) {
                employeeImageView.setImage(new Image(emp.getImage()));
            }
        }
    }


    @FXML
    private void searchEmployee(ActionEvent event) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            employeeTable.setItems(employeeList);
            return;
        }


        ObservableList<EmployeeInfo> filteredList = FXCollections.observableArrayList();
        for (EmployeeInfo employeeInfo : employeeList) {
            if (employeeInfo.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(employeeInfo.getEmployeeId()).equals(keyword)) {
                filteredList.add(employeeInfo);
            }
        }
        employeeTable.setItems(filteredList);
    }

    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("employeeManagement");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getEmployeeId()));
        columnName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        columnDepartment.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDepartment()));
        columnPosition.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));
        columnPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhoneNumber()));
        columnStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmploymentStatus()));
        columnEmployeeType.setCellValueFactory(c -> new  SimpleStringProperty(c.getValue().getEmployeetype()));

        loadEmployees();

        employeeTable.setItems(employeeList);
        employeeTable.setOnMouseClicked(this::RowClick);
    }
}
