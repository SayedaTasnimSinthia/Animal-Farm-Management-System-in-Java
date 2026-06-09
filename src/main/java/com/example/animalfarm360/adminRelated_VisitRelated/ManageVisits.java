package com.example.animalfarm360.adminRelated_VisitRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Booking;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ManageVisits implements Initializable {
    @FXML
    private TableColumn<Booking, Boolean> approvedColumn;

    @FXML
    private ToggleGroup approvedGroup;

    @FXML
    private RadioButton approvedNoRadio;

    @FXML
    private RadioButton approvedYesRadio;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, Boolean> contactedColumn;

    @FXML
    private ToggleGroup contactedGroup;

    @FXML
    private RadioButton contactedNoRadio;

    @FXML
    private RadioButton contactedYesRadio;

    @FXML
    private TableColumn<Booking, String> dateColumn;

    @FXML
    private TableColumn<Booking, String> emailColumn;

    @FXML
    private TableColumn<Booking, String> nameColumn;

    @FXML
    private TableColumn<Booking, String> phoneColumn;

    @FXML
    private TableColumn<Booking, String> timeColumn;



    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();


    @FXML
    void BackAction(ActionEvent event) {
        HelloApplication.changeScene("adminLogin");
    }



    @FXML
    void saveChanges(ActionEvent event) {
        Booking selected = bookingTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a booking to update.");
            return;
        }

        boolean contacted = contactedYesRadio.isSelected();
        boolean approved = approvedYesRadio.isSelected();

        String updateQuery = "UPDATE bookings SET contacted=?, approved=? WHERE email=? AND booking_date=? AND booking_time=?";

        try {
            Connection conn = ConnectionSingleton.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateQuery);
            ps.setBoolean(1, contacted);
            ps.setBoolean(2, approved);
            ps.setString(3, selected.getEmail());
            ps.setDate(4, Date.valueOf(selected.getDate()));
            ps.setString(5, selected.getTime());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                showAlert("Booking updated successfully!");
                loadBookings();
            } else {
                showAlert("Failed to update booking.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database error occurred.");
        }
    }

    @FXML
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }



    private void loadBookings() {
        bookingList.clear();
        String query = "SELECT * FROM bookings ORDER BY booking_date DESC, booking_time ASC";
        try {
            Connection conn = ConnectionSingleton.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                bookingList.add(new Booking(rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("booking_date").toLocalDate(),
                        rs.getString("booking_time"),
                        rs.getInt("visitors"),
                        rs.getString("notes"),
                        rs.getBoolean("contacted"),
                        rs.getBoolean("approved")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        contactedColumn.setCellValueFactory(new PropertyValueFactory<>("contacted"));
        approvedColumn.setCellValueFactory(new PropertyValueFactory<>("approved"));

        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        emailColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        phoneColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhone()));
        dateColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate().toString()));
        timeColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        contactedColumn.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isContacted()).asObject());
        approvedColumn.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isApproved()).asObject());

        bookingTable.setItems(bookingList);

        loadBookings();

        contactedGroup.selectToggle(null);
        approvedGroup.selectToggle(null);

        bookingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                if (newSelection.isContacted()) {contactedYesRadio.setSelected(true);}
                else {contactedNoRadio.setSelected(true);}


                if (newSelection.isApproved()) {approvedYesRadio.setSelected(true);}
                else {approvedNoRadio.setSelected(true);}

            }

            else {
                contactedGroup.selectToggle(null);
                approvedGroup.selectToggle(null);
            }});

    }


}
