package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private  SimpleStringProperty  email, name, jobTitle,phone;
    private Account account;
    private int id;
    private User initials, updatedBy;

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Contact(String name, String jobTitle, Account account, String email, String phone, User initials, int id, User updatedBy) {
        this.name = new SimpleStringProperty(name);
        this.jobTitle = new SimpleStringProperty(jobTitle);
        this.account = account;
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.initials = initials;
        this.id = id;
        this.updatedBy = updatedBy;
    }

    public User getInitials() {
        return initials;
    }

    public void setInitials(User initials) {
        this.initials = initials;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getJobTitle() {
        return jobTitle.get();
    }

    public SimpleStringProperty jobTitleProperty() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle.set(jobTitle);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

}
