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

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(7), getDepartment(rs.getInt(8)), rs.getInt(1));
        return user;
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

}
