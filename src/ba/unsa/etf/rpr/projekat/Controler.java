package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static javafx.scene.control.PopupControl.USE_PREF_SIZE;

public abstract class Controler {
    protected DAO dao;
    protected User user;
    public Button btnDashboard;
    private Parent root;

    private Stage prepareForOpen(String path,Controler ctrl){
        Stage stage = (Stage) btnDashboard.getScene().getWindow();
        root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            loader.setController(ctrl);
            root = loader.load();
            return stage;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void openMyTeam(){
       Stage stage = prepareForOpen("/fxml/myTeam.fxml",new MyTeamController(user));
        stage.setTitle("My Team");
        double width = stage.getWidth();
        double height =  stage.getHeight();
        stage.setScene(new Scene(root,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
    protected void openDashboard(){
        Stage stage = prepareForOpen("/fxml/app.fxml",new AppController(user));
        stage.setTitle("Dashboard");
        double width = stage.getWidth();
        double height =  stage.getHeight();
        stage.setScene(new Scene(root,USE_PREF_SIZE,USE_PREF_SIZE));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
    protected void openLogin(){
        Stage stage = (Stage) btnDashboard.getScene().getWindow();
        root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            LoginController ctrl = new LoginController();
            loader.setController(ctrl);
            root = loader.load();
            ctrl.username.setText(user.getUsername());
            stage.setTitle("Login");
            double width = stage.getWidth();
            double height =  stage.getHeight();
            stage.setScene(new Scene(root,USE_PREF_SIZE,USE_PREF_SIZE));
            stage.setWidth(1300);
            stage.setHeight(650);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void openEmailsLogin(String addresses) {
        Stage stage = prepareForOpen("/fxml/sendEmailsLogin.fxml", new SendEmailsLoginController(user, addresses));
        stage.setTitle("Gmail Login");
        double width = stage.getWidth();
        double height = stage.getHeight();
        stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
    protected void openEmails(String addresses) {
        Stage stage = prepareForOpen("/fxml/sendEmails.fxml", new SendEmailsController(user, addresses));
        stage.setTitle("Send emails");
        double width = stage.getWidth();
        double height = stage.getHeight();
        stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.show();
    }
    protected void openContacts() {
        Stage stage = (Stage) btnDashboard.getScene().getWindow();
        root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contacts.fxml"));
            Controler ctrl = new ContactsController(user);
            loader.setController(ctrl);
            root = loader.load();
            stage.setTitle("Contacts");
            double width = stage.getWidth();
            double height =  stage.getHeight();
            stage.setScene(new Scene(root,USE_PREF_SIZE,USE_PREF_SIZE));
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
