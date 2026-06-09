package com.example.animalfarm360.adminRelated_AnimalManagementRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class EditAnimalInfo {

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
    private TextField searchAnimalIdField;

    @FXML
    private ToggleGroup systemFlagGroup;

    @FXML
    private TextField woolField;


    private String selectedImagePath;


    @FXML
    void loadAnimal(ActionEvent event) {
        String id = searchAnimalIdField.getText();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter Animal ID.");
            return;
        }

        try (Connection conn = ConnectionSingleton.getConnection()) {
            String sql = "SELECT * FROM animal WHERE animalId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                animalIdField.setText(String.valueOf(rs.getInt("animalId")));
                categoryComboBox.setValue(rs.getString("category"));
                breedComboBox.setValue(rs.getString("breed"));
                if (rs.getDate("dob") != null) {
                    dobField.setValue(rs.getDate("dob").toLocalDate());
                }
                selectRadioByValue(genderGroup, rs.getString("gender"));
                selectRadioByValue(healthStatusGroup, rs.getString("healthStatus"));
                notesField.setText(rs.getString("notes"));
                selectRadioByValue(productivityGroup, rs.getString("productivity"));
                milkYieldField.setText(rs.getString("milkYield"));
                eggCountField.setText(rs.getString("eggCount"));
                meatYieldField.setText(rs.getString("meatYield"));
                woolField.setText(rs.getString("wool"));
                selectRadioByValue(saleStatusGroup, rs.getString("saleStatus"));
                salePriceField.setText(rs.getString("salePrice"));
                selectRadioByValue(systemFlagGroup, rs.getString("systemFlag"));

                String imagePath = rs.getString("image");
                if (imagePath != null && !imagePath.isEmpty()) {
                    try {
                        animalImageView.setImage(new Image(imagePath));
                        selectedImagePath = imagePath;
                    } catch (Exception e) {
                        e.printStackTrace();
                        animalImageView.setImage(null);
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "No animal found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading animal: " + e.getMessage());
        }
    }






    @FXML
    void updateAnimal(ActionEvent event) {
        if (animalIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please load an animal first.");
            return;
        }

        try (Connection conn = ConnectionSingleton.getConnection()) {
            String sql = "UPDATE animal SET category=?, breed=?, dob=?, gender=?, healthStatus=?, notes=?, " +
                    "productivity=?, milkYield=?, eggCount=?, meatYield=?, wool=?, saleStatus=?, salePrice=?, systemFlag=?, image=? " +
                    "WHERE animalId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, categoryComboBox.getValue());
            stmt.setString(2, breedComboBox.getValue());
            stmt.setDate(3, dobField.getValue() != null ? Date.valueOf(dobField.getValue()) : null);
            stmt.setString(4, getSelectedRadioText(genderGroup));
            stmt.setString(5, getSelectedRadioText(healthStatusGroup));
            stmt.setString(6, notesField.getText());
            stmt.setString(7, getSelectedRadioText(productivityGroup));

            stmt.setBigDecimal(8, safeText(milkYieldField).isEmpty() ? null : new java.math.BigDecimal(safeText(milkYieldField)));
            stmt.setInt(9, safeText(eggCountField).isEmpty() ? 0 : Integer.parseInt(safeText(eggCountField)));
            stmt.setBigDecimal(10, safeText(meatYieldField).isEmpty() ? null : new java.math.BigDecimal(safeText(meatYieldField)));
            stmt.setBigDecimal(11, safeText(woolField).isEmpty() ? null : new java.math.BigDecimal(safeText(woolField)));
            stmt.setString(12, getSelectedRadioText(saleStatusGroup));
            stmt.setBigDecimal(13, safeText(salePriceField).isEmpty() ? null : new java.math.BigDecimal(safeText(salePriceField)));
            stmt.setString(14, getSelectedRadioText(systemFlagGroup));
            stmt.setString(15, selectedImagePath);
            stmt.setInt(16, Integer.parseInt(animalIdField.getText()));

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Animal updated successfully.");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update animal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error updating animal: " + e.getMessage());
        }
    }

    private void clearFields() {
        animalIdField.clear();
        searchAnimalIdField.clear();
        categoryComboBox.setValue(null);
        breedComboBox.setValue(null);
        dobField.setValue(null);
        genderGroup.selectToggle(null);
        healthStatusGroup.selectToggle(null);
        productivityGroup.selectToggle(null);
        saleStatusGroup.selectToggle(null);
        systemFlagGroup.selectToggle(null);
        notesField.clear();
        milkYieldField.clear();
        eggCountField.clear();
        meatYieldField.clear();
        woolField.clear();
        salePriceField.clear();
        animalImageView.setImage(null);
        selectedImagePath = null;
    }

    private String safeText(TextField field) {
        return field != null && field.getText() != null ? field.getText().trim() : "";
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Animal Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImagePath = file.toURI().toString();
            animalImageView.setImage(new Image(selectedImagePath));
        }
    }

      @FXML
    void backAction(ActionEvent event)  {
        HelloApplication.changeScene("animalManagement");
    }


    private void selectRadioByValue(ToggleGroup group, String value) {
        if (value == null) return;
        for (Toggle toggle : group.getToggles()) {
            RadioButton rb = (RadioButton) toggle;
            if (rb.getText().equalsIgnoreCase(value)) {
                group.selectToggle(rb);
                break;
            }
        }
    }

    private String getSelectedRadioText(ToggleGroup group) {
        if (group.getSelectedToggle() != null) {
            return ((RadioButton) group.getSelectedToggle()).getText();
        }
        return null;
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
