package com.example.animalfarm360.model;

import java.time.LocalDate;

public class VaccinationRecord {
    private String vaccineName;
    private LocalDate dateGiven;
    private LocalDate nextDueDate;
    private String status;


    public VaccinationRecord() {
    }


    public VaccinationRecord(String vaccineName, LocalDate dateGiven, LocalDate nextDueDate, String status) {
        this.vaccineName = vaccineName;
        this.dateGiven = dateGiven;
        this.nextDueDate = nextDueDate;
        this.status = status;
    }


    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(LocalDate dateGiven) {
        this.dateGiven = dateGiven;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
