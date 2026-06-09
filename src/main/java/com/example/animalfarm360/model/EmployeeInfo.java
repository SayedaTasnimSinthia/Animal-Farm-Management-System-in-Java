package com.example.animalfarm360.model;

public class EmployeeInfo {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;

    private String email;
    private int employeeId;
    private String department;
    private String position;
    private String dateOfJoining;
    private String employmentStatus;
    private String employeetype;
    private String username;
    private String password;
    private double salary;
    private String emergencyContactName;
    private String emergencyContactPhone;

    private String image;

    public EmployeeInfo(String name, String dateOfBirth, String gender, String phoneNumber, String address, String email, int employeeId, String department, String position, String dateOfJoining, String employmentStatus, String employeetype, String username, String password, double salary, String emergencyContactName, String emergencyContactPhone, String image) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.employeeId = employeeId;
        this.department = department;
        this.position = position;
        this.dateOfJoining = dateOfJoining;
        this.employmentStatus = employmentStatus;
        this.employeetype = employeetype;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.image = image;
    }

    public EmployeeInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
