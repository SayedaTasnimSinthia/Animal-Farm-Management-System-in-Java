package com.example.animalfarm360.model;

import java.time.LocalDate;

public class Attendance {
    private LocalDate date;
    private String status;

    public Attendance() {
    }

    public Attendance(LocalDate date, String status) {
        this.date = date;

        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
