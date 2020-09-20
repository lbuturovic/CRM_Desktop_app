package ba.unsa.etf.rpr.projekat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DAO {
    private static DAO instance;
    private PreparedStatement getUsersQuery;
    private Connection conn;
    private PreparedStatement getUserQuery;
    private PreparedStatement getDepartmentQuery;
    private PreparedStatement getUserByIdQuery;
    private PreparedStatement getAccountTypeQuery;
    private PreparedStatement getAccountByIdQuery;
    private PreparedStatement getContactsQuery;
    private PreparedStatement getAccountsQuery;
    private PreparedStatement updateContactQuery;
    private PreparedStatement addContactQuery;
    private PreparedStatement deleteContactQuery;
    private PreparedStatement getAccountTypeName;
    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }

    private DAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getUserQuery = conn.prepareStatement("SELECT * FROM users WHERE username=? OR email=? AND password=?");
        } catch (SQLException e) {
            regenerisiBazu();
        }
        try {
            getUserQuery = conn.prepareStatement("SELECT * FROM users WHERE username=? OR email=? AND password=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getDepartmentQuery = conn.prepareStatement("SELECT name FROM department WHERE id=?");
            getUsersQuery = conn.prepareStatement("SELECT * FROM users ORDER BY department");
            getUserByIdQuery = conn.prepareStatement("SELECT * FROM users WHERE id=?");
            getAccountTypeQuery = conn.prepareStatement("SELECT type FROM accountsType WHERE id=(SELECT type FROM accounts WHERE id=? )");
            getAccountByIdQuery = conn.prepareStatement("SELECT * FROM accounts WHERE id=?");
            getContactsQuery = conn.prepareStatement("SELECT * FROM contacts");
            getAccountsQuery = conn.prepareStatement("SELECT * FROM accounts");
            updateContactQuery = conn.prepareStatement("UPDATE contacts SET name=?, jobTitle=?, account=?, email=?, phone=?, updateBy=? WHERE id=?");
            addContactQuery = conn.prepareStatement("INSERT INTO contacts (name, jobTitle, account, email, phone, initials, updateBy) VALUES (?,?,?,?,?,?,?)");
            deleteContactQuery = conn.prepareStatement("DELETE FROM contacts WHERE id=?");
            getAccountTypeName = conn.prepareStatement("SELECT type FROM accountsType");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User userLogin(String username, String email, String password) {
        try {
            getUserQuery.setString(1, username);
            getUserQuery.setString(2, email);
            getUserQuery.setString(3, password);
            ResultSet rs = getUserQuery.executeQuery();
            if (!rs.next()) return null;
            return getUserFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> users() {
        ArrayList<User> result = new ArrayList();
        try {
            ResultSet rs = getUsersQuery.executeQuery();
            while (rs.next()) {
                User user = getUserFromResultSet(rs);
                result.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Contact> contacts() {
        ArrayList<Contact> result = new ArrayList();
        try {
            ResultSet rs = getContactsQuery.executeQuery();
            while (rs.next()) {
                Contact contact = getContactFromResultSet(rs);
                result.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Account> accounts() {
        ArrayList<Account> result = new ArrayList();
        try {
            ResultSet rs = getAccountsQuery.executeQuery();
            while (rs.next()) {
                Account account= getAccountFromResultSet(rs);
                result.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private User getUser(int id){
        try {
            getUserByIdQuery.setInt(1, id);
            ResultSet rs = getUserByIdQuery.executeQuery();
            if (!rs.next()) return null;
            User user = getUserFromResultSet(rs);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7), getDepartment(rs.getInt(8)), rs.getInt(1));
        return user;
    }
    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account(rs.getInt(1), rs.getString(2), getAccountType(rs.getInt(3)), rs.getString(4),rs.getString(5),getUser(rs.getInt(6)),getUser(rs.getInt(7)));
        return account;
    }
    private Contact getContactFromResultSet(ResultSet rs) throws SQLException {
        Contact contact = new Contact(rs.getString(2), rs.getString(3), getAccount(rs.getInt(4)), rs.getString(5),rs.getString(6),getUser(rs.getInt(7)),rs.getInt(1),getUser(rs.getInt(8)));
        return contact;
    }

    private String getAccountType(int accountId) {
        try {
            getAccountTypeQuery.setInt(1, accountId);
            ResultSet rs = getAccountTypeQuery.executeQuery();
            if (!rs.next()) return null;
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDepartment(int id) {
        try {
            getDepartmentQuery.setInt(1, id);
            ResultSet rs = getDepartmentQuery.executeQuery();
            if (!rs.next()) return null;
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Account getAccount(int accountId) {
        try {
            getAccountByIdQuery.setInt(1, accountId);
            ResultSet rs = getAccountByIdQuery.executeQuery();
            if (!rs.next()) return null;
            Account account = getAccountFromResultSet(rs);
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateContact(String name, String jobTitle, int accountId, String email, String phone, int updatedById, int id) {
        try {
            updateContactQuery.setString(1,name);
            updateContactQuery.setString(2,jobTitle);
            updateContactQuery.setInt(3,accountId);
            updateContactQuery.setString(4,email);
            updateContactQuery.setString(5,phone);
            updateContactQuery.setInt(6,updatedById);
            updateContactQuery.setInt(7,id);
            updateContactQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addContact(String name, String jobTitle, int accountId, String email, String phone, int initialsId, int updatedById){

        try {
            addContactQuery.setString(1,name);
            addContactQuery.setString(2,jobTitle);
            addContactQuery.setInt(3,accountId);
            addContactQuery.setString(4,email);
            addContactQuery.setString(5,phone);
            addContactQuery.setInt(7,updatedById);
            addContactQuery.setInt(6,initialsId);
            addContactQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteContact(int id){
        try {
            deleteContactQuery.setInt(1,id);
            deleteContactQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn(){
        return conn;
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if (sqlUpit.length() > 1 && sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> typeNames() {

        try {
            ResultSet rs = getAccountTypeName.executeQuery();
            ArrayList<String> types = new ArrayList<>();
            while(rs.next()){
                types.add(rs.getString(1));
            }
            return types;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
