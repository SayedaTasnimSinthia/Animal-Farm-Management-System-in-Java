package com.example.animalfarm360.adminRelated_AnimalManagementRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.VaccinationRecord;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VaccinationManagement implements Initializable {
    @FXML
    private TextField breedField;

    @FXML
    private TextField categoryField;

    @FXML
    private TableColumn<VaccinationRecord, String> columnDateGiven;

    @FXML
    private TableColumn<VaccinationRecord, String> columnNextDueDate;

    @FXML
    private TableColumn<VaccinationRecord, String> columnStatus;

    @FXML
    private TableColumn<VaccinationRecord, String> columnVaccineName;

    @FXML
    private DatePicker dateGivenField;

    @FXML
    private DatePicker nextDueDateField;

    @FXML
    private RadioButton rbDone;

    @FXML
    private RadioButton rbMissed;

    @FXML
    private TextField searchAnimalIdField;

    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private TableView<VaccinationRecord> vaccinationTable;

    @FXML
    private TextField vaccineNameField;



    private ObservableList<VaccinationRecord> vaccinationList = FXCollections.observableArrayList();


    @FXML
    private void loadAnimal() {
        String animalIdText = searchAnimalIdField.getText();
        if (animalIdText.isEmpty()) return;
        int animalId = Integer.parseInt(animalIdText);

        try { Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT category, breed FROM animal WHERE animalId=?");
            ps.setInt(1, animalId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categoryField.setText(rs.getString("category"));
                breedField.setText(rs.getString("breed"));
            }


            vaccinationList.clear();
            PreparedStatement ps2 = connection.prepareStatement("SELECT vaccineName, dateGiven, nextDueDate, status FROM vaccinationrecord WHERE animalId=?");
            ps2.setInt(1, animalId);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                vaccinationList.add(new VaccinationRecord(
                        rs2.getString("vaccineName"),
                        rs2.getDate("dateGiven") != null ? rs2.getDate("dateGiven").toLocalDate() : null,
                        rs2.getDate("nextDueDate") != null ? rs2.getDate("nextDueDate").toLocalDate() : null,
                        rs2.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addVaccination() {
        String animalIdText = searchAnimalIdField.getText();
        if (animalIdText.isEmpty()) return;
        int animalId = Integer.parseInt(animalIdText);

        String vaccineName = vaccineNameField.getText();
        LocalDate dateGiven = dateGivenField.getValue();
        LocalDate nextDue = nextDueDateField.getValue();
        String status = rbDone.isSelected() ? "Done" : rbMissed.isSelected() ? "Missed" : "";

        if (vaccineName.isEmpty() || status.isEmpty()) return;

        try  {Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO vaccinationrecord (animalId, vaccineName, dateGiven, nextDueDate, status) VALUES (?,?,?,?,?)");
            ps.setInt(1, animalId);
            ps.setString(2, vaccineName);
            ps.setDate(3, dateGiven != null ? Date.valueOf(dateGiven) : null);
            ps.setDate(4, nextDue != null ? Date.valueOf(nextDue) : null);
            ps.setString(5, status);
            ps.executeUpdate();

            if ("Missed".equals(status)) {
                PreparedStatement ps2 = connection.prepareStatement("UPDATE animal SET systemFlag='Inactive' WHERE animalId=?");
                ps2.setInt(1, animalId);
                ps2.executeUpdate();
            }

            vaccinationList.add(new VaccinationRecord(vaccineName, dateGiven, nextDue, status));

            vaccineNameField.clear();
            dateGivenField.setValue(null);
            nextDueDateField.setValue(null);
            statusGroup.selectToggle(null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void back(ActionEvent event) {
        HelloApplication.changeScene("animalManagement");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnVaccineName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getVaccineName()));
        columnDateGiven.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDateGiven() != null ? c.getValue().getDateGiven().toString() : ""));
        columnNextDueDate.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getNextDueDate() != null ? c.getValue().getNextDueDate().toString() : ""));
        columnStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        vaccinationTable.setItems(vaccinationList);
    }
}
