package View;

import Presenter.LogInP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LogInGUI implements ILogInView {
    @FXML
    Label invalidData;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button logInButton;

    public LogInGUI() {

    }

    @Override
    public void setInvalidData(String error) {
        invalidData.setText(error);
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
    public Button getLogInButton() {
        return logInButton;
    }

    public void logIn(ActionEvent actionEvent) {
        LogInP logInP = new LogInP();
        int userType = logInP.logIn(this, actionEvent);
        if (userType == 1) {
            try {
                setInvalidData("");
                Parent employeeAccess = FXMLLoader.load(getClass().getResource("../userView.fxml"));
                Scene scene = new Scene(employeeAccess, 930, 780);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Image img = new Image("./c3.png");
                window.setResizable(true);
                window.getIcons().add(img);
                window.setTitle("Employee page");
                window.setScene(scene);
                window.show();
            } catch (Exception e) {
            }
        } else {
            if (userType == 2) {
                try {
                    setInvalidData("");
                    Parent adminAccess = FXMLLoader.load(getClass().getResource("../adminView.fxml"));
                    Scene scene = new Scene(adminAccess, 700, 700);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Image img = new Image("./c3.png");
                    window.setResizable(true);
                    window.getIcons().add(img);
                    window.setTitle("Admin page");
                    window.setScene(scene);
                    window.show();
                } catch (Exception e) {
                }
            } else {
                setInvalidData("Invalid data. Please try again!");
            }
        }
    }
}
