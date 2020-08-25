package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendEmailsController extends Controler {
    private String addresses;
    public TextField fldEmail;
    public TextArea areaTo;
    public TextArea areaMail;
    public TextArea areaSubject;
    public Button btnAdd;
    public Label message;

    public SendEmailsController(User user) {
        this.user = user;
        addresses = "";
    }

    public SendEmailsController(User user, String addresses) {
        this.addresses = addresses;
        this.user = user;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    @FXML
    void initialize() {
        btnAdd.setDisable(true);
        if (addresses != null)
            areaTo.setText(addresses);
        fldEmail.textProperty().addListener((obs, oldEmail, newEmail) -> {
            if (validate(newEmail)) {
                fldEmail.getStyleClass().removeAll("fieldNotOk");
                fldEmail.getStyleClass().add("fieldOk");
                btnAdd.setDisable(false);

            } else {
                fldEmail.getStyleClass().removeAll("fieldOk");
                fldEmail.getStyleClass().add("fieldNotOk");
                btnAdd.setDisable(true);
            }
        });
    }


    public void addAction(ActionEvent actionEvent) {
        if (addresses == "") addresses += fldEmail.getText();
        else
            addresses = addresses + "," + fldEmail.getText();
        areaTo.setText(addresses);

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