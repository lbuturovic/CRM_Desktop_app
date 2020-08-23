package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;

public class User {
    private  final SimpleStringProperty username, email, name, surname, phoneNumber, department;
    private String password;
    private int id;

    public User(String name, String surname, String username, String email, String phoneNumber, String department,int id) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.department = new SimpleStringProperty(department);
        this.password = null;
        this.id = id;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
