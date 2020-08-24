package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MyTeamController extends Controler{
    //private User user;
   // private DAO dao;
    public TableView<User> tableViewUsers;
    public TableColumn colUserName;
    public TableColumn colUserSurname;
    public TableColumn colUserUsername;
    public TableColumn colUserEmail;
    public TableColumn colUserPhoneNumber;
    public TableColumn<User, String> colUserDepartment;
    public ChoiceBox<String> choiceDepartment;
    public TextField filterField;
    private ObservableList<User> listUsers;
    private ObservableList<User> filteredUsers;

    public MyTeamController(User logedInUser) {
        user = logedInUser;
        super.dao = DAO.getInstance();
        listUsers = FXCollections.observableArrayList(dao.users());
    }

    public MyTeamController() {

    }

    @FXML
    public void initialize() {
        tableViewUsers.setItems(listUsers);
        colUserName.setCellValueFactory(new PropertyValueFactory("name"));
        colUserSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        colUserUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUserPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colUserDepartment.setCellValueFactory(new PropertyValueFactory<User, String>("department"));
        search_user();
        tableViewUsers.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
    }


    public void mailAction(ActionEvent actionEvent) {
        String addresses = "";
        ObservableList<User> users = tableViewUsers.getSelectionModel().getSelectedItems();
        ArrayList<User> clients = new ArrayList<User>(users);
        if(clients!=null)
            for (int i = 0; i < clients.size(); i++) {
                if(i==clients.size()-1) addresses = clients.get(i).getEmail();
                else  addresses = clients.get(i).getEmail() + ",";
            }
        else addresses = "";
        openEmailsLogin(addresses);

    }



    @FXML
    void search_user() {
        colUserName.setCellValueFactory(new PropertyValueFactory("name"));
        colUserSurname.setCellValueFactory(new PropertyValueFactory("surname"));
        colUserUsername.setCellValueFactory(new PropertyValueFactory("username"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colUserPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        colUserDepartment.setCellValueFactory(new PropertyValueFactory<User,String>("department"));

        filteredUsers = FXCollections.observableArrayList(dao.users());
        tableViewUsers.setItems(filteredUsers);
        FilteredList<User> filteredData = new FilteredList<>(filteredUsers, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches username
                } else if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches name
                }else if (person.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches phone number
                }
                else if(person.getSurname().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true; // Filter matches surname
                }
                else if (person.getDepartment().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true; // Filter matches department
                }
                else if (String.valueOf(person.getEmail()).indexOf(lowerCaseFilter)!=-1)
                    return true;// Filter matches email

                else
                    return false; // Does not match.
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewUsers.comparatorProperty());
        tableViewUsers.setItems(sortedData);

    }

    public void deselectAction(ActionEvent Actionevent) {
        tableViewUsers.getSelectionModel().clearSelection();
    }

    public void signOutAction(ActionEvent actionEvent) {
        openLogin();
    }

    public void dashboardAction(ActionEvent actionEvent) {
        openDashboard();
    }
    public void sendEmailsAction(ActionEvent actionEvent){
        openEmailsLogin("");
    }
}
