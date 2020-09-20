package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

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
    public CheckBox chkSales, chkManagement, chkMarketing;
    public TextField filterField;
    public ArrayList<User> users;
    private ObservableList<User> listUsers;
    private ObservableList<User> filteredUsers;

    public MyTeamController(User logedInUser) {
        user = logedInUser;
        super.dao = DAO.getInstance();
        users = dao.users();
        listUsers = FXCollections.observableArrayList(users);
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
        if(user.getPassword()==null)
        openEmailsLogin(addresses);
        else openEmails(addresses);

    }

    public void departmentAction(ActionEvent actionEvent) {
        if(chkManagement.isSelected() && chkSales.isSelected() && chkMarketing.isSelected())  listUsers= FXCollections.observableArrayList(users);
        else if(chkMarketing.isSelected() && chkSales.isSelected()) {

            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Marketing") || u.getDepartment().equals("Sales")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }
        else if(chkMarketing.isSelected() && chkManagement.isSelected()) {

            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Marketing") || u.getDepartment().equals("Management")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }

        else if(chkSales.isSelected() && chkManagement.isSelected()) {

            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Sales") || u.getDepartment().equals("Management")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }
        else if(chkSales.isSelected()) {

            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Sales")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }
        else if(chkManagement.isSelected()) {

            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Management")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }
        else {
            ArrayList<User> usersByDepartments = users.stream().filter(u ->u.getDepartment().equals("Marketing")).collect(toCollection(ArrayList::new));
            listUsers = FXCollections.observableArrayList(usersByDepartments);
        }

        tableViewUsers.setItems(listUsers);


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
        tableViewUsers.setItems(listUsers);
        FilteredList<User> filteredData = new FilteredList<>(listUsers, b -> true);
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
    public void contactsAction(ActionEvent actionEvent) {openContacts();}
    public void dashboardAction(ActionEvent actionEvent) {
        openDashboard();
    }
    public void sendEmailsAction(ActionEvent actionEvent){
        openEmailsLogin("");
    }
}
