package com.example.animalfarm360.employeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.controller.EmployeeLogin;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDetailsInfo {
    @FXML
    private Label addressLabel;

    @FXML
    private Button backButton;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label dobLabel;

    @FXML
    private Label dojLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label emergencyNameLabel;

    @FXML
    private Label emergencyPhoneLabel;

    @FXML
    private Label employeeIdLabel;

    @FXML
    private Label employeeTypeLabel;

    @FXML
    private Label employmentStatusLabel;

    @FXML
    private Label genderLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label positionLabel;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label salaryLabel;

    @FXML
    private Label usernameLabel;



    @FXML
    public void initialize() {
        loadEmployeeDetails();
    }

    private void loadEmployeeDetails() {
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM employee WHERE employeeId = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, EmployeeLogin.loggedInEmployeeId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                dobLabel.setText(rs.getString("dob"));
                genderLabel.setText(rs.getString("gender"));
                phoneLabel.setText(rs.getString("phone"));
                addressLabel.setText(rs.getString("address"));
                emailLabel.setText(rs.getString("email"));
                employeeIdLabel.setText(String.valueOf(rs.getInt("employeeId")));
                departmentLabel.setText(rs.getString("department"));
                positionLabel.setText(rs.getString("position"));
                dojLabel.setText(rs.getString("doj"));
                employmentStatusLabel.setText(rs.getString("employmentStatus"));
                employeeTypeLabel.setText(rs.getString("employeetype"));
                usernameLabel.setText(rs.getString("username"));
                salaryLabel.setText(String.valueOf(rs.getDouble("salary")));
                emergencyNameLabel.setText(rs.getString("emergencyName"));
                emergencyPhoneLabel.setText(rs.getString("emergencyPhone"));
                String imagePath = rs.getString("image");
                if (imagePath != null && !imagePath.isEmpty()) {
                    try {

                        if (imagePath.startsWith("file:/")) {
                            imagePath = imagePath.replace("file:/", "");
                        }
                        imagePath = java.net.URLDecoder.decode(imagePath, "UTF-8");
                        File file = new File(imagePath);
                        Image img = file.exists()
                                ? new Image(file.toURI().toString())
                                : new Image(getClass().getResource("/images/default.png").toExternalForm());
                        profileImage.setImage(img);
                    } catch (Exception e) {
                        e.printStackTrace();
                        profileImage.setImage(new Image(getClass().getResource("/images/default.png").toExternalForm()));
                    }
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        HelloApplication.changeScene("employeeLogin");
    }
}
