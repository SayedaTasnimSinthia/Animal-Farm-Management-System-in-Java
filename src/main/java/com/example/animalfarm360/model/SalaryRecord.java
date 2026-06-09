package com.example.animalfarm360.model;

public class SalaryRecord {
    private String monthYear;
    private double salaryAmount;
    private String status;

    public SalaryRecord() {
    }

    public SalaryRecord(String monthYear, double salaryAmount, String status) {
        this.monthYear = monthYear;
        this.salaryAmount = salaryAmount;
        this.status = status;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
