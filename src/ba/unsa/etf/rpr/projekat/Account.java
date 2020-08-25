package ba.unsa.etf.rpr.projekat;

import java.util.Objects;

public class Account {
private int id;
private String name;
private String type;
private String phone;
private String website;
private User initials;
private User updateBy;

    public Account(int id, String name, String type, String phone, String website, User initials, User updateBy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.website = website;
        this.initials = initials;
        this.updateBy = updateBy;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }
}
