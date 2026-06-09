package com.example.animalfarm360.controller;
import com.example.animalfarm360.HelloApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class About {
    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private Label review;

    @FXML
    void Review(MouseEvent event) {
     HelloApplication.changeScene("review");
    }

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    public void initialize() {


        loginCombo.setItems(FXCollections.observableArrayList(
                "Buyer",
                "Employee",
                "Admin"
        ));
    }


    @FXML
    void About(MouseEvent event) {
      HelloApplication.changeScene("about");
    }



    @FXML
    void Booking(MouseEvent event) {
    HelloApplication.changeScene("bookVisit");
    }

    @FXML
    void ContactInfo(MouseEvent event) {
      HelloApplication.changeScene("contact");
    }

    @FXML
    void Home(MouseEvent event) {
        HelloApplication.changeScene("home");
    }



    @FXML
    void Login(ActionEvent event) {
        String choice = loginCombo.getValue();
        if (choice == null) return;

        switch (choice) {
            case "Buyer":
                HelloApplication.changeScene("buyerLogin");
                break;

            case "Admin":
             HelloApplication.changeScene("adminLogin");
                break;

            case "Employee":
                HelloApplication.changeScene("employeeLogin");
                break;

        }


        loginCombo.setValue(null);
    }

}
