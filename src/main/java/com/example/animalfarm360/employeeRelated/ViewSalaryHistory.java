package com.example.animalfarm360.employeeRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.controller.EmployeeLogin;
import com.example.animalfarm360.model.Salary;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ViewSalaryHistory implements Initializable {

    @FXML
    private TableView<Salary> salaryTable;

    @FXML
    private TableColumn<Salary, String> monthColumn;

    @FXML
    private TableColumn<Salary, Double> amountColumn;

    @FXML
    private TableColumn<Salary, String> statusColumn;

    @FXML
    private Button backButton;

    private ObservableList<Salary> salaryList = FXCollections.observableArrayList();


    private void loadSalaryHistory(int employeeId) {
        salaryList.clear();
        try {
            Connection connection = ConnectionSingleton.getConnection();
            String query = "SELECT monthYear, salaryAmount, status FROM salary WHERE employeeId = ? ORDER BY monthYear DESC";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, employeeId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                salaryList.add(new Salary(
                        rs.getString("monthYear"),
                        rs.getDouble("salaryAmount"),
                        rs.getString("status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        HelloApplication.changeScene("employeeLogin");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMonthYear()));
        amountColumn.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getSalaryAmount()).asObject());
        statusColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        salaryTable.setItems(salaryList);
        if (EmployeeLogin.loggedInEmployeeId != 0) {loadSalaryHistory(EmployeeLogin.loggedInEmployeeId);}
    }
}
