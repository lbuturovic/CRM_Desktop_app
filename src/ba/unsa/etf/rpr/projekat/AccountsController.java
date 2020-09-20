package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

public class AccountsController extends Controler{
    public TextField filterField;
    public TextField fileName;
    public TableView<Account> tableViewAccounts;
    public TableColumn colAccountName;
    public TableColumn colAccountType;
    public TableColumn colAccountWebsite;
    public TableColumn colAccountPhone;
    public TableColumn<Account, String> colAccountInitials;
    public TableColumn<Account, String> colAccountUpdateBy;
    public TextField fldName, fldWebsite, fldPhone;
    private ObservableList<Account> listAccounts;
    private ObservableList<Account> filteredAccounts;
    public Button btnAdd;
    public Button btnDelete, btnUpdate;
    public ChoiceBox<String> choiceType;
    private ObservableList<String> choices;

    public AccountsController(User logedInUser) {
        user = logedInUser;
        dao = DAO.getInstance();
        listAccounts = FXCollections.observableArrayList(dao.accounts());
        choices = FXCollections.observableArrayList(dao.typeNames());
    }

    @FXML
    public void initialize() {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tableViewAccounts.setItems(listAccounts);
        choiceType.setItems(choices);
        choiceType.getSelectionModel().selectFirst();
        colAccountName.setCellValueFactory(new PropertyValueFactory("name"));
        colAccountPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        colAccountWebsite.setCellValueFactory(new PropertyValueFactory("website"));
        colAccountType.setCellValueFactory(new PropertyValueFactory("type"));
        colAccountInitials.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInitials().getName()));
        colAccountUpdateBy.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUpdatedBy().getName()));
        search_account();
        tableViewAccounts.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        fldName.textProperty().addListener((obs, oldName, newName) -> {

            if (newName != null && newName.length() != 0) {
                btnAdd.setDisable(false);
                if (tableViewAccounts.getSelectionModel().getSelectedItem() != null)
                    btnUpdate.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(true);
            }


            if (newName == null || newName.length() > 0) fldName.getStyleClass().removeAll("fieldNotOk");
            else fldName.getStyleClass().add("fieldNotOk");
        });

        tableViewAccounts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection==null) btnDelete.setDisable(true);
            else btnDelete.setDisable(false);
            fldWebsite.getStyleClass().removeAll("fieldNotOk");
            fldName.getStyleClass().removeAll("fieldNotOk");
            fldPhone.getStyleClass().removeAll("fieldNotOk");
            if (newSelection != null) {
                btnUpdate.setDisable(false);
                Account selected = tableViewAccounts.getSelectionModel().getSelectedItem();
                fldName.setText(selected.getName());
                fldWebsite.setText(selected.getWebsite());
                fldPhone.setText(selected.getPhone());
                choiceType.getSelectionModel().select(selected.getType());
            } else {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(true);
                fldName.setText(null);
                fldWebsite.setText(null);
                fldPhone.setText(null);
                choiceType.getSelectionModel().selectFirst();

            }
        });

    }

    private void search_account() {
        filteredAccounts = FXCollections.observableArrayList(dao.accounts());
        tableViewAccounts.setItems(filteredAccounts);
        FilteredList<Account> filteredData = new FilteredList<>(filteredAccounts, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(account -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (account.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches type
                } else if (account.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches name
                } else if (account.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches phone number
                } else if (account.getInitials().getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches initials name
                } else if (account.getWebsite().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches website
                } else if (account.getUpdatedBy().getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;// Filter matches update by
                } 

                else
                    return false; // Does not match.
            });
        });
        SortedList<Account> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewAccounts.comparatorProperty());
        tableViewAccounts.setItems(sortedData);
    }

    private static boolean validate(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void signOutAction(ActionEvent actionEvent) {
        openLogin();
    }

    public void unselectAction(ActionEvent actionEvent) {
        tableViewAccounts.getSelectionModel().clearSelection();
    }

    public void reportAction(ActionEvent actionEvent) {
    }

    public void employersAction(ActionEvent actionEvent) {
    }

    public void txtFileAction(ActionEvent actionEvent) {
    }

    public void addAction(ActionEvent actionEvent) {
    }

    public void updateAction(ActionEvent actionEvent) {
    }

    public void deleteAction(ActionEvent actionEvent) {
    }

    public void dashboardAction(ActionEvent actionEvent) {
        openDashboard();
    }

    public void tasksAction(ActionEvent actionEvent) {
    }

    public void myTeamAction(ActionEvent actionEvent) {
        openMyTeam();
    }

    public void sendEmailsAction(ActionEvent actionEvent) {
        if(user.getPassword()!=null) openEmails("");
        else openEmailsLogin("");
    }
}
