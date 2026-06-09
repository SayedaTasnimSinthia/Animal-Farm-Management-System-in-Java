package com.example.animalfarm360.model;

public class Cart {
    private int cartId;
    private String name;
    private String type;
    private double price;
    private int productId;

    public Cart(int cartId, String name, String type, double price, int productId) {
        this.cartId = cartId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.productId = productId;
    }

    public Cart() {
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
