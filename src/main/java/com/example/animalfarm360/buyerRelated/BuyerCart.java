package com.example.animalfarm360.buyerRelated;

import com.example.animalfarm360.HelloApplication;
import com.example.animalfarm360.model.Cart;
import com.example.animalfarm360.util.ConnectionSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BuyerCart implements Initializable {
    @FXML
    private TextArea addressField;

    @FXML
    private TableView<Cart> cartTable;

    @FXML
    private TableColumn<Cart, String> nameCol;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<Cart, Number> priceCol;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TableColumn<Cart, String> typeCol;

    @FXML
    private void backToShop(ActionEvent event) {
        HelloApplication.changeScene("productsForBuyerToSee");
    }


    private ObservableList<Cart> cartItems = FXCollections.observableArrayList();
    private int buyerId = 1;


    private void loadCart() {
        cartItems.clear();
        double total = 0;

        try {Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps1 = con.prepareStatement("SELECT c.cart_id, c.product_id, a.category, a.salePrice " + "FROM cart c JOIN animal a ON c.product_id=a.animalId " + "WHERE c.buyer_id=? AND c.product_type='animal'");
            ps1.setInt(1, buyerId);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Cart item = new Cart(
                        rs1.getInt("cart_id"),
                        rs1.getString("category"),
                        "Animal",
                        rs1.getDouble("salePrice"),
                        rs1.getInt("product_id"));
                cartItems.add(item);
                total += item.getPrice();
            }

            PreparedStatement ps2 = con.prepareStatement("SELECT c.cart_id, c.product_id, p.productName, p.salePrice " + "FROM cart c JOIN animal_product p ON c.product_id=p.productId " +
                            "WHERE c.buyer_id=? AND c.product_type='animal_product'");

            ps2.setInt(1, buyerId);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                Cart item = new Cart(
                        rs2.getInt("cart_id"),
                        rs2.getString("productName"),
                        "Product",
                        rs2.getDouble("salePrice"),
                        rs2.getInt("product_id"));

                cartItems.add(item);
                total += item.getPrice();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        cartTable.setItems(cartItems);
        totalPriceLabel.setText("Total: ৳ " + total);
    }

    @FXML
    private void remove() {
        Cart selected = cartTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a row to remove", Alert.AlertType.WARNING);
            return;
        }

        try {
            Connection con = ConnectionSingleton.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM cart WHERE cart_id=?");
            ps.setInt(1, selected.getCartId());
            ps.executeUpdate();
            loadCart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void placeOrder() {
        if (cartItems.isEmpty()) {
            showAlert("Your cart is empty. Please add products before placing an order.", Alert.AlertType.WARNING);
            return;
        }

        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();


        if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            showAlert("Please enter Name, Phone, and Address before placing the order.", Alert.AlertType.WARNING);
            return;
        }


        if (phone.length() != 11 || !phone.startsWith("01")) {
            showAlert("Phone number must be 11 digits and start with 01.", Alert.AlertType.WARNING);
            return;
        }


        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                showAlert("Phone number must contain only digits.", Alert.AlertType.WARNING);
                return;
            }
        }

        try {
            Connection con = ConnectionSingleton.getConnection();

            PreparedStatement psOrder = con.prepareStatement("INSERT INTO orders (buyer_id, buyer_name, phone, address) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, buyerId);
            psOrder.setString(2, name);
            psOrder.setString(3, phone);
            psOrder.setString(4, address);
            psOrder.executeUpdate();

            ResultSet generatedKeys = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }

            for (Cart item : cartItems) {
                PreparedStatement psItem = con.prepareStatement("INSERT INTO order_items (order_id, product_type, product_id, product_name, price) VALUES (?, ?, ?, ?, ?)");
                psItem.setInt(1, orderId);
                psItem.setString(2, item.getType().equals("Animal") ? "animal" : "animal_product");
                psItem.setInt(3, item.getProductId());
                psItem.setString(4, item.getName());
                psItem.setDouble(5, item.getPrice());
                psItem.executeUpdate();
            }


            PreparedStatement clear = con.prepareStatement("DELETE FROM cart WHERE buyer_id=?");
            clear.setInt(1, buyerId);
            clear.executeUpdate();

            cartItems.clear();
            cartTable.setItems(cartItems);
            totalPriceLabel.setText("Total: ৳ 0.0");
            nameField.clear();
            phoneField.clear();
            addressField.clear();

            showAlert("Order placed! You will be contacted soon.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error placing order: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }




    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        typeCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getType()));
        priceCol.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPrice()));

        loadCart();
    }
}