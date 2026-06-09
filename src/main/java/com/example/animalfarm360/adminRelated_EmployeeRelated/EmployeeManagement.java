package com.example.animalfarm360.adminRelated_EmployeeRelated;

import com.example.animalfarm360.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EmployeeManagement {

    @FXML
    void Attandence(ActionEvent event) {
HelloApplication.changeScene("manageAttendance");
    }

    @FXML
    void addEmployee(ActionEvent event) {
HelloApplication.changeScene("addNewEmployee");
    }



    @FXML
    void editEmployee(ActionEvent event) {
HelloApplication.changeScene("editEmployeeInfo");
    }

    @FXML
    void manageSalaries(ActionEvent event) {
        HelloApplication.changeScene("manageSalary");
    }

    @FXML
    void viewAllEmployeeInfo(ActionEvent event) {
HelloApplication.changeScene("viewAllEmployeeInformation");
    }

    @FXML
    void logout(ActionEvent event) {
        HelloApplication.changeScene("adminLogin");
    }

    @FXML
    void AttandenceProg(ActionEvent event) {
HelloApplication.changeScene("attendanceProgress");
    }

}
