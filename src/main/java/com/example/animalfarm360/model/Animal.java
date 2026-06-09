package com.example.animalfarm360.model;

import java.time.LocalDate;

public class Animal {
    private int animalId;
    private String category;
    private String breed;
    private LocalDate dob;
    private String gender;
    private String healthStatus;
    private String notes;
    private String productivity;
    private Double milkYield;
    private Integer eggCount;
    private Double meatYield;
    private Double wool;
    private String saleStatus;
    private Double salePrice;
    private String systemFlag;
    private String image;

    public Animal(int animalId, String category, String breed, LocalDate dob, String gender, String healthStatus, String notes, String productivity, Double milkYield, Integer eggCount, Double meatYield, Double wool, String saleStatus, Double salePrice, String systemFlag, String image) {
        this.animalId = animalId;
        this.category = category;
        this.breed = breed;
        this.dob = dob;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.notes = notes;
        this.productivity = productivity;
        this.milkYield = milkYield;
        this.eggCount = eggCount;
        this.meatYield = meatYield;
        this.wool = wool;
        this.saleStatus = saleStatus;
        this.salePrice = salePrice;
        this.systemFlag = systemFlag;
        this.image = image;
    }

    public Animal() {
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProductivity() {
        return productivity;
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }

    public Double getMilkYield() {
        return milkYield;
    }

    public void setMilkYield(Double milkYield) {
        this.milkYield = milkYield;
    }

    public Integer getEggCount() {
        return eggCount;
    }

    public void setEggCount(Integer eggCount) {
        this.eggCount = eggCount;
    }

    public Double getMeatYield() {
        return meatYield;
    }

    public void setMeatYield(Double meatYield) {
        this.meatYield = meatYield;
    }

    public Double getWool() {
        return wool;
    }

    public void setWool(Double wool) {
        this.wool = wool;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(String systemFlag) {
        this.systemFlag = systemFlag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
