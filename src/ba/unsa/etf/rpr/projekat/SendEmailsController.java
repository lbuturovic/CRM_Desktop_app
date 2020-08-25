package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
        final String email = user.getEmail();
        final String pass = user.getPassword();
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
        try {
            //Creating a Message object to set the email content
            MimeMessage msg = new MimeMessage(session);
            //Storing the comma seperated values to email addresses
            String to = addresses;
            //Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email   addresses in an array of InternetAddress objects
            InternetAddress[] address = InternetAddress.parse(to, true);
            //Setting the recepients from the address variable
            msg.setRecipients(Message.RecipientType.TO, address);
            String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());
            msg.setSubject(areaSubject.getText()+": " + timeStamp);
            msg.setSentDate(new Date());
            msg.setText(areaMail.getText());
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            message.setText("Mail has been sent successfully");
        } catch (MessagingException mex) {
            message.setText("Unable to send an email" + mex);
        }
    }

}