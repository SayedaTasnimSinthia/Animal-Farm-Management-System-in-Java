package com.example.animalfarm360.adminRelated_AnimalManagementRelated;

import com.example.animalfarm360.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AnimalManagement {



    @FXML
    void addAnimal(ActionEvent event) {
        HelloApplication.changeScene("addNewAnimal");
    }

    @FXML
    void addAnimalBasedInfo(ActionEvent event) {
        HelloApplication.changeScene("addNewAnimalBasedProduct");
    }

    @FXML
    void editAnimalBasedProduct(ActionEvent event) {
        HelloApplication.changeScene("editAnimalBasedProductInfo");
    }

    @FXML
    void editAnimalInfo(ActionEvent event) {
        HelloApplication.changeScene("editAnimalInfo");
    }

    @FXML
    void logout(ActionEvent event) {
        HelloApplication.changeScene("adminLogin");
    }

    @FXML
    void vaccination(ActionEvent event) {
        HelloApplication.changeScene("vaccinationManagement");
    }

    @FXML
    void viewAllAnimalInfo(ActionEvent event) {
        HelloApplication.changeScene("viewAllAnimalInfo");
    }

    @FXML
    void viewAnimalBasedProduct(ActionEvent event) {
        HelloApplication.changeScene("viewAllAnimalBasedProduct");
    }


}

