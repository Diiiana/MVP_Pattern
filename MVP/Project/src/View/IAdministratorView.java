package View;

import Model.User;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public interface IAdministratorView {

    String getUsernameField();

    void setUsernameField(String text);

    String getPasswordField();

    void setPasswordField(String text);

    String getRoleField();

    void setRoleField(String text);

    String getNewUsernameField();

    void setNewUsernameField(String text);

    String getNewPasswordField();

    void setNewPasswordField(String text);

    String getNewRoleField();

    void setNewRoleField(String text);

    ComboBox getSelectUser();

    ComboBox getDeleteUserComboBox();

    ComboBox getWorkplace();

    TableView<User> getTableView();

    TableColumn<User, String> getUsernameColumn();

    TableColumn<User, String> getPasswordColumn();

    TableColumn<User, String> getRoleColumn();
}
