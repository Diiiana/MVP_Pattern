package View;

import Model.User;
import Presenter.AdministratorP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdministratorGUI implements IAdministratorView {
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private Button viewUsersButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField roleField;
    @FXML
    private Button addUserButton;
    @FXML
    private Label invalidDataLabel;
    @FXML
    private ComboBox selectUser;

    @FXML
    private TextField newUsernameField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField newRoleField;
    @FXML
    private Button updateUserButton;
    @FXML
    private Label updateInvalidData;
    @FXML
    private ComboBox deleteUserComboBox;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox workplace;


    public AdministratorGUI() {

    }

    public void viewAllUsers() {
        AdministratorP administratorP = new AdministratorP();
        administratorP.viewAllUsers(this);
    }

    public void addUser() {
        AdministratorP administratorP = new AdministratorP();
        if (administratorP.addUser(this)) {
            invalidDataLabel.setText("");
            usernameField.setText("");
            passwordField.setText("");
            roleField.setText("");
        } else {
            invalidDataLabel.setText("Invalid data! Role should be admin or employee. \n Name and password longer than 5 characters.");
        }

    }

    public void fillSelectUser() {
        AdministratorP administratorP = new AdministratorP();
        administratorP.fillSelectUser(this);
    }

    public void setSelectedUser() {
        AdministratorP administratorP = new AdministratorP();
        administratorP.setSelectedUser(this);
    }

    public void updateUser() {
        AdministratorP administratorP = new AdministratorP();
        if (administratorP.updateUser(this)) {
            updateInvalidData.setText("");
            newUsernameField.setText("");
            newPasswordField.setText("");
            newRoleField.setText("");
        } else {
            updateInvalidData.setText("Invalid data! Role should be admin or employee. \n Name and password longer than 5 characters.");
        }
    }

    public void fillDeleteUser() {
        AdministratorP administratorP = new AdministratorP();
        administratorP.fillDeleteUser(this);
    }

    public void deleteUser() {
        AdministratorP administratorP = new AdministratorP();
        if (administratorP.deleteUser(this)) {
            deleteUserComboBox.setValue("");
        }
    }

    public void logOut(ActionEvent actionEvent) {
        try {
            Parent logInAccess = FXMLLoader.load(getClass().getResource("../login.fxml"));
            Scene scene = new Scene(logInAccess, 700, 700);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Image img = new Image("./c3.png");
            window.setResizable(true);
            window.getIcons().add(img);
            window.setTitle("Cake Shop");
            window.setScene(scene);
            window.show();
        } catch (Exception exception) {

        }
    }

    public void fillWorkplace() {
        AdministratorP administratorP = new AdministratorP();
        administratorP.fillWorkplace(this);
    }

    @Override
    public String getUsernameField() {
        return usernameField.getText();
    }

    @Override
    public void setUsernameField(String text) {
        usernameField.setText(text);
    }

    @Override
    public String getPasswordField() {
        return passwordField.getText();
    }

    @Override
    public void setPasswordField(String text) {
        passwordField.setText(text);
    }

    @Override
    public String getRoleField() {
        return roleField.getText();
    }

    @Override
    public void setRoleField(String text) {
        roleField.setText(text);
    }

    @Override
    public String getNewUsernameField() {
        return newUsernameField.getText();
    }

    @Override
    public void setNewUsernameField(String text) {
        newUsernameField.setText(text);
    }

    @Override
    public String getNewPasswordField() {
        return newPasswordField.getText();
    }

    @Override
    public void setNewPasswordField(String text) {
        newPasswordField.setText(text);
    }

    @Override
    public String getNewRoleField() {
        return newRoleField.getText();
    }

    @Override
    public void setNewRoleField(String text) {
        newRoleField.setText(text);
    }

    @Override
    public ComboBox getSelectUser() {
        return selectUser;
    }

    @Override
    public ComboBox getDeleteUserComboBox() {
        return deleteUserComboBox;
    }

    @Override
    public ComboBox getWorkplace() {
        return workplace;
    }

    @Override
    public TableView<User> getTableView() {
        return tableView;
    }

    @Override
    public TableColumn<User, String> getUsernameColumn() {
        return usernameColumn;
    }

    @Override
    public TableColumn<User, String> getPasswordColumn() {
        return passwordColumn;
    }

    @Override
    public TableColumn<User, String> getRoleColumn() {
        return roleColumn;
    }
}
