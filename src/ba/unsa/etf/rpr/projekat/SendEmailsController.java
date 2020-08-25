package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SendEmailsController extends Controler {
    private String addresses;
    public TextField fldEmail;
    public TextArea areaTo;
    public TextArea areaMail;
    public TextArea areaSubject;
    public Button btnAdd;

    public SendEmailsController(User user) {
        this.user = user;
        addresses = "";
    }

    public SendEmailsController(User user, String addresses) {
        this.addresses = addresses;
        this.user = user;
    }

    public void addAction(ActionEvent actionEvent) {

    }

    public void clientsAction(ActionEvent actionEvent) {
    }

    public void tasksAction(ActionEvent actionEvent) {
    }

    public void myTeamAction(ActionEvent actionEvent) {
        openMyTeam();
    }

    public void dashboardAction(ActionEvent actionEvent) {
        openDashboard();
    }

    public void signOutAction(ActionEvent actionEvent) {
        openLogin();
    }

    public void sendEmailsAction(ActionEvent actionEvent) {
    }
}
