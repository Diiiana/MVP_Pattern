package View;

import Presenter.UserP;
import Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.event.*;

import java.io.IOException;

public class UserGUI implements IUserView {
    @FXML
    private ComboBox<String> allCakeShops;
    @FXML
    private Button viewAllCakesButton;
    @FXML
    private TextArea allCakesFromCakeShop;
    @FXML
    private TextField searchCakeField;
    @FXML
    private TextArea foundCakeField;
    @FXML
    private Button searchCakeButton;
    @FXML
    private RadioButton jsonRadioButton;
    @FXML
    private RadioButton csvRadioButton;
    @FXML
    private Button saveReportsButton;
    @FXML
    private TextField cakeName;
    @FXML
    private TextField cakeDisponibility;
    @FXML
    private TextField cakeAvailability;
    @FXML
    private TextField cakePrice;
    @FXML
    private Label validDataLabel;
    @FXML
    private ComboBox selectCakeComboBox;
    @FXML
    private Button deleteCakeButton;
    @FXML
    private TextField newCakeNameField;
    @FXML
    private TextField newCakeDisponibilityField;
    @FXML
    private TextField newCakeAvailabilityField;
    @FXML
    private TextField newCakePriceField;
    @FXML
    private RadioButton availabilityRadioButton;
    @FXML
    private TextField disponibilityField;
    @FXML
    private TextField firstPrice;
    @FXML
    private TextField secondPrice;
    @FXML
    private Button logOutButton;
    @FXML
    private TableView<Cake> tableView;
    @FXML
    private TableColumn<Cake, String> cakeNameColumn;

    @FXML
    private TableColumn<Cake, Integer> quantityColumn;

    @FXML
    private TableColumn<Cake, String> availabilityColumn;

    @FXML
    private TableColumn<Cake, Double> priceColumn;

    @FXML
    private ComboBox<String> optionalComboBox;

    @FXML
    private ComboBox<String> viewStatisticsComboBox;

    @FXML
    private Button viewStatisticsButton;

    @FXML
    private ComboBox<String> statisticsForComboBox;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private NumberAxis value;
    @FXML
    private CategoryAxis cakeNameA;
    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis barChartX;

    @FXML
    private NumberAxis barChartY;

    public UserGUI() {
    }

    public void viewCakesFromCakeShop() {
        UserP userP = new UserP();
        userP.viewCakesFromCakeShop(this);
        allCakesFromCakeShop.setEditable(false);
    }

    public void fillWithShops() {
        UserP userP = new UserP();
        userP.fillWithShops(this);
    }

    public void fillWithShopsOptional() {
        UserP userP = new UserP();
        userP.fillWithShopsOptional(this);
    }

    public void searchCake() {
        UserP userP = new UserP();
        userP.getCakeByName(this);
        foundCakeField.setEditable(false);
    }

    public void saveReports() {
        UserP userP = new UserP();
        try {
            userP.saveReports(this);
        } catch (Exception e) {

        }
    }

    public void addCake() {
        UserP userP = new UserP();
        try {
            userP.addCake(this);
            setValidDataLabel("");
            setCakeName("");
            setCakeDisponibility("");
            setCakeAvailability("");
            setCakePrice("");
        } catch (Exception e) {
            e.printStackTrace();
            setValidDataLabel("Invalid data.");
        }
    }

    public void fillSelectCakeComboBox() {
        UserP userP = new UserP();
        userP.fillSelectCakeComboBox(this);
    }

    public void deleteCake() {
        UserP userP = new UserP();
        userP.deleteCake(this);
    }

    public void fillFields() {
        UserP userP = new UserP();
        userP.fillFields(this);
    }

    public void updateCake() {
        UserP userP = new UserP();
        userP.updateCake(this);
    }

    public void filterCakes() {
        try {
            UserP userP = new UserP();
            Cakes cakes = new Cakes();
            if (getAvailabilityRadioButton()) {
                cakes = userP.filterCakes();
            }
            if (!firstPrice.getText().equals("") || !secondPrice.getText().equals("")) {
                cakes = userP.filterBetweenPrices(this);
            }
            if (!disponibilityField.getText().equals("")) {
                cakes = userP.filterByDisponibility(this);
            }
            for (Cake cake : userP.getObservableCakes(cakes)) {
                cakeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("disponibility"));
                availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
                priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
            tableView.setItems(userP.getObservableCakes(cakes));
            disponibilityField.setText("");
            availabilityRadioButton.setSelected(false);
            firstPrice.setText("");
            secondPrice.setText("");
            lineChart.setTitle("");
        } catch (Exception e) {

        }
    }

    public void fillStatistics() {
        viewStatisticsComboBox.getItems().clear();
        viewStatisticsComboBox.getItems().add("Pie Chart");
        viewStatisticsComboBox.getItems().add("Line Chart");
        viewStatisticsComboBox.getItems().add("Bar Chart");
    }

