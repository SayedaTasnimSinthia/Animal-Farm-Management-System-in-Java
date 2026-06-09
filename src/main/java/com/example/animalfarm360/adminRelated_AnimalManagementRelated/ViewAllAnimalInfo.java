package com.example.animalfarm360.adminRelated_AnimalManagementRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Animal;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class ViewAllAnimalInfo implements Initializable{
    @FXML
    private TextField animalIdField;

    @FXML
    private ImageView animalImageView;

    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TextField breedField;

    @FXML
    private TextField categoryField;

    @FXML
    private TableColumn<Animal, String> columnBreed;

    @FXML
    private TableColumn<Animal, String> columnCategory;

    @FXML
    private TableColumn<Animal, String> columnGender;

    @FXML
    private TableColumn<Animal, String> columnHealthStatus;

    @FXML
    private TableColumn<Animal, Number> columnId;

    @FXML
    private TableColumn<Animal, String> columnProductivity;

    @FXML
    private TableColumn<Animal, String> columnSaleStatus;

    @FXML
    private TableColumn<Animal, String> columnSystemFlag;

    @FXML
    private TextField dobField;

    @FXML
    private TextField eggCountField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField healthStatusField;

    @FXML
    private TextField meatYieldField;

    @FXML
    private TextField milkYieldField;

    @FXML
    private TextField notesField;

    @FXML
    private TextField productivityField;

    @FXML
    private TextField salePriceField;

    @FXML
    private TextField saleStatusField;

    @FXML
    private TextField searchActiveField;

    @FXML
    private TextField searchBreedField;

    @FXML
    private TextField searchCategoryField;

    @FXML
    private TextField searchIdField;

    @FXML
    private TextField searchSaleField;

    @FXML
    private TextField systemFlagField;

    @FXML
    private TextField woolField;



    private ObservableList<Animal> animalList = FXCollections.observableArrayList();
    private FilteredList<Animal> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        columnId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getAnimalId()));
        columnCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        columnBreed.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBreed()));
        columnGender.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGender()));
        columnHealthStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHealthStatus()));
        columnProductivity.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProductivity()));
        columnSaleStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSaleStatus()));
        columnSystemFlag.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSystemFlag()));

        loadAnimalsFromDatabase();


        animalTable.setItems(FXCollections.observableArrayList(animalList));

        animalTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) showAnimalDetails(newSel);});

        searchIdField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        searchCategoryField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        searchBreedField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        searchActiveField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        searchSaleField.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());

        applyFilters();
    }



    private void loadAnimalsFromDatabase() {
        try {
            Connection conn = ConnectionSingleton.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM animal");

            while(rs.next()) {
                Animal a = new Animal(
                        rs.getInt("animalId"),
                        rs.getString("category"),
                        rs.getString("breed"),
                        rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null,
                        rs.getString("gender"),
                        rs.getString("healthStatus"),
                        rs.getString("notes"),
                        rs.getString("productivity"),
                        rs.getObject("milkYield") != null ? rs.getDouble("milkYield") : null,
                        rs.getObject("eggCount") != null ? rs.getInt("eggCount") : null,
                        rs.getObject("meatYield") != null ? rs.getDouble("meatYield") : null,
                        rs.getObject("wool") != null ? rs.getDouble("wool") : null,
                        rs.getString("saleStatus"),
                        rs.getObject("salePrice") != null ? rs.getDouble("salePrice") : null,
                        rs.getString("systemFlag"),
                        rs.getString("image")
                );
                animalList.add(a);
            }

            filteredList = new FilteredList<>(animalList, p -> true);
            animalTable.setItems(filteredList);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    private void applyFilters() {
        String idFilter = searchIdField.getText().trim().toLowerCase();
        String categoryFilter = searchCategoryField.getText().trim().toLowerCase();
        String breedFilter = searchBreedField.getText().trim().toLowerCase();
        String activeFilter = searchActiveField.getText().trim().toLowerCase();
        String saleFilter = searchSaleField.getText().trim().toLowerCase();


    List<Animal> filtered = animalList.stream()
                .filter(a -> a != null)
                .filter(a -> idFilter.isEmpty() || String.valueOf(a.getAnimalId()).toLowerCase().startsWith(idFilter))
                .filter(a -> categoryFilter.isEmpty() || a.getCategory().toLowerCase().startsWith(categoryFilter))
                .filter(a -> breedFilter.isEmpty() || a.getBreed().toLowerCase().startsWith(breedFilter))
                .filter(a -> activeFilter.isEmpty() || a.getSystemFlag().toLowerCase().startsWith(activeFilter))
                .filter(a -> saleFilter.isEmpty() || a.getSaleStatus().toLowerCase().startsWith(saleFilter))
                .toList();


        animalTable.setItems(FXCollections.observableArrayList(filtered));
    }



    private void showAnimalDetails(Animal a) {
        animalIdField.setText(String.valueOf(a.getAnimalId()));
        categoryField.setText(a.getCategory());
        breedField.setText(a.getBreed());
        dobField.setText(a.getDob() != null ? a.getDob().toString() : "");
        genderField.setText(a.getGender());
        healthStatusField.setText(a.getHealthStatus());
        notesField.setText(a.getNotes());
        productivityField.setText(a.getProductivity());
        milkYieldField.setText(a.getMilkYield() != null ? String.valueOf(a.getMilkYield()) : "");
        eggCountField.setText(a.getEggCount() != null ? String.valueOf(a.getEggCount()) : "");
        meatYieldField.setText(a.getMeatYield() != null ? String.valueOf(a.getMeatYield()) : "");
        woolField.setText(a.getWool() != null ? String.valueOf(a.getWool()) : "");
        saleStatusField.setText(a.getSaleStatus());
        salePriceField.setText(a.getSalePrice() != null ? String.valueOf(a.getSalePrice()) : "");
        systemFlagField.setText(a.getSystemFlag());

        if(a.getImage() != null && !a.getImage().isEmpty()) {
            try {
                Image img = new Image(a.getImage(), true);
                animalImageView.setImage(img);
            } catch(Exception e) {
                animalImageView.setImage(null);
            }
        } else {
            animalImageView.setImage(null);
        }
    }

    @FXML
    void backAction(ActionEvent event) {
HelloApplication.changeScene("animalManagement");
    }


}