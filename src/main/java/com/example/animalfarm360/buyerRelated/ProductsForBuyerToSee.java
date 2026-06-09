package com.example.animalfarm360.buyerRelated;


import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
        import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ProductsForBuyerToSee implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private AnchorPane animalContainer;

    @FXML
    private AnchorPane productContainer;
    @FXML
    void back(ActionEvent event) {
        HelloApplication.changeScene("buyerLogin");
    }


    @FXML
    void searchProduct() {
        String keyword = searchField.getText().trim();
        animalContainer.getChildren().clear();
        productContainer.getChildren().clear();
        loadAnimals(keyword);
        loadProducts(keyword);
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
                } else {
                    x += 420;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProducts(String keyword) {
        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM animal_product WHERE status='Active' AND (productName LIKE ? OR productType LIKE ?)");
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
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
                        rs.getInt("productId"));
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
        addBtn.setLayoutY(140);
        addBtn.setOnAction(e -> addToCart("animal", id));

        card.getChildren().addAll(img, nameLabel, dobLabel, genderLabel, healthLabel, priceLabel, addBtn);
        return card;
    }

    private AnchorPane createProductCard(String imagePath, String name, String type,
                                         String source, double price, int id) {
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
        addBtn.setOnAction(e -> addToCart("animal_product", id));

        card.getChildren().addAll(img, nameLabel, sourceLabel, priceLabel, addBtn);
        return card;
    }

    private void addToCart(String type, int id) {
        int buyerId = 1;

        try {Connection con = ConnectionSingleton.getConnection();

            PreparedStatement check = con.prepareStatement("SELECT 1 FROM cart WHERE buyer_id=? AND product_type=? AND product_id=?");
            check.setInt(1, buyerId);
            check.setString(2, type);
            check.setInt(3, id);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                showAlert("Product is already in your cart!", Alert.AlertType.WARNING);
                return;
            }


            PreparedStatement ps = con.prepareStatement("INSERT INTO cart (buyer_id, product_type, product_id) VALUES (?, ?, ?)");
            ps.setInt(1, buyerId);
            ps.setString(2, type);
            ps.setInt(3, id);
            ps.executeUpdate();

            showAlert("Added to cart!", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error adding to cart: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void goToCart() {
        HelloApplication.changeScene("cart");
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAnimals("");
        loadProducts("");
    }
}
