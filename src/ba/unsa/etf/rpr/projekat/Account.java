package ba.unsa.etf.rpr.projekat;

import java.util.Objects;

public class Account {
private int id;
private String name;
private AccountType type;
private String phone;
private String website;
private User initials;
private User updatedBy;

    public Account(int id, String name, AccountType type, String phone, String website, User initials, User updateBy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.website = website;
        this.initials = initials;
        this.updatedBy = updateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public User getInitials() {
        return initials;
    }

    public void setInitials(User initials) {
        this.initials = initials;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updateBy) {
        this.updatedBy = updatedBy;
    }
}
