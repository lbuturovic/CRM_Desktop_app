package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class SendEmailsLoginController extends Controler {
    String addresses;
    public TextField username;
    public PasswordField password;
    public Label message;
    public Label message2;
    public SendEmailsLoginController(User user, String addresses) {
        this.user = user;
        this.addresses = addresses;
    }

    @FXML
    public void initialize() {
        username.setText(user.getEmail());
        username.setEditable(false);
        username.setDisable(true);
    }

    public void loginAction(ActionEvent actionEvent) {
        final String email = user.getEmail();
        final String pass = password.getText();
        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, pass);
            }
        });
        boolean isOk = true;
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", 587, email, pass);
            System.out.println("konektovano");
        } catch (NoSuchProviderException e) {
            message.setText("Problem with server.");
            message2.setText("Try again later.");

            isOk = false;
        } catch (MessagingException e) {
            message.setText("Your username or password");
            message2.setText(" is inncorect.");
            isOk = false;
        }
        if (isOk == true) {
            user.setPassword(pass);
            openEmails(addresses);
        }
    }

    public void dashboardAction(ActionEvent actionEvent) {
        openDashboard();
    }

    public void accountsAction(ActionEvent actionEvent) {
    }

    public void clientsAction(ActionEvent actionEvent) {
    }

    public void tasksAction(ActionEvent actionEvent) {
    }

    public void myTeamAction(ActionEvent actionEvent) {
        openMyTeam();
    }

    public void signOutAction(ActionEvent actionEvent) {
        openLogin();
    }
}
