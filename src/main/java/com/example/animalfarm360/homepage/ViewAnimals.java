package com.example.animalfarm360.homepage;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;



public class ViewAnimals implements Initializable {
    @FXML
    private Label about;

    @FXML
    private AnchorPane animalContainer;

    @FXML
    private Label bookVisit;

    @FXML
    private Label contact;

    @FXML
    private Label home;

    @FXML
    private ComboBox<String> loginCombo;

    @FXML
    private Label review;

    @FXML
    private TextField searchField;



    @FXML
    private AnchorPane productContainer;

    @FXML
    void Review(MouseEvent event) {
        HelloApplication.changeScene("review");
    }



    @FXML
    void About(MouseEvent event) {
        HelloApplication.changeScene("about");
    }



    @FXML
    void Booking(MouseEvent event) {
        HelloApplication.changeScene("bookVisit");
    }

    @FXML
    void ContactInfo(MouseEvent event) {
        HelloApplication.changeScene("contact");
    }

    @FXML
    void Home(MouseEvent event) {
        HelloApplication.changeScene("home");
    }



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



    private void loadAnimals(String keyword) {
        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM animal WHERE saleStatus='Available' AND (category LIKE ? OR breed LIKE ?)");
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();


            double x = 10, y = 10;
            int col = 0;
            int rowCount = 0;

            animalContainer.getChildren().clear();

            while (rs.next()) {
                AnchorPane card = createAnimalCard(
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("breed"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("healthStatus"),
                        rs.getDouble("salePrice"),
                        rs.getInt("animalId")
                );

                card.setLayoutX(x);
                card.setLayoutY(y);
                animalContainer.getChildren().add(card);

                col++;
                if (col % 2 == 0) {
                    x = 10;
                    y += 190;
                    rowCount++;
                } else {
                    x += 420;
                }
            }

            int totalRows = (col + 1) / 2;
            animalContainer.setPrefHeight(totalRows * 190 + 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private AnchorPane createAnimalCard(String imagePath, String category, String breed, String dob, String gender, String health, double price, int id) {
        AnchorPane card = new AnchorPane();
        card.setPrefSize(400, 180);
        card.setStyle("-fx-border-color: #50c6b4; -fx-border-radius: 8; -fx-background-color: #f9f9f9;");

        ImageView img = new ImageView();
        img.setFitWidth(120);
        img.setFitHeight(120);
        img.setLayoutX(10);
        img.setLayoutY(20);

        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                String path = imagePath.replace("file:/", "").replace("%20", " ");
                File f = new File(path);
                if (f.exists()) {
                    img.setImage(new Image(f.toURI().toString()));
                } else {
                    img.setImage(new Image(getClass().getResource("/images/no_image.png").toExternalForm()));
                }
            } else {
                img.setImage(new Image(getClass().getResource("/images/no_image.png").toExternalForm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Label nameLabel = new Label(category + " (" + breed + ")");
        nameLabel.setLayoutX(140);
        nameLabel.setLayoutY(20);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2a2fbf;");

        Label dobLabel = new Label("DOB: " + dob);
        dobLabel.setLayoutX(140);
        dobLabel.setLayoutY(50);
        dobLabel.setStyle("-fx-text-fill: #555;");

        Label genderLabel = new Label("Gender: " + gender);
        genderLabel.setLayoutX(140);
        genderLabel.setLayoutY(70);
        genderLabel.setStyle("-fx-text-fill: #555;");

        Label healthLabel = new Label("Health: " + health);
        healthLabel.setLayoutX(140);
        healthLabel.setLayoutY(90);
        healthLabel.setStyle("-fx-text-fill: #555;");

        Label priceLabel = new Label("৳ " + price);
        priceLabel.setLayoutX(140);
        priceLabel.setLayoutY(120);
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");


        Button addBtn = new Button("Add to Cart");
        addBtn.setLayoutX(140);
        addBtn.setLayoutY(150);
        addBtn.setStyle("-fx-background-color: #50c6b4; -fx-text-fill: white;");


        addBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please login to add products to the cart!");
            alert.showAndWait();
        });

        card.getChildren().addAll(img, nameLabel, dobLabel, genderLabel, healthLabel, priceLabel, addBtn);
        return card;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAnimals("");
        loginCombo.setItems(FXCollections.observableArrayList(
                "Buyer",
                "Employee",
                "Admin"
        ));

    }

}