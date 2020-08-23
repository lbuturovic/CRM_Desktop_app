package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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

    public void SignOutAction(ActionEvent actionEvent) {
        Stage stage = (Stage) message3.getScene().getWindow();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            LoginController ctrl = new LoginController();
            loader.setController(ctrl);
            root = loader.load();
            ctrl.username.setText(user.getUsername());
            stage.setTitle("Login");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void accountsAction(ActionEvent actionEvent) {

    }

    public void contactsAction(ActionEvent actionEvent) {

    }

    public void tasksAction(ActionEvent actionEvent) {
    }

    public void sendEmailsAction(ActionEvent actionEvent) {


    }

    public void myTeamAction(ActionEvent actionEvent) {
        openMyTeam();
    }
}
