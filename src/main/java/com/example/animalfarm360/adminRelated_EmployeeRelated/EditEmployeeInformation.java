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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditEmployeeInformation implements Initializable {
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
    private ComboBox<String> departmentComboBox;

    @FXML
    private DatePicker dobField;

    @FXML
    private DatePicker dojField;

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
    private ComboBox<String> positionComboBox;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField usernameField;



    private ObservableList<EmployeeInfo> employeeList = FXCollections.observableArrayList();
    private Image employeeImage;


    private void clearFields() {
        employeeIdField.clear();
        nameField.clear();
        dobField.setValue(null);
        dojField.setValue(null);
        genderField.clear();
        phoneField.clear();
        addressField.clear();
        emailField.clear();
        departmentComboBox.setValue(null);
        positionComboBox.setValue(null);
        statusField.clear();
        employeeTypeField.clear();
        usernameField.clear();
        passwordField.clear();
        salaryField.clear();
        emergencyNameField.clear();
        emergencyPhoneField.clear();
        employeeImageView.setImage(null);
    }




    private void updatePositions() {
        positionComboBox.getItems().clear();
        String department = departmentComboBox.getValue();
        if (department == null) return;

        switch (department) {
            case "Animal Care" -> positionComboBox.getItems().addAll("Veterinarian",
                    "Animal Caretaker", "Farm Worker", "Livestock Nutritionist");


            case "Farm Management" -> positionComboBox.getItems().addAll("Farm Manager",
                    "Operations Supervisor", "Task Coordinator");


            case "Sales & Marketing" -> positionComboBox.getItems().addAll("Sales Executive",
                    "Product Manager", "Marketing Officer");


            case "Customer Relations" -> positionComboBox.getItems().addAll("Customer Service Representative",
                    "Booking Coordinator","Feedback Manager");


            case "Finance" -> positionComboBox.getItems().addAll("Accountant",
                    "Finance Officer", "Payroll Specialist");


            case "HR" -> positionComboBox.getItems().addAll(
                    "HR Manager", "Attendance & Payroll Officer", "Administrative Assistant");


        }
    }

    private void loadEmployees() {
        employeeList.clear();
        try (Connection conn = ConnectionSingleton.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employee")) {

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
                        rs.getString("image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rowClick(MouseEvent event) {
        EmployeeInfo emp = employeeTable.getSelectionModel().getSelectedItem();
        if (emp != null) {
            nameField.setText(emp.getName());
            employeeIdField.setText(String.valueOf(emp.getEmployeeId()));
            departmentComboBox.setValue(emp.getDepartment());
            updatePositions();
            positionComboBox.setValue(emp.getPosition());
            phoneField.setText(emp.getPhoneNumber());
            statusField.setText(emp.getEmploymentStatus());
            employeeTypeField.setText(emp.getEmployeetype());
            emailField.setText(emp.getEmail());
            addressField.setText(emp.getAddress());
            dobField.setValue(LocalDate.parse(emp.getDateOfBirth()));
            dojField.setValue(LocalDate.parse(emp.getDateOfJoining()));
            genderField.setText(emp.getGender());
            usernameField.setText(emp.getUsername());
            passwordField.setText(emp.getPassword());
            salaryField.setText(String.valueOf(emp.getSalary()));
            emergencyNameField.setText(emp.getEmergencyContactName());
            emergencyPhoneField.setText(emp.getEmergencyContactPhone());

            if (emp.getImage() != null && !emp.getImage().isEmpty()) {
                employeeImage = new Image(emp.getImage());
                employeeImageView.setImage(employeeImage);
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
        for (EmployeeInfo emp : employeeList) {
            if (emp.getName().toLowerCase().startsWith(keyword.toLowerCase()) ||
                    String.valueOf(emp.getEmployeeId()).equals(keyword)) {
                filteredList.add(emp);
            }
        }
        employeeTable.setItems(filteredList);
    }

    @FXML
    private void updateEmployee(ActionEvent event) {
        try {
            if (employeeIdField.getText().isEmpty()) {
                showAlert("Employee ID is required!", Alert.AlertType.ERROR);
                return;
            }

            int employeeId = Integer.parseInt(employeeIdField.getText());
            String name = nameField.getText();
            LocalDate dob = dobField.getValue();
            LocalDate doj = dojField.getValue();
            String gender = genderField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String department = departmentComboBox.getValue();
            String position = positionComboBox.getValue();
            String employmentStatus = statusField.getText();
            String employeetype = employeeTypeField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            String emergencyName = emergencyNameField.getText();
            String emergencyPhone = emergencyPhoneField.getText();


            String updateQuery = "UPDATE employee SET name=?, dob=?, gender=?, phone=?, address=?, email=?, department=?, position=?, doj=?, employmentStatus=?, employeetype=?, username=?, password=?, salary=?, emergencyName=?, emergencyPhone=?, image=? WHERE employeeId=?";
            try {
                Connection conn = ConnectionSingleton.getConnection();
                 PreparedStatement ps = conn.prepareStatement(updateQuery);

                ps.setString(1, name);
                ps.setDate(2, Date.valueOf(dob));
                ps.setString(3, gender);
                ps.setString(4, phone);
                ps.setString(5, address);
                ps.setString(6, email);
                ps.setString(7, department);
                ps.setString(8, position);
                ps.setDate(9, Date.valueOf(doj));
                ps.setString(10, employmentStatus);
                ps.setString(11, employeetype);
                ps.setString(12, username);
                ps.setString(13, password);
                ps.setDouble(14, salary);
                ps.setString(15, emergencyName);
                ps.setString(16, emergencyPhone);
                ps.setString(17, employeeImage.getUrl());
                ps.setInt(18, employeeId);

                ps.executeUpdate();
            }catch (Exception e) {
                e.printStackTrace();
                showAlert("Error updating employee info!", Alert.AlertType.ERROR);
            }

            showAlert("Employee info updated successfully!", Alert.AlertType.INFORMATION);
            clearFields();
            loadEmployees();
            employeeTable.setItems(employeeList);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error updating employee info!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void backAction(ActionEvent event) {
        HelloApplication.changeScene("employeeManagement");
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentComboBox.getItems().addAll("Animal Care", "Farm Management", "Sales & Marketing",
                "Customer Relations", "Finance", "HR");

        departmentComboBox.setOnAction(e -> updatePositions());

        columnId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getEmployeeId()));
        columnName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        columnDepartment.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDepartment()));
        columnPosition.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));
        columnPhone.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhoneNumber()));
        columnStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmploymentStatus()));
        columnEmployeeType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmployeetype()));

        loadEmployees();

        employeeTable.setItems(employeeList);
        employeeTable.setOnMouseClicked(this::rowClick);
    }
}
