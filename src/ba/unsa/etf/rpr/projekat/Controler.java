package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
        stage.setScene(new Scene(root,stage.getWidth(), stage.getHeight()));
        stage.show();
    }
    protected void openDashboard(){
        Stage stage = prepareForOpen("/fxml/app.fxml",new AppController(user));
        stage.setTitle("Dashboard");
        stage.setScene(new Scene(root,stage.getWidth(), stage.getHeight()));
        stage.show();
    }
}
