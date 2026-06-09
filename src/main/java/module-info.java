module com.example.animalfarm360 {
    requires javafx.controls;
    requires javafx.fxml;
requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;


    opens com.example.animalfarm360.homepage to javafx.fxml;
    opens com.example.animalfarm360.adminRelated_ManageCustomer to javafx.fxml;
    opens com.example.animalfarm360.buyerRelated to javafx.fxml;
    opens com.example.animalfarm360.employeeRelated to javafx.fxml;
    exports com.example.animalfarm360.employeeRelated to javafx.fxml;
    opens com.example.animalfarm360.adminRelated_AnimalManagementRelated to javafx.fxml;
    exports com.example.animalfarm360.adminRelated_VisitRelated;
    opens com.example.animalfarm360.adminRelated_VisitRelated to javafx.fxml;
    opens com.example.animalfarm360 to javafx.fxml;
    exports com.example.animalfarm360;
    exports com.example.animalfarm360.controller;
    opens com.example.animalfarm360.controller to javafx.fxml;
    exports com.example.animalfarm360.util;
    opens com.example.animalfarm360.util to javafx.fxml;
    exports com.example.animalfarm360.adminRelated_EmployeeRelated;
    opens com.example.animalfarm360.adminRelated_EmployeeRelated to javafx.fxml;
    exports com.example.animalfarm360.adminRelated_AnimalManagementRelated;
}