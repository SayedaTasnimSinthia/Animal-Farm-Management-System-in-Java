package com.example.animalfarm360.adminRelated_AnimalManagementRelated;


import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.AnimalProduct;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
        import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAllAnimalBasedProduct  implements Initializable {
    @FXML
    private TextField breedField;


    @FXML
    private TableColumn<AnimalProduct, String> columnAnimal;

    @FXML
    private TableColumn<AnimalProduct, String> columnBreed;

    @FXML
    private TableColumn<AnimalProduct, Number> columnId;

    @FXML
    private TableColumn<AnimalProduct, String> columnName;

    @FXML
    private TableColumn<AnimalProduct, Number> columnPrice;

    @FXML
    private TableColumn<AnimalProduct, Number> columnQuantity;

    @FXML
    private TableColumn<AnimalProduct, String> columnType;

    @FXML
    private TableColumn<AnimalProduct, String> columnUnit;

    @FXML
    private TableColumn<AnimalProduct, String> columnStatus;

    @FXML
    private TextField notesField;

    @FXML
    private TextField productIdField;

    @FXML
    private ImageView productImageView;

    @FXML
    private TextField productNameField;

    @FXML
    private TableView<AnimalProduct> productTable;

    @FXML
    private TextField productTypeField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField salePriceField;



    @FXML
    private TextField searchAnimalField;

    @FXML
    private TextField searchBreedField;

    @FXML
    private TextField searchIdField;

    @FXML
    private TextField searchTypeField;

    @FXML
    private TextField sourceAnimalField;

    @FXML
    private TextField unitField;
    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("animalManagement");
    }

    private ObservableList<AnimalProduct> productList = FXCollections.observableArrayList();

    private void loadProducts() {
        productList.clear();
        String sql = "SELECT * FROM animal_product";
        try {
            Connection con = ConnectionSingleton.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String status = rs.getString("status");

                if (status == null) status = "Active";
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
                      status);
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadProductDetails(AnimalProduct product) {
        productIdField.setText(String.valueOf(product.getProductId()));
        productNameField.setText(product.getProductName());
        productTypeField.setText(product.getProductType());
        sourceAnimalField.setText(product.getSourceAnimal());
        breedField.setText(product.getBreed());
        quantityField.setText(product.getQuantity() != null ? String.valueOf(product.getQuantity()) : "");
        unitField.setText(product.getUnit());
        salePriceField.setText(product.getSalePrice() != null ? String.valueOf(product.getSalePrice()) : "");
        notesField.setText(product.getNotes());
        if (product.getImage() != null) {productImageView.setImage(new Image(product.getImage()));}
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnId.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getProductId()));
        columnName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProductName()));
        columnType.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProductType()));
        columnAnimal.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSourceAnimal()));
        columnBreed.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBreed()));
        columnQuantity.setCellValueFactory(cell -> new SimpleDoubleProperty(
                cell.getValue().getQuantity() != null ? cell.getValue().getQuantity() : 0));
        columnUnit.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getUnit() != null ? cell.getValue().getUnit() : ""));
        columnPrice.setCellValueFactory(cell -> new SimpleDoubleProperty(
                cell.getValue().getSalePrice() != null ? cell.getValue().getSalePrice() : 0));
        columnStatus.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getStatus() != null ? cell.getValue().getStatus() : "Active"));

        loadProducts();


        productTable.setItems(FXCollections.observableArrayList(productList));


        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) loadProductDetails(newSel);});


        searchIdField.textProperty().addListener((obs, oldVal, newVal) -> updateTableFilter());
        searchTypeField.textProperty().addListener((obs, oldVal, newVal) -> updateTableFilter());
        searchAnimalField.textProperty().addListener((obs, oldVal, newVal) -> updateTableFilter());
        searchBreedField.textProperty().addListener((obs, oldVal, newVal) -> updateTableFilter());


        updateTableFilter();
    }


    private void updateTableFilter() {
        String idFilter = searchIdField.getText().toLowerCase().trim();
        String typeFilter = searchTypeField.getText().toLowerCase().trim();
        String animalFilter = searchAnimalField.getText().toLowerCase().trim();
        String breedFilter = searchBreedField.getText().toLowerCase().trim();

        List<AnimalProduct> filtered = productList.stream()
                .filter(p -> p != null)
                .filter(p -> idFilter.isEmpty() || String.valueOf(p.getProductId()).toLowerCase().startsWith(idFilter))
                .filter(p -> typeFilter.isEmpty() || p.getProductType().toLowerCase().startsWith(typeFilter))
                .filter(p -> animalFilter.isEmpty() || p.getSourceAnimal().toLowerCase().startsWith(animalFilter))
                .filter(p -> breedFilter.isEmpty() || p.getBreed().toLowerCase().startsWith(breedFilter))
                .toList();

        productTable.setItems(FXCollections.observableArrayList(filtered));
    }





}
