package com.example.animalfarm360.model;

import java.time.LocalDate;

public class AnimalProduct {
    private int productId;
    private String productName;
    private String productType;
    private String breed;
    private String sourceAnimal;
    private LocalDate productionDate;
    private Double quantity;
    private String unit;
    private Double salePrice;
    private String notes;
    private String image;
    private String status;

    public AnimalProduct() {
    }


    public AnimalProduct(int productId, String productName, String productType, String breed, String sourceAnimal, LocalDate productionDate, Double quantity, String unit, Double salePrice, String notes, String image, String status) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.breed = breed;
        this.sourceAnimal = sourceAnimal;
        this.productionDate = productionDate;
        this.quantity = quantity;
        this.unit = unit;
        this.salePrice = salePrice;
        this.notes = notes;
        this.image = image;
        this.status = status;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSourceAnimal() {
        return sourceAnimal;
    }

    public void setSourceAnimal(String sourceAnimal) {
        this.sourceAnimal = sourceAnimal;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
