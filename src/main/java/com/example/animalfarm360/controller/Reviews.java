package com.example.animalfarm360.controller;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Review;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reviews {


    @FXML
    private Label about;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private TextArea feedbackField;

    @FXML
    private Label home;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<Integer> ratingBox;

    @FXML
    private Label review;

    @FXML
    private AnchorPane reviewPanel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button submit;


    private double nextY = 10;

    private List<Review> reviews = new ArrayList<>();

    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review");
    }

    @FXML
    public void initialize() {
        ratingBox.getItems().addAll(1, 2, 3, 4, 5);

        loadReviewsFromDatabase();

        loginCombo.setItems(FXCollections.observableArrayList("Buyer", "Employee", "Admin"));
    }

    @FXML
    void SubmitAction(ActionEvent event) {
        String name = nameField.getText().trim();
        String feedback = feedbackField.getText().trim();
        Integer rating = ratingBox.getValue();

        if (name.isEmpty() || feedback.isEmpty() || rating == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please fill all fields!");
            alert.showAndWait();
            return;
        }

        String sql = "INSERT INTO feedback (name, review, rating) VALUES (?, ?, ?)";

        try{
            Connection connection = ConnectionSingleton.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql) ;

            ps.setString(1, name);
            ps.setString(2, feedback);
            ps.setInt(3, rating);
            ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Database Error!");
            alert.showAndWait();
            return;
        }

        Review reviewObj = new Review(name, feedback, rating);
        reviews.add(reviewObj);
        addReviewBlock(name, feedback, rating);

        nameField.clear();
        feedbackField.clear();
        ratingBox.setValue(null);
    }


    private void loadReviewsFromDatabase() {
        String sql = "SELECT * FROM feedback";

        try (Connection connection = ConnectionSingleton.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String reviewText = rs.getString("review");
                int rating = rs.getInt("rating");

                Review reviewObj = new Review(name, reviewText, rating);
                reviews.add(reviewObj);

                addReviewBlock(name, reviewText, rating);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Failed to load reviews!");
            alert.showAndWait();
        }
    }

    private void addReviewBlock(String name, String feedback, int rating) {
        AnchorPane reviewBlock = new AnchorPane();
        reviewBlock.setPrefWidth(520);
        reviewBlock.setLayoutY(nextY);


        Text nameLabelText = new Text("Name: ");
        nameLabelText.setFill(Color.web("#29756b"));
        Text nameValueText = new Text(name + "\n");


        Text ratingLabelText = new Text("Review: ");
        ratingLabelText.setFill(Color.web("#29756b"));
        Text ratingValueText = new Text(rating + "/5\n");


        Text feedbackLabelText = new Text("Feedback: ");
        feedbackLabelText.setFill(Color.web("#29756b"));
        Text feedbackValueText = new Text(feedback + "\n");


        Text separator = new Text("-------------------------------------------------------------------------------------------------------------------------------------------\n");

        TextFlow textFlow = new TextFlow(nameLabelText, nameValueText, ratingLabelText, ratingValueText, feedbackLabelText, feedbackValueText, separator);
        textFlow.prefWidthProperty().bind(reviewPanel.widthProperty().subtract(20));
        textFlow.setLayoutX(10);
        textFlow.setLayoutY(0);
        textFlow.setLineSpacing(5);

        reviewBlock.getChildren().add(textFlow);
        reviewPanel.getChildren().add(reviewBlock);

        nextY += textFlow.getBoundsInParent().getHeight();
        reviewPanel.setPrefHeight(nextY + 20);
    }


    @FXML
    void About(MouseEvent event) { HelloApplication.changeScene("about"); }



    @FXML
    void Booking(MouseEvent event) {    HelloApplication.changeScene("bookVisit"); }

    @FXML
    void ContactInfo(MouseEvent event) { HelloApplication.changeScene("contact"); }

    @FXML
    void Home(MouseEvent event) { HelloApplication.changeScene("home"); }



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
