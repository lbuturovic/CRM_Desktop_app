package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController  {

  public TextField username;
  public PasswordField password;
  public Label message;
  public Label message2;
  private DAO dao;
  public LoginController() {
    dao = DAO.getInstance();
  }
  public void loginAction(ActionEvent actionEvent) {
      User user = dao.userLogin(username.getText(), username.getText(), password.getText());
      if (user != null) {
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = null;
        try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/app.fxml"));
          AppController appController = new AppController(user);
          loader.setController(appController);
          root = loader.load();
          stage.setTitle("CRM");
          appController.message3.setText("Welcome, " + user.getName());
          stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
          stage.show();

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      else {
          message.setWrapText(true);
          message.setText("Your username or password");
          message2.setText(" is inncorect.");
      }

    }
}
