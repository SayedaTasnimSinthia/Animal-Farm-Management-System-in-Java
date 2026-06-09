package com.example.animalfarm360.model;

public class OrderItem {

    private int orderId;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;
    private String productName;
    private String productType;
    private double price;
    private String status;
    private int orderItemId;
    private int productId;

    public OrderItem(int orderId, String buyerName, String buyerEmail, String buyerPhone, String buyerAddress, String productName, String productType, double price, String status, int orderItemId, int productId) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.status = status;
        this.orderItemId = orderItemId;
        this.productId = productId;
    }

    public OrderItem() {
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
