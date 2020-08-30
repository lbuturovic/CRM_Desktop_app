package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController extends Controler {
    public Label message3;

    public AppController(User logedInUser) {
        user = logedInUser;
        dao = DAO.getInstance();
    }

    @FXML
    public void initialize(){
        message3.setText("Welcome, " + user.getName());
    }
    public AppController() {

    }

    public void accountsAction(ActionEvent actionEvent) {

    }

    public void contactsAction(ActionEvent actionEvent) {
       openContacts();

    }

    public void tasksAction(ActionEvent actionEvent) {
    }

    public void sendEmailsAction(ActionEvent actionEvent) {
        openEmailsLogin("");

    }

    public void myTeamAction(ActionEvent actionEvent) {
        openMyTeam();
    }

    public void signOutAction(ActionEvent actionEvent) {
        openLogin();
    }
}
