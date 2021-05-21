package View;

import javafx.scene.control.*;

public interface IUserView {

    ComboBox getAllCakeShops();

    TextArea getAllCakesFromCakeShop();

    String getSearchCakeField();

    void setFoundCakeField(String s);

    boolean getJsonRadioButton();

    boolean getCsvRadioButton();

    String getCakeName();

    void setCakeName(String s);

    String getCakeDisponibility();

    void setCakeDisponibility(String s);

    String getCakeAvailability();

    void setCakeAvailability(String s);

    String getCakePrice();

    void setCakePrice(String s);

    void setValidDataLabel(String s);

    ComboBox getSelectCakeComboBox();

    String getNewCakeName();

    void setNewCakeName(String s);

    String getNewCakeDisponibility();

    void setNewCakeDisponibility(String s);

    String getNewCakeAvailability();

    void setNewCakeAvailability(String s);

    String getNewCakePrice();

    void setNewCakePrice(String s);

    boolean getAvailabilityRadioButton();

    Double getFirstPrice();

    Double getSecondPrice();

    int getDisponibilityField();

    ComboBox getOptionalComboBox();
}
