package com.example.animalfarm360.adminRelated_EmployeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewEmployee implements Initializable {

    @FXML
    private TextField addressField;

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
    private TextField employeeTypeField;

    @FXML
    private ToggleGroup employmentStatusGroup;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> positionComboBox;

    @FXML
    private TextField salaryField;

    @FXML
    private TextField usernameField;




    private Image employeeImage;



    private void updatePositions() {
        positionComboBox.getItems().clear();

        String department = departmentComboBox.getValue();
        if (department == null) return;

        switch (department) {
            case "Animal Care" -> positionComboBox.getItems().addAll("Veterinarian", "Animal Caretaker",
                    "Farm Worker", "Livestock Nutritionist");


            case "Farm Management" -> positionComboBox.getItems().addAll("Farm Manager", "Operations Supervisor",
                    "Task Coordinator");


            case "Sales & Marketing" -> positionComboBox.getItems().addAll("Sales Executive", "Product Manager",
                    "Marketing Officer");


            case "Customer Relations" -> positionComboBox.getItems().addAll("Customer Service Representative",
                    "Booking Coordinator","Feedback Manager");


            case "Finance" -> positionComboBox.getItems().addAll("Accountant",
                    "Finance Officer", "Payroll Specialist");


            case "HR" -> positionComboBox.getItems().addAll("HR Manager",
                    "Attendance & Payroll Officer", "Administrative Assistant");


        }
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            employeeImage = new Image(file.toURI().toString());
            imageView.setImage(employeeImage);
        }
    }

    @FXML
    void saveEmployee(ActionEvent event) {
        try {

            if (nameField.getText().isEmpty()) {
                showAlert("Full Name is required!", Alert.AlertType.ERROR);
                nameField.requestFocus(); return;
            }
            String name = nameField.getText();


            if (dobField.getValue() == null) {
                showAlert("Date of Birth is required!", Alert.AlertType.ERROR);
                dobField.requestFocus(); return;
            }
            LocalDate dob = dobField.getValue();
            if (dob.isAfter(LocalDate.now())) {
                showAlert("Date of Birth cannot be in the future!", Alert.AlertType.ERROR);
                dobField.requestFocus(); return;
            }
            String dateOfBirth = dob.toString();


            if (genderGroup.getSelectedToggle() == null) {
                showAlert("Please select a gender!", Alert.AlertType.ERROR); return;
            }
            String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();


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


            if (addressField.getText().isEmpty()) {
                showAlert("Address is required!", Alert.AlertType.ERROR);
                addressField.requestFocus(); return;
            }
            String address = addressField.getText();

            if (emailField.getText().isEmpty()) {
                showAlert("Email is required!", Alert.AlertType.ERROR);
                emailField.requestFocus(); return;
            }
            String email = emailField.getText();




            if (!email.contains("@") || !email.contains(".")) {
                showAlert("Invalid email format! Must contain '@' and '.'", Alert.AlertType.ERROR);
                emailField.requestFocus();
                return;
            }

            int atIndex = email.indexOf("@");
            int lastDotIndex = email.lastIndexOf(".");

            if (atIndex <= 0 || lastDotIndex <= atIndex + 1 || lastDotIndex >= email.length() - 1) {
                showAlert("Invalid email format!", Alert.AlertType.ERROR);
                emailField.requestFocus();
                return;
            }


            if (employeeIdField.getText().isEmpty()) {
                showAlert("Employee ID is required!", Alert.AlertType.ERROR);
                employeeIdField.requestFocus(); return;
            }
            int employeeId;
            try {
                employeeId = Integer.parseInt(employeeIdField.getText());
            } catch (NumberFormatException e) {
                showAlert("Employee ID must be a number!", Alert.AlertType.ERROR);
                employeeIdField.requestFocus(); return;
            }


            if (departmentComboBox.getValue() == null) {
                showAlert("Please select a department!", Alert.AlertType.ERROR);
                departmentComboBox.requestFocus(); return;
            }
            String department = departmentComboBox.getValue();


            if (positionComboBox.getValue() == null) {
                showAlert("Please select a position!", Alert.AlertType.ERROR);
                positionComboBox.requestFocus(); return;
            }
            String position = positionComboBox.getValue();


            if (dojField.getValue() == null) {
                showAlert("Date of Joining is required!", Alert.AlertType.ERROR);
                dojField.requestFocus(); return;
            }


            LocalDate doj = dojField.getValue();
            if (doj.isAfter(LocalDate.now())) {
                showAlert("Date of Joining cannot be in the future!", Alert.AlertType.ERROR);
                dojField.requestFocus(); return;
            }
            if (dob.plusYears(18).isAfter(doj)) {
                showAlert("Employee must be at least 18 years old on the Date of Joining!", Alert.AlertType.ERROR);
                dobField.requestFocus(); return;
            }
            String dateOfJoining = doj.toString();

            if (employmentStatusGroup.getSelectedToggle() == null) {
                showAlert("Please select an employment status!", Alert.AlertType.ERROR); return;
            }
            String employmentStatus = ((RadioButton) employmentStatusGroup.getSelectedToggle()).getText();


            if (employeeTypeField.getText().isEmpty()) {
                showAlert("Employee Type is required!", Alert.AlertType.ERROR);
                employeeTypeField.requestFocus(); return;
            }
            String employeetype = employeeTypeField.getText();


            if (usernameField.getText().isEmpty()) {
                showAlert("Username is required!", Alert.AlertType.ERROR);
                usernameField.requestFocus(); return;
            }
            String username = usernameField.getText();


            if (passwordField.getText().isEmpty()) {
                showAlert("Password is required!", Alert.AlertType.ERROR);
                passwordField.requestFocus(); return;
            }
            String password = passwordField.getText();


            if (salaryField.getText().isEmpty()) {
                showAlert("Salary is required!", Alert.AlertType.ERROR);
                salaryField.requestFocus(); return;
            }
            double salary;
            try {
                salary = Double.parseDouble(salaryField.getText());
            } catch (NumberFormatException e) {
                showAlert("Salary must be a valid number!", Alert.AlertType.ERROR);
                salaryField.requestFocus(); return;
            }


            if (emergencyNameField.getText().isEmpty()) {
                showAlert("Emergency Contact Name is required!", Alert.AlertType.ERROR);
                emergencyNameField.requestFocus(); return;
            }
            String emergencyContactName = emergencyNameField.getText();


            if (emergencyPhoneField.getText().isEmpty()) {
                showAlert("Emergency Contact Phone is required!", Alert.AlertType.ERROR);
                emergencyPhoneField.requestFocus(); return;
            }
            if (emergencyPhoneField.getText().length() != 11 || !emergencyPhoneField.getText().startsWith("01")) {
                showAlert("Emergency phone must be 11 digits and start with 01!", Alert.AlertType.ERROR);
                emergencyPhoneField.requestFocus(); return;
            }

            try { Long.parseLong(emergencyPhoneField.getText()); }
            catch (NumberFormatException e) {
                showAlert("Emergency phone must contain only digits!", Alert.AlertType.ERROR);
                emergencyPhoneField.requestFocus(); return;
            }
            String emergencyContactPhone = emergencyPhoneField.getText();


            if (employeeImage == null) {
                showAlert("Please upload a profile picture before saving!", Alert.AlertType.ERROR);
                return;
            }
            Image image = employeeImage;


            Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement psCheck = connection.prepareStatement("SELECT * FROM employee WHERE employeeId = ?");
            psCheck.setInt(1, employeeId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                showAlert("Employee ID already exists! Please use a different ID.", Alert.AlertType.ERROR); employeeIdField.requestFocus(); return; }

            String query = "INSERT INTO employee (name, dob, gender, phone, address, email, employeeId, department, position, doj, employmentStatus, employeetype, username, password, salary, emergencyName, emergencyPhone, image) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, nameField.getText());
            ps.setDate(2, Date.valueOf(dob));
            ps.setString(3, gender);
            ps.setString(4, phoneField.getText());
            ps.setString(5, addressField.getText());
            ps.setString(6, emailField.getText());
            ps.setInt(7, employeeId);
            ps.setString(8, department);
            ps.setString(9, position);
            ps.setDate(10, Date.valueOf(doj));
            ps.setString(11, employmentStatus);
            ps.setString(12, employeeTypeField.getText());
            ps.setString(13, usernameField.getText());
            ps.setString(14, passwordField.getText());
            ps.setDouble(15, salary);
            ps.setString(16, emergencyNameField.getText());
            ps.setString(17, emergencyPhoneField.getText());
            ps.setString(18, employeeImage.getUrl());

            ps.executeUpdate();
            showAlert("Employee saved successfully!", Alert.AlertType.INFORMATION);
            clearFields();

        } catch (SQLException e) {
            showAlert("Error saving employee!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        dobField.setValue(null);
        genderGroup.selectToggle(null);
        phoneField.clear();
        addressField.clear();
        emailField.clear();
        employeeIdField.clear();
        departmentComboBox.setValue(null);
        positionComboBox.setValue(null);
        dojField.setValue(null);
        employmentStatusGroup.selectToggle(null);
        employeeTypeField.clear();
        usernameField.clear();
        passwordField.clear();
        salaryField.clear();
        emergencyNameField.clear();
        emergencyPhoneField.clear();
        imageView.setImage(null);
        employeeImage = null;
        positionComboBox.getItems().clear();
    }



    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("employeeManagement");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentComboBox.getItems().addAll("Animal Care", "Farm Management", "Sales & Marketing",
                "Customer Relations", "Finance", "HR");


        departmentComboBox.setOnAction(e -> updatePositions());

    }


}
