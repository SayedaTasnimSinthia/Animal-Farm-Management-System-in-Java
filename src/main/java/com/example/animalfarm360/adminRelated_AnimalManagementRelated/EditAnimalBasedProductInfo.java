package com.example.animalfarm360.adminRelated_AnimalManagementRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.AnimalProduct;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EditAnimalBasedProductInfo {

    @FXML
    private ComboBox<String> breedComboBox;

    @FXML
    private TextArea notesField;

    @FXML
    private TextField productIdField;

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField productNameField;

    @FXML
    private ComboBox<String> productTypeComboBox;

    @FXML
    private DatePicker productionDateField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField salePriceField;

    @FXML
    private TextField searchProductIdField;

    @FXML
    private ComboBox<String> sourceAnimalComboBox;

    @FXML
    private ComboBox<String> unitComboBox;

    @FXML
    private RadioButton activeRadio;

    @FXML
    private RadioButton inactiveRadio;

    @FXML
    private ToggleGroup statusToggleGroup;

    private File selectedImageFile;

    @FXML
    public void initialize() {
        productTypeComboBox.getItems().addAll("Milk", "Egg", "Ghee", "Cheese", "Honey", "Meat");
        unitComboBox.getItems().addAll("Liters", "Kg", "Pieces");
        sourceAnimalComboBox.getItems().addAll("Cow", "Sheep", "Chicken", "Goat", "Goose");
        breedComboBox.getItems().addAll("Holstein", "Jersey", "Rhode Island Red", "Merino");
    }

    @FXML
    private void loadProduct() {
        try {
            int productId = Integer.parseInt(searchProductIdField.getText());

            String sql = "SELECT * FROM animal_product WHERE productId=?";
            try (Connection con = ConnectionSingleton.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, productId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    AnimalProduct product = new AnimalProduct(
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getString("productType"),
                            rs.getString("breed"),
                            rs.getString("sourceAnimal"),
                            rs.getDate("productionDate") != null ? rs.getDate("productionDate").toLocalDate() : null,
                            rs.getDouble("quantity"),
                            rs.getString("unit"),
                            rs.getDouble("salePrice"),
                            rs.getString("notes"),
                            rs.getString("image"),
                            rs.getString("status")
                    );
                    populateFields(product);
                } else {
                    showAlert("No product found with this ID.", Alert.AlertType.WARNING);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading product: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateProduct() {
        try {
            String sql = "UPDATE animal_product SET productName=?, productType=?, sourceAnimal=?, breed=?, productionDate=?, quantity=?, unit=?, salePrice=?, notes=?, image=?, status=? WHERE productId=?";
            try (Connection con = ConnectionSingleton.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, productNameField.getText());
                ps.setString(2, productTypeComboBox.getValue());
                ps.setString(3, sourceAnimalComboBox.getValue());
                ps.setString(4, breedComboBox.getValue());
                ps.setDate(5, productionDateField.getValue() != null ? Date.valueOf(productionDateField.getValue()) : null);
                ps.setDouble(6, Double.parseDouble(quantityField.getText()));
                ps.setString(7, unitComboBox.getValue());
                ps.setDouble(8, Double.parseDouble(salePriceField.getText()));
                ps.setString(9, notesField.getText());

                String imagePath;
                if (selectedImageFile != null) {
                    imagePath = selectedImageFile.toURI().toString();
                } else {
                    imagePath = productImageView.getImage() != null ? productImageView.getImage().getUrl() : null;
                }
                ps.setString(10, imagePath);


                String status = activeRadio.isSelected() ? "Active" : "Inactive";
                ps.setString(11, status);

                ps.setInt(12, Integer.parseInt(productIdField.getText()));

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showAlert("Product updated successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Product update failed!", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            showAlert("Error updating product: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type == Alert.AlertType.ERROR ? "Error" : "Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void populateFields(AnimalProduct product) {
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getProductName());
        productTypeComboBox.setValue(product.getProductType());
        sourceAnimalComboBox.setValue(product.getSourceAnimal());
        breedComboBox.setValue(product.getBreed());
        productionDateField.setValue(product.getProductionDate());
        quantityField.setText(product.getQuantity() != null ? String.valueOf(product.getQuantity()) : "");
        unitComboBox.setValue(product.getUnit());
        salePriceField.setText(product.getSalePrice() != null ? String.valueOf(product.getSalePrice()) : "");
        notesField.setText(product.getNotes());
        if (product.getImage() != null) {
            productImageView.setImage(new Image(product.getImage()));
        }

        if ("Active".equalsIgnoreCase(product.getStatus())) {
            activeRadio.setSelected(true);
        } else {
            inactiveRadio.setSelected(true);
        }
    }

    @FXML
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(productImageView.getScene().getWindow());
        if (selectedImageFile != null) {
            productImageView.setImage(new Image(selectedImageFile.toURI().toString()));
        }
    }

    @FXML
    private void backAction() {
        HelloApplication.changeScene("animalManagement");
    }
}