    public void fillStatisticsAbout() {
        statisticsForComboBox.getItems().clear();
        statisticsForComboBox.getItems().add("Price");
        statisticsForComboBox.getItems().add("Disponibility");
        statisticsForComboBox.getItems().add("Availability");
    }

    public void showStatistics(ActionEvent actionEvent) throws IOException {
        barChart.setVisible(false);
        lineChart.setVisible(false);
        pieChart.setVisible(false);
        UserP userP = new UserP();
        String about = statisticsForComboBox.getValue();
        pieChart.setVisible(false);
        lineChart.setVisible(false);
        if (about != null && viewStatisticsComboBox.getValue() != null) {
            if (viewStatisticsComboBox.getValue().equals("Pie Chart")) {
                try {
                    ObservableList<PieChart.Data> pieData = userP.createPieChartData(about);
                    pieChart.getData().clear();
                    pieChart.setData(pieData);
                    pieChart.setVisible(true);
                } catch (Exception e) {

                }

            } else {
                if (viewStatisticsComboBox.getValue().equals("Line Chart")) {
                    XYChart.Series series = new XYChart.Series();
                    ObservableList<XYChart.Data> lineData = userP.createLineChartData(about);
                    cakeNameA.setStartMargin(0);
                    cakeNameA.setEndMargin(0);
                    value.minHeight(20000000);
                    series.getData().addAll(lineData);
                    lineChart.getData().clear();
                    lineChart.getData().addAll(series);
                    lineChart.setVisible(true);
                } else {
                    if (viewStatisticsComboBox.getValue().equals("Bar Chart")) {
                        BarChart.Series series = new BarChart.Series();
                        ObservableList<BarChart.Data> lineData = userP.createBarChartData(about);
                        series.getData().addAll(lineData);
                        barChart.setVisible(true);
                        barChart.getData().clear();
                        barChart.getData().addAll(series);
                    }
                }
            }
        }
    }

    public void notSetJson() {
        jsonRadioButton.setSelected(false);
    }

    public void notSetCsv() {
        csvRadioButton.setSelected(false);
    }

    @FXML
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

    @Override
    public ComboBox getAllCakeShops() {
        return allCakeShops;
    }

    @Override
    public TextArea getAllCakesFromCakeShop() {
        return allCakesFromCakeShop;
    }

    @Override
    public String getSearchCakeField() {
        return searchCakeField.getText();
    }

    @Override
    public void setFoundCakeField(String s) {
        foundCakeField.setText(s);
    }

    @Override
    public boolean getJsonRadioButton() {
        return jsonRadioButton.isSelected();
    }

    @Override
    public boolean getCsvRadioButton() {
        return csvRadioButton.isSelected();
    }

    @Override
    public String getCakeName() {
        return cakeName.getText();
    }

    @Override
    public void setCakeName(String s) {
        cakeName.setText(s);
    }

    @Override
    public String getCakeDisponibility() {
        return cakeDisponibility.getText();
    }

    @Override
    public void setCakeDisponibility(String s) {
        cakeDisponibility.setText(s);
    }

    @Override
    public String getCakeAvailability() {
        return cakeAvailability.getText();
    }

    @Override
    public void setCakeAvailability(String s) {
        cakeAvailability.setText(s);
    }

    @Override
    public String getCakePrice() {
        return cakePrice.getText();
    }

    @Override
    public void setCakePrice(String s) {
        cakePrice.setText(s);
    }

    @Override
    public void setValidDataLabel(String s) {
        validDataLabel.setText(s);
    }

    @Override
    public ComboBox getSelectCakeComboBox() {
        return selectCakeComboBox;
    }

    @Override
    public String getNewCakeName() {
        return newCakeNameField.getText();
    }

    @Override
    public void setNewCakeName(String s) {
        newCakeNameField.setText(s);
    }

    @Override
    public String getNewCakeDisponibility() {
        return newCakeDisponibilityField.getText();
    }

    @Override
    public void setNewCakeDisponibility(String s) {
        newCakeDisponibilityField.setText(s);
    }

    @Override
    public String getNewCakeAvailability() {
        return newCakeAvailabilityField.getText();
    }

    @Override
    public void setNewCakeAvailability(String s) {
        newCakeAvailabilityField.setText(s);
    }

    @Override
    public String getNewCakePrice() {
        return newCakePriceField.getText();
    }

    @Override
    public void setNewCakePrice(String s) {
        newCakePriceField.setText(s);
    }

    @Override
    public boolean getAvailabilityRadioButton() {
        return availabilityRadioButton.isSelected();
    }

    @Override
    public Double getFirstPrice() {
        if (firstPrice.getText().equals("")) {
            return 0.0;
        } else {
            return Double.parseDouble(firstPrice.getText());
        }
    }

    @Override
    public Double getSecondPrice() {
        if (secondPrice.getText().equals("")) {
            return 0.0;
        } else {
            return Double.parseDouble(secondPrice.getText());
        }
    }

    @Override
    public int getDisponibilityField() {
        String s = disponibilityField.getText();
        if (s.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    @Override
    public ComboBox getOptionalComboBox() {
        return optionalComboBox;
    }
}
