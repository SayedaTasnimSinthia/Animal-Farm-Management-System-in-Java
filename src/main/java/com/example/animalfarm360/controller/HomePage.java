package com.example.animalfarm360.controller;

import com.example.animalfarm360.HelloApplication;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HomePage {

    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private ImageView image;

    @FXML
    private ComboBox<String> loginCombo;
    @FXML
    private Label review;

    @FXML
    void Review(MouseEvent event) {
     HelloApplication.changeScene("review");
    }

    @FXML
    public void initialize() {

        String url = "https://static.vecteezy.com/system/resources/previews/049/924/436/non_2x/pasture-farm-with-cows-vector.jpg";
        Image i = new Image(url, true);
        image.setImage(i);


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
    void AnimalBasedProducts(ActionEvent event) {
    HelloApplication.changeScene("homeViewAnimalProduct");

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
    void LiveAnimals(ActionEvent event) {
      HelloApplication.changeScene("homeViewAnimals");
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