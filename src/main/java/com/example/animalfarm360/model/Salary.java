package com.example.animalfarm360.model;

public class Salary {
    private String monthYear;
    private  Double salaryAmount;
    private  String status;


    public Salary(String monthYear, Double salaryAmount, String status) {
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

    public Double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Salary() {
    }
}
