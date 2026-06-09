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
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddNewAnimal implements Initializable {


    @FXML
    private TextField animalIdField;

    @FXML
    private ImageView animalImageView;

    @FXML
    private ComboBox<String> breedComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private DatePicker dobField;

    @FXML
    private TextField eggCountField;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private ToggleGroup healthStatusGroup;

    @FXML
    private TextField meatYieldField;

    @FXML
    private TextField milkYieldField;

    @FXML
    private TextArea notesField;

    @FXML
    private ToggleGroup productivityGroup;

    @FXML
    private TextField salePriceField;

    @FXML
    private ToggleGroup saleStatusGroup;

    @FXML
    private ToggleGroup systemFlagGroup;

    @FXML
    private TextField woolField;



    private Image animalImage;

    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("animalManagement");
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(null);
        if(file != null){
            animalImage = new Image(file.toURI().toString());
            animalImageView.setImage(animalImage);
        }
    }

    @FXML
    void saveAnimal(ActionEvent event){
        try {

            if(animalIdField.getText().isEmpty()){
                showAlert("Animal ID is required!", Alert.AlertType.ERROR);
                return; }

            int animalId = Integer.parseInt(animalIdField.getText());

            if(categoryComboBox.getValue() == null){
                showAlert("Select a category!", Alert.AlertType.ERROR); return; }
            String category = categoryComboBox.getValue();
            String breed = breedComboBox.getValue();

            if(breed == null || breed.isEmpty()){
                showAlert("Select a breed!", Alert.AlertType.ERROR); return; }


            LocalDate dob = dobField.getValue();
            String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();
            String healthStatus = ((RadioButton)healthStatusGroup.getSelectedToggle()).getText();
            String notes = notesField.getText();

            String productivity = ((RadioButton)productivityGroup.getSelectedToggle()).getText();
            Double milkYield = milkYieldField.getText().isEmpty()?null:Double.parseDouble(milkYieldField.getText());
            Integer eggCount = eggCountField.getText().isEmpty()?null:Integer.parseInt(eggCountField.getText());
            Double meatYield = meatYieldField.getText().isEmpty()?null:Double.parseDouble(meatYieldField.getText());
            Double wool = woolField.getText().isEmpty()?null:Double.parseDouble(woolField.getText());

            String saleStatus = ((RadioButton)saleStatusGroup.getSelectedToggle()).getText();
            Double salePrice = salePriceField.getText().isEmpty()?null:Double.parseDouble(salePriceField.getText());
            String systemFlag = ((RadioButton)systemFlagGroup.getSelectedToggle()).getText();

            if(animalImage == null){ showAlert("Upload animal image!", Alert.AlertType.ERROR); return; }

            Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement psCheck = connection.prepareStatement("SELECT * FROM animal WHERE animalId = ?");
            psCheck.setInt(1, animalId);
            ResultSet rs = psCheck.executeQuery();
            if(rs.next()){ showAlert("Animal ID already exists!", Alert.AlertType.ERROR); return; }

            String query = "INSERT INTO animal (animalId, category, breed, dob, gender, healthStatus, notes, productivity, milkYield, eggCount, meatYield, wool, saleStatus, salePrice, systemFlag, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, animalId);
            ps.setString(2, category);
            ps.setString(3, breed);
            ps.setDate(4,dob!=null?Date.valueOf(dob):null);
            ps.setString(5,gender);
            ps.setString(6,healthStatus);
            ps.setString(7,notes);
            ps.setString(8,productivity);
            ps.setObject(9,milkYield);
            ps.setObject(10,eggCount);
            ps.setObject(11,meatYield);
            ps.setObject(12,wool);
            ps.setString(13,saleStatus);
            ps.setObject(14,salePrice);
            ps.setString(15,systemFlag);
            ps.setString(16,animalImage.getUrl());

            ps.executeUpdate();
            showAlert("Animal saved successfully!", Alert.AlertType.INFORMATION);
            clearFields();

        } catch(Exception e){
            e.printStackTrace();
            showAlert("Error saving animal!", Alert.AlertType.ERROR);}
    }

    private void showAlert(String msg, Alert.AlertType type){
        Alert a = new Alert(type);
        a.setTitle(type==Alert.AlertType.ERROR?"Error":"Success");
        a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void clearFields(){
        animalIdField.clear();
        categoryComboBox.setValue(null);
        dobField.setValue(null);
        genderGroup.selectToggle(null);
        healthStatusGroup.selectToggle(null);
        productivityGroup.selectToggle(null);
        milkYieldField.clear();
        eggCountField.clear();
        meatYieldField.clear();
        woolField.clear();
        saleStatusGroup.selectToggle(null);
        salePriceField.clear();
        systemFlagGroup.selectToggle(null);
        notesField.clear();
        animalImageView.setImage(null);
        animalImage=null;
        breedComboBox.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        categoryComboBox.getItems().addAll("Goat","Sheep","Cow","Chicken","Rabbit","Geese");
        categoryComboBox.setOnAction(e -> updateBreeds());
    }

    private void updateBreeds() {
        breedComboBox.getItems().clear();
        String category = categoryComboBox.getValue();
        if(category == null) return;

        switch(category){
            case "Goat" -> breedComboBox.getItems().addAll("Boer", "Kalahari Red", "Saanen");
            case "Sheep" -> breedComboBox.getItems().addAll("Merino", "Dorper", "Suffolk");
            case "Cow" -> breedComboBox.getItems().addAll("Holstein", "Jersey", "Guernsey");
            case "Chicken" -> breedComboBox.getItems().addAll("Rhode Island Red", "Leghorn", "Plymouth Rock");
            case "Rabbit" -> breedComboBox.getItems().addAll("New Zealand", "Flemish Giant", "Mini Rex");
            case "Geese" -> breedComboBox.getItems().addAll("Embden", "Toulouse", "Pilgrim");
        }
    }
}
