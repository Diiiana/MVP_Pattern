package View;

import javafx.scene.control.Button;

public interface ILogInView {

    void setInvalidData(String error);

    String getUsernameField();

    void setUsernameField(String text);

    String getPasswordField();

    void setPasswordField(String text);

    Button getLogInButton();
}
