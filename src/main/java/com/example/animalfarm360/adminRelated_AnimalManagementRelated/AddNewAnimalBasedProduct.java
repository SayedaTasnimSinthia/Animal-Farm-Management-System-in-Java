package com.example.animalfarm360.adminRelated_AnimalManagementRelated;


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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewAnimalBasedProduct implements Initializable {

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private ComboBox<String> productTypeComboBox;

    @FXML
    private ComboBox<String> sourceAnimalComboBox;

    @FXML
    private ComboBox<String> breedComboBox;

    @FXML
    private DatePicker productionDateField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox<String> unitComboBox;

    @FXML
    private TextField salePriceField;

    @FXML
    private TextArea notesField;

    @FXML
    private ImageView productImageView;

    private Image productImage;

    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("animalManagement");
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if (file != null) {
            productImage = new Image(file.toURI().toString());
            productImageView.setImage(productImage);
        }
    }

    @FXML
    void saveProduct(ActionEvent event) {
        try {
            if (productIdField.getText().isEmpty()) {
                showAlert("Product ID is required!", Alert.AlertType.ERROR);
                return;
            }
            int productId = Integer.parseInt(productIdField.getText());

            if (productTypeComboBox.getValue() == null) {
                showAlert("Select a product type!", Alert.AlertType.ERROR);
                return;
            }

            String productType = productTypeComboBox.getValue();
            String sourceAnimal = sourceAnimalComboBox.getValue();
            String breed = breedComboBox.getValue();
            String productName = productNameField.getText();
            LocalDate productionDate = productionDateField.getValue();

            Double quantity = null;
            try {
                if (!quantityField.getText().isEmpty()) {
                    quantity = Double.parseDouble(quantityField.getText());
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity format!", Alert.AlertType.ERROR);
                return;
            }

            String unit = unitComboBox.getValue();

            Double salePrice = null;
            try {
                if (!salePriceField.getText().isEmpty()) {
                    salePrice = Double.parseDouble(salePriceField.getText());
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid sale price format!", Alert.AlertType.ERROR);
                return;
            }

            String notes = notesField.getText();

            if (productImage == null) {
                showAlert("Upload product image!", Alert.AlertType.ERROR);
                return;
            }

            try { Connection connection = ConnectionSingleton.getConnection();
                 PreparedStatement psCheck = connection.prepareStatement("SELECT * FROM animal_product WHERE productId = ?");
                psCheck.setInt(1, productId);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    showAlert("Product ID already exists!", Alert.AlertType.ERROR);
                    return;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            String query = "INSERT INTO animal_product (productId, productName, productType, breed, sourceAnimal, productionDate, quantity, unit, salePrice, notes, image) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            try (Connection connection = ConnectionSingleton.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {

                ps.setInt(1, productId);
                ps.setString(2, productName);
                ps.setString(3, productType);
                ps.setString(4, breed);
                ps.setString(5, sourceAnimal);
                ps.setDate(6, productionDate != null ? Date.valueOf(productionDate) : null);
                ps.setObject(7, quantity);
                ps.setString(8, unit);
                ps.setObject(9, salePrice);
                ps.setString(10, notes);
                ps.setString(11, productImage.getUrl());

                ps.executeUpdate();
                showAlert("Product saved successfully!", Alert.AlertType.INFORMATION);
                clearFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error saving product: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Success");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void clearFields() {
        productIdField.clear();
        productNameField.clear();
        productTypeComboBox.setValue(null);
        sourceAnimalComboBox.setValue(null);
        breedComboBox.setValue(null);
        productionDateField.setValue(null);
        quantityField.clear();
        unitComboBox.setValue(null);
        salePriceField.clear();
        notesField.clear();
        productImageView.setImage(null);
        productImage = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTypeComboBox.getItems().addAll("Milk", "Ghee", "Egg", "Meat", "Honey", "Cheese");
        sourceAnimalComboBox.getItems().addAll("Cow", "Sheep", "Goat", "Chicken", "Geese", "Bee");
        unitComboBox.getItems().addAll("Liters", "Kg", "Count");

        sourceAnimalComboBox.setOnAction(e -> updateBreed());
    }

    private void updateBreed() {
        breedComboBox.getItems().clear();
        String animal = sourceAnimalComboBox.getValue();
        if (animal == null) return;

        switch (animal) {
            case "Cow" -> breedComboBox.getItems().addAll("Holstein", "Jersey", "Guernsey");
            case "Sheep" -> breedComboBox.getItems().addAll("Merino", "Dorper", "Suffolk");
            case "Goat" -> breedComboBox.getItems().addAll("Boer", "Saanen", "Kalahari Red");
            case "Chicken" -> breedComboBox.getItems().addAll("Leghorn", "Rhode Island Red", "Plymouth Rock");
            case "Geese" -> breedComboBox.getItems().addAll("Embden", "Toulouse", "Pilgrim");
            case "Bee" -> breedComboBox.getItems().add("Apis Mellifera");
        }
    }
}
