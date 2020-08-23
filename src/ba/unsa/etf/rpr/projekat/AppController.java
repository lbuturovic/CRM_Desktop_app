package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AppController {
    private User user;
    private DAO dao;
    public Label message3;
    public AppController(User logedInUser) {
        user = logedInUser;
        dao = DAO.getInstance();
    }
    public AppController(){

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
                stage.setScene(new Scene(root,stage.getWidth(), stage.getHeight()));
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
    public void sendEmailsAction(ActionEvent actionEvent){



    }

    public void myTeamAction(ActionEvent actionEvent) {

    }

}
