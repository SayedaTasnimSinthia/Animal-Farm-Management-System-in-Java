package com.example.animalfarm360.adminRelated_ManageCustomer;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ConfirmOrder implements Initializable {

    @FXML
    private AnchorPane ordersContainer;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadOrders();
    }


    @FXML
    void backAction(ActionEvent event) {
        HelloApplication.changeScene("adminLogin");
    }

    private void loadOrders() {
        ordersContainer.getChildren().clear();
        double y = 10;

        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement psOrders = con.prepareStatement("SELECT * FROM orders ORDER BY order_date DESC");
            ResultSet rsOrders = psOrders.executeQuery();

            while (rsOrders.next()) {
                int orderId = rsOrders.getInt("order_id");
                String buyerName = rsOrders.getString("buyer_name");
                String phone = rsOrders.getString("phone");
                String address = rsOrders.getString("address");

                AnchorPane orderCard = new AnchorPane();
                orderCard.setPrefSize(840, 100);
                orderCard.setLayoutX(10);
                orderCard.setLayoutY(y);
                orderCard.setStyle("-fx-border-color: #50c6b4; -fx-background-color: #f9f9f9; -fx-border-radius: 8;");

                Label buyerInfo = new Label("Buyer: " + buyerName + " | Phone: " + phone + " | Address: " + address);
                buyerInfo.setLayoutX(10);
                buyerInfo.setLayoutY(10);
                buyerInfo.setStyle("-fx-font-weight: bold;");
                orderCard.getChildren().add(buyerInfo);

                PreparedStatement psItems = con.prepareStatement("SELECT * FROM order_items WHERE order_id=?");
                psItems.setInt(1, orderId);
                ResultSet rsItems = psItems.executeQuery();

                double itemY = 40;
                while (rsItems.next()) {
                    int itemId = rsItems.getInt("order_item_id");
                    int productId = rsItems.getInt("product_id");
                    String productName = rsItems.getString("product_name");
                    String type = rsItems.getString("product_type");
                    double price = rsItems.getDouble("price");
                    String status = rsItems.getString("status");

                    AnchorPane itemCard = new AnchorPane();
                    itemCard.setPrefSize(820, 40);
                    itemCard.setLayoutX(10);
                    itemCard.setLayoutY(itemY);

                    Label productLabel = new Label(productName + " (" + type + ") - ৳" + price);
                    productLabel.setLayoutX(10);
                    productLabel.setLayoutY(10);


                    Label statusLabel = new Label("Status: " + status);
                    statusLabel.setLayoutX(400);
                    statusLabel.setLayoutY(10);
                    switch (status) {
                        case "Pending" -> statusLabel.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                        case "Confirmed" -> statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        case "Cancelled" -> statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    }

                    Button confirmBtn = new Button("Confirm");
                    confirmBtn.setLayoutX(650);
                    confirmBtn.setLayoutY(5);
                    confirmBtn.setOnAction(e -> {
                        confirmProduct(itemId, productId, type);
                        loadOrders();});

                    Button declineBtn = new Button("Decline");
                    declineBtn.setLayoutX(730);
                    declineBtn.setLayoutY(5);
                    declineBtn.setOnAction(e -> {
                        declineProduct(itemId);
                        loadOrders();});


                    if ("Pending".equals(status)) {
                        itemCard.getChildren().addAll(productLabel, statusLabel, confirmBtn, declineBtn);
                    } else {
                        itemCard.getChildren().addAll(productLabel, statusLabel);
                    }

                    orderCard.getChildren().add(itemCard);
                    itemY += 45;
                }

                ordersContainer.getChildren().add(orderCard);
                y += Math.max(100, itemY + 10);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading orders: " + e.getMessage());
        }
    }

    private void confirmProduct(int itemId, int productId, String type) {
        try {
            Connection con = ConnectionSingleton.getConnection();
            if (type.equals("animal_product")) {
                PreparedStatement check = con.prepareStatement("SELECT status FROM animal_product WHERE productId=?");
                check.setInt(1, productId);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getString("status").equals("Inactive")) {
                    showAlert("This product is already confirmed by another admin.");
                    return;
                }
            } else if (type.equals("animal")) {
                PreparedStatement check = con.prepareStatement("SELECT saleStatus FROM animal WHERE animalId=?");
                check.setInt(1, productId);
                ResultSet rs = check.executeQuery();
                if (rs.next() && rs.getString("saleStatus").equals("Sold")) {
                    showAlert("This animal is already confirmed by another admin.");
                    return;
                }
            }

            PreparedStatement ps = con.prepareStatement("UPDATE order_items SET status='Confirmed' WHERE order_item_id=?");
            ps.setInt(1, itemId);
            ps.executeUpdate();

            if (type.equals("animal_product")) {
                PreparedStatement psProd = con.prepareStatement("UPDATE animal_product SET status='Inactive' WHERE productId=?");
                psProd.setInt(1, productId);
                psProd.executeUpdate();
            } else if (type.equals("animal")) {
                PreparedStatement psAnimal = con.prepareStatement("UPDATE animal SET saleStatus='Sold' WHERE animalId=?");
                psAnimal.setInt(1, productId);
                psAnimal.executeUpdate();
            }

            showAlert("Product confirmed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error confirming product: " + e.getMessage());
        }
    }

    private void declineProduct(int itemId) {
        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE order_items SET status='Cancelled' WHERE order_item_id=?");
            ps.setInt(1, itemId);
            ps.executeUpdate();
            showAlert("Product declined.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error declining product: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}