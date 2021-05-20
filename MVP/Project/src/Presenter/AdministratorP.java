package Presenter;

import Model.*;
import View.AdministratorGUI;
import View.IAdministratorView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;


public class AdministratorP {

    public AdministratorP() {
    }

    public void viewAllUsers(IAdministratorView iAdministratorView) {
        try {
            for (User user : getObservableUsers()) {
                iAdministratorView.getUsernameColumn().setCellValueFactory(new PropertyValueFactory<User, String>("name"));
                iAdministratorView.getPasswordColumn().setCellValueFactory(new PropertyValueFactory<User, String>("password"));
                iAdministratorView.getRoleColumn().setCellValueFactory(new PropertyValueFactory<User, String>("role"));
            }
            iAdministratorView.getTableView().setItems(getObservableUsers());
        } catch (Exception e) {

        }
    }

    public ObservableList<User> getObservableUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        UserPersistence userPersistence = new UserPersistence();
        for (User user : userPersistence.readUsers().getUsersList()) {
            String mew = user.getName() + " works at " + workPlaceForUser(user.getId());
            users.add(new User(user.getId(), mew, user.getPassword(), user.getRole()));
        }
        return users;
    }

    public boolean addUser(AdministratorGUI view) {
        try {
            if (view.getUsernameField().length() < 5 || view.getPasswordField().length() < 5 || (!view.getRoleField().equals("admin") && !view.getRoleField().equals("employee"))) {
                return false;
            }
            UserPersistence userPersistence = new UserPersistence();
            userPersistence.addUser(new User(view.getUsernameField(), view.getPasswordField(), view.getRoleField()), view.getWorkplace().getValue().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillSelectUser(IAdministratorView view) {
        UserPersistence userPersistence = new UserPersistence();
        Users users = userPersistence.readUsers();
        view.getSelectUser().getItems().clear();
        for (User user : users.getUsersList()) {
            view.getSelectUser().getItems().add(user.getName());
        }
    }

    public void setSelectedUser(IAdministratorView view) {
        UserPersistence userPersistence = new UserPersistence();
        if (view.getSelectUser() != null && view.getSelectUser().getValue() != null) {
            String name = view.getSelectUser().getValue().toString().trim();
            User user = userPersistence.getUserByName(name);
            if (user != null) {
                view.setNewUsernameField(user.getName());
                view.setNewPasswordField(user.getPassword());
                view.setNewRoleField(user.getRole());
            }
        }
    }

    public boolean updateUser(IAdministratorView view) {
        String name = view.getNewUsernameField();
        String password = view.getNewPasswordField();
        String role = view.getNewRoleField();
        if (name.length() < 5 || password.length() < 5 || (!role.equals("admin") && !role.equals("employee"))) {
            return false;
        } else {
            User user = new User(name, password, role);
            UserPersistence userPersistence = new UserPersistence();
            if (userPersistence.updateUser(view.getSelectUser().getValue().toString(), user)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void fillDeleteUser(IAdministratorView view) {
        UserPersistence userPersistence = new UserPersistence();
        Users users = userPersistence.readUsers();
        view.getDeleteUserComboBox().getItems().clear();
        for (User user : users.getUsersList()) {
            view.getDeleteUserComboBox().getItems().add(user.getName());
        }
    }

    public boolean deleteUser(IAdministratorView view) {
        UserPersistence userPersistence = new UserPersistence();
        if (view.getDeleteUserComboBox().getValue().equals("")) {
            return false;
        } else {
            userPersistence.deleteUser(view.getDeleteUserComboBox().getValue().toString().trim());
            return true;
        }
    }

    public void fillWorkplace(AdministratorGUI administratorGUI) {
        CakePersistence cakePersistence = new CakePersistence();
        CakeShops cakeShops = cakePersistence.getCakeShops();
        administratorGUI.getWorkplace().getItems().clear();
        for (CakeShop cakeShop : cakeShops.getCakeShopList()) {
            administratorGUI.getWorkplace().getItems().add(cakeShop.getName());
        }
    }

    public String workPlaceForUser(String id) {
        CakePersistence cakePersistence = new CakePersistence();
        if (cakePersistence.cakeShopWhereUserWorks(id) != null) {
            return cakePersistence.cakeShopWhereUserWorks(id).getName();
        } else return " ";
    }
}
