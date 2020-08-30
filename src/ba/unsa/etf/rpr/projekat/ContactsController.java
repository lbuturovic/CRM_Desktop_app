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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsController extends Controler {
    public TextField filterField;
    public TableView<Contact> tableViewContacts;
    public TableColumn colContactName;
    public TableColumn colContactJobTitle;
    public TableColumn<Contact, String> colContactAccountName;
    public TableColumn colContactEmail;
    public TableColumn colContactPhone;
    public TableColumn<Contact, String> colContactInitials;
    public TableColumn<Contact, String> colContactUpdateBy;
    public TextField fldName, fldEmail, fldPhone, fldJobTitle;
    private ObservableList<Contact> listContacts;
    private ObservableList<Contact> filteredContacts;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public Button btnAdd;
    public Button btnDelete, btnUpdate;
    public ChoiceBox<Account> choiceAccount;
    private ObservableList<Account> choices;


    public ContactsController(User logedInUser) {
        user = logedInUser;
        dao = DAO.getInstance();
        listContacts = FXCollections.observableArrayList(dao.contacts());
        choices = FXCollections.observableArrayList(dao.accounts());
    }

    @FXML
    public void initialize() {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        tableViewContacts.setItems(listContacts);
        choiceAccount.setItems(choices);
        choiceAccount.getSelectionModel().selectFirst();
        colContactName.setCellValueFactory(new PropertyValueFactory("name"));
        colContactPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        colContactJobTitle.setCellValueFactory(new PropertyValueFactory("jobTitle"));
        colContactAccountName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAccount().getName()));
        colContactEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colContactInitials.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInitials().getName()));
        colContactUpdateBy.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUpdatedBy().getName()));
        search_user();
        tableViewContacts.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        fldEmail.textProperty().addListener((obs, oldEmail, newEmail) -> {

            if (newEmail != null && validate(newEmail) && (fldName.getText() != null && fldName.getText().length() != 0)) {
                btnAdd.setDisable(false);
                if (tableViewContacts.getSelectionModel().getSelectedItem() != null)
                    btnUpdate.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(true);
            }


            if (newEmail == null || validate(newEmail)) fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
            else fldEmail.getStyleClass().add("poljeNijeIspravno");
        });

        fldName.textProperty().addListener((obs, oldName, newName) -> {

            if (fldEmail.getText() != null && validate(fldEmail.getText()) && (newName != null && newName.length() != 0)) {
                btnAdd.setDisable(false);
                if (tableViewContacts.getSelectionModel().getSelectedItem() != null)
                    btnUpdate.setDisable(false);
            } else {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(true);
            }


            if (newName == null || newName.length() > 0) fldName.getStyleClass().removeAll("poljeNijeIspravno");
            else fldName.getStyleClass().add("poljeNijeIspravno");
        });

        tableViewContacts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
            fldName.getStyleClass().removeAll("poljeNijeIspravno");
            fldPhone.getStyleClass().removeAll("poljeNijeIspravno");
            if (newSelection != null) {
                btnUpdate.setDisable(false);
                Contact selected = tableViewContacts.getSelectionModel().getSelectedItem();
                fldName.setText(selected.getName());
                fldEmail.setText(selected.getEmail());
                fldJobTitle.setText(selected.getJobTitle());
                fldPhone.setText(selected.getPhone());
                choiceAccount.getSelectionModel().select(selected.getAccount());
            } else {
                btnAdd.setDisable(true);
                btnUpdate.setDisable(true);
                fldName.setText(null);
                fldEmail.setText(null);
                fldJobTitle.setText(null);
                fldPhone.setText(null);
                choiceAccount.getSelectionModel().selectFirst();

            }
        });


    }

    private void search_user() {
        filteredContacts = FXCollections.observableArrayList(dao.contacts());
        tableViewContacts.setItems(filteredContacts);
        FilteredList<Contact> filteredData = new FilteredList<>(filteredContacts, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
                } else if (person.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches name
                } else if (person.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches phone number
                } else if (person.getInitials().getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getJobTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (person.getAccount().getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(person.getEmail()).indexOf(lowerCaseFilter) != -1)
                    return true;// Filter matches email

                else
                    return false; // Does not match.
            });
        });
        SortedList<Contact> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewContacts.comparatorProperty());
        tableViewContacts.setItems(sortedData);
    }

    public void mailAction(ActionEvent actionEvent) {
        String addresses = "";
        ObservableList<Contact> contacts = tableViewContacts.getSelectionModel().getSelectedItems();
        ArrayList<Contact> clients = new ArrayList<Contact>(contacts);
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

    public void addAction(ActionEvent actionEvent) {
        dao.addContact(fldName.getText(), fldJobTitle.getText(), choiceAccount.getSelectionModel().getSelectedItem().getId(), fldEmail.getText(), fldPhone.getText(), user.getId(), user.getId());
        listContacts = FXCollections.observableArrayList(dao.contacts());
        tableViewContacts.setItems(listContacts);
    }

    public void deleteAction(ActionEvent actionEvent) {
        dao.deleteContact(tableViewContacts.getSelectionModel().getSelectedItem().getId());
        tableViewContacts.getSelectionModel().clearSelection();
        listContacts = FXCollections.observableArrayList(dao.contacts());
        tableViewContacts.setItems(listContacts);
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    public void unselectAction(ActionEvent Actionevent) {
        tableViewContacts.getSelectionModel().clearSelection();
    }

    public void updateAction(ActionEvent actionEvent) {

        int id = tableViewContacts.getSelectionModel().getSelectedItem().getId();
        int accountId = choiceAccount.getSelectionModel().getSelectedItem().getId();
        dao.updateContact(fldName.getText(), fldJobTitle.getText(), accountId, fldEmail.getText(), fldPhone.getText(), user.getId(), id);
        listContacts = FXCollections.observableArrayList(dao.contacts());
        tableViewContacts.setItems(listContacts);

    }
}
