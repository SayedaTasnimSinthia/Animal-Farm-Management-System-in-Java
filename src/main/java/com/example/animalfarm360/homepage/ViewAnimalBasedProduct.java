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

public class ViewAnimalBasedProduct implements Initializable {
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



    private void loadProducts() {
        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM animal_product WHERE status='Active'"
            );
            ResultSet rs = ps.executeQuery();

            double x = 10, y = 10;
            int col = 0;

            while (rs.next()) {
                AnchorPane card = createProductCard(
                        rs.getString("image"),
                        rs.getString("productName"),
                        rs.getString("productType"),
                        rs.getString("sourceAnimal"),
                        rs.getDouble("salePrice"),
                        rs.getInt("productId")
                );

                card.setLayoutX(x);
                card.setLayoutY(y);
                productContainer.getChildren().add(card);

                col++;
                if (col % 2 == 0) {
                    x = 10;
                    y += 190;
                } else {
                    x += 420;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AnchorPane createProductCard(String imagePath, String name, String type, String source, double price, int id) {
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
                if (f.exists()) img.setImage(new Image(f.toURI().toString()));
                else img.setImage(new Image(getClass().getResource("/images/no_image.png").toExternalForm()));
            } else {
                img.setImage(new Image(getClass().getResource("/images/no_image.png").toExternalForm()));
            }
        } catch (Exception e) { e.printStackTrace(); }

        Label nameLabel = new Label(name + " (" + type + ")");
        nameLabel.setLayoutX(140);
        nameLabel.setLayoutY(20);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2a2fbf;");

        Label sourceLabel = new Label("Source: " + source);
        sourceLabel.setLayoutX(140);
        sourceLabel.setLayoutY(50);
        sourceLabel.setStyle("-fx-text-fill: #555;");

        Label priceLabel = new Label("৳ " + price);
        priceLabel.setLayoutX(140);
        priceLabel.setLayoutY(80);
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button addBtn = new Button("Add to Cart");
        addBtn.setLayoutX(140);
        addBtn.setLayoutY(120);
        addBtn.setStyle("-fx-background-color: #50c6b4; -fx-text-fill: white; -fx-font-weight: bold;");
        addBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Required");
            alert.setHeaderText(null);
            alert.setContentText("Please login to add products to the cart!");
            alert.showAndWait();
        });

        card.getChildren().addAll(img, nameLabel, sourceLabel, priceLabel, addBtn);
        return card;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginCombo.setItems(FXCollections.observableArrayList("Buyer", "Employee", "Admin"));
        loadProducts();
    }
}