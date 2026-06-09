package com.example.animalfarm360.model;

import javafx.beans.property.SimpleBooleanProperty;

import java.time.LocalDate;

public class Booking {
    private String name;
    private String email;
    private String phone;
    private LocalDate date;
    private String  time;
    private int visitors;
    private String notes;
    private boolean contacted;
    private boolean approved;
    public boolean isContacted() {
        return contacted;
    }


    public Booking() {
    }


    public Booking(String name, String email, String phone, LocalDate date, String time, int visitors, String notes, boolean contacted, boolean approved) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.visitors = visitors;
        this.notes = notes;
        this.contacted = contacted;
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setContacted(boolean contacted) {
        this.contacted = contacted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
