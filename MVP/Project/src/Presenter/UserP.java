package Presenter;

import Model.*;
import View.IUserView;
import View.UserGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.json.simple.JSONArray;
import org.json.simple.JsonObject;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UserP {
    private static int fileCount = 1;

    public UserP() {

    }

    public void viewCakesFromCakeShop(IUserView view) {
        CakePersistence cakePersistence = new CakePersistence();
        try {
            Cakes cakes = cakePersistence.getCakesFromCakeShop(view.getAllCakeShops().getValue().toString());
            view.getAllCakesFromCakeShop().setText(cakes.toString());

        } catch (Exception e) {
        }
    }

    public CakeShops viewAllCakeShops() {
        CakePersistence cakePersistence = new CakePersistence();
        return cakePersistence.getCakeShops();
    }

    public String getCakeByName(IUserView view) {
        Cake cake = null;
        try {
            CakePersistence cakePersistence = new CakePersistence();
            cake = cakePersistence.searchCakeWithName(view.getSearchCakeField(), LogInP.getLoggedUser());
            view.setFoundCakeField(cakePersistence.cakeByNameFromCakeShops(cake) + cake.toString());
            return cakePersistence.cakeByNameFromCakeShops(cake);
        } catch (Exception e) {
        }
        view.setFoundCakeField("Cake not found.");
        return null;
    }

    public Cake getCakeByName(String name) {
        Cake cake = null;
        try {
            CakePersistence cakePersistence = new CakePersistence();
            cake = cakePersistence.searchCakeWithName(name, LogInP.getLoggedUser());
            return cake;
        } catch (Exception e) {
        }
        return null;
    }

    public void saveReports(IUserView iUserView) throws IOException {
        CakePersistence cakePersistence = new CakePersistence();
        if (iUserView.getCsvRadioButton()) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Report_" + fileCount + ".csv"));
                fileCount++;
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                if (iUserView.getAllCakeShops().getValue() != null) {
                    printWriter.print(cakePersistence.getCakesFromCakeShop(iUserView.getAllCakeShops().getValue().toString()).toString());
                } else {
                    printWriter.print(cakePersistence.readFile().toString());
                }
                printWriter.flush();
                printWriter.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            if (iUserView.getJsonRadioButton()) {
                JSONArray cakes = new JSONArray();
                Cakes cakes1 = new Cakes();
                if (iUserView.getAllCakeShops().getValue() != null) {
                    cakes1 = cakePersistence.getCakesFromCakeShop((iUserView.getAllCakeShops().getValue().toString()));
                } else {
                    cakes1 = cakePersistence.readFile();
                }
                for (Cake cake : cakes1.getCakeList()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.put("name", cake.getName());
                    jsonObject.put("disponibility", cake.getDisponibility());
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(cake.getAvailability());
                    jsonObject.put("availability", date);
                    jsonObject.put("price", cake.getPrice());
                    cakes.add(jsonObject);
                }
                try (FileWriter file = new FileWriter("Report_" + fileCount + ".json")) {
                    fileCount++;
                    file.write(cakes.toJSONString());
                    file.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addCake(IUserView iUserView) {
        try {
            Date date = new SimpleDateFormat("dd-MM-yyy").parse(iUserView.getCakeAvailability());
            Cake cake = new Cake(iUserView.getCakeName(), Integer.parseInt(iUserView.getCakeDisponibility()), date, Float.parseFloat(iUserView.getCakePrice()));
            CakePersistence cakePersistence = new CakePersistence();
            cakePersistence.addCake(cake);
            if (iUserView.getOptionalComboBox().getValue() != null) {
                cakePersistence.addCakeToCakeShop(iUserView.getOptionalComboBox().getValue().toString(), cake.getId());
            } else {
                AdministratorP administratorP = new AdministratorP();
                cakePersistence.addCakeToCakeShop(administratorP.workPlaceForUser(LogInP.getLoggedUser()), cake.getId());
            }
        } catch (Exception e) {

        }
    }

    public void deleteCake(IUserView iUserView) {
        try {
            CakePersistence cakePersistence = new CakePersistence();
            cakePersistence.deleteCake(iUserView.getSelectCakeComboBox().getValue().toString(), LogInP.getLoggedUser());
            iUserView.getSelectCakeComboBox().setValue("");
            iUserView.setNewCakeAvailability("");
            iUserView.setNewCakeName("");
            iUserView.setNewCakePrice("");
            iUserView.setNewCakeDisponibility("");
        } catch (Exception e) {

        }
    }

    public void updateCake(UserGUI userGUI) {
        CakePersistence cakePersistence = new CakePersistence();
        try {
            Date date = new SimpleDateFormat("dd-MM-yyy").parse(userGUI.getNewCakeAvailability());
            Cake cake = new Cake(userGUI.getNewCakeName(), Integer.parseInt(userGUI.getNewCakeDisponibility()), date, Float.parseFloat(userGUI.getNewCakePrice()));
            cakePersistence.updateCake(userGUI.getSelectCakeComboBox().getValue().toString(), cake);
            userGUI.setNewCakeName("");
            userGUI.setNewCakePrice("");
            userGUI.setNewCakeAvailability("");
            userGUI.setNewCakeDisponibility("");
        } catch (Exception e) {
        }
    }

    public Cakes filterCakes() {
        CakePersistence cakePersistence = new CakePersistence();
        return cakePersistence.returnByValability(LogInP.getLoggedUser());
    }

    public Cakes filterBetweenPrices(IUserView view) {
        CakePersistence cakePersistence = new CakePersistence();
        return cakePersistence.filterBetweenPrices(view.getFirstPrice(), view.getSecondPrice(), LogInP.getLoggedUser());
    }

    public Cakes filterByDisponibility(IUserView view) {
        CakePersistence cakePersistence = new CakePersistence();
        return cakePersistence.filterByDisponibility(view.getDisponibilityField(), LogInP.getLoggedUser());
    }

    public ObservableList<Cake> getObservableCakes(Cakes cakes) {
        ObservableList<Cake> cakes1 = FXCollections.observableArrayList();
        for (Cake cake : cakes.getCakeList()) {
            cakes1.add(cake);
        }
        return cakes1;
    }

    public ObservableList<PieChart.Data> createPieChartData(String about) throws ParseException {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        CakePersistence cakePersistence = new CakePersistence();
        Cakes cakes = cakePersistence.readFile();
        for (Cake cake : cakes.getCakeList()) {
            if (about.equals("Price")) {
                pieData.add(new PieChart.Data(cake.getName(), cake.getPrice()));
            } else {
                if (about.equals("Disponibility")) {
                    pieData.add(new PieChart.Data(cake.getName(), cake.getDisponibility()));
                } else {
                    if (about.equals("Availability")) {
                        Date currentDate = new Date(System.currentTimeMillis());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        String firstDate = dateFormat.format(currentDate);
                        String secondDate = dateFormat.format(cake.getAvailability());
                        Date firstDate1 = dateFormat.parse(firstDate);
                        Date secondDate1 = dateFormat.parse(secondDate);
                        long diffMillies = Math.abs(secondDate1.getTime() - firstDate1.getTime());
                        long diff = TimeUnit.DAYS.convert(diffMillies, TimeUnit.MILLISECONDS);
                        pieData.add(new PieChart.Data(cake.getName(), diff));
                    }
                }
            }
        }
        return pieData;
    }

    public ObservableList<XYChart.Data> createLineChartData(String about) {
        ObservableList<LineChart.Data> lineData = FXCollections.observableArrayList();
        CakePersistence cakePersistence = new CakePersistence();
        Cakes cakes = cakePersistence.readFile();
        for (Cake cake : cakes.getCakeList()) {
            if (about.equals("Price")) {
                lineData.add(new XYChart.Data(cake.getName(), cake.getPrice()));
            } else {
                if (about.equals("Disponibility")) {
                    lineData.add(new XYChart.Data(cake.getName(), cake.getDisponibility()));
                } else {
                    if (about.equals("Availability")) {
                        try {
                            Date currentDate = new Date(System.currentTimeMillis());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            String firstDate = dateFormat.format(currentDate);
                            String secondDate = dateFormat.format(cake.getAvailability());
                            Date firstDate1 = dateFormat.parse(firstDate);
                            Date secondDate1 = dateFormat.parse(secondDate);
                            long diffMillies = Math.abs(secondDate1.getTime() - firstDate1.getTime());
                            long diff = TimeUnit.DAYS.convert(diffMillies, TimeUnit.MILLISECONDS);
                            lineData.add(new XYChart.Data(cake.getName(), diff));
                        } catch (Exception e) {

                        }
                    }
                }
            }
        }
        return lineData;
    }

    public ObservableList<BarChart.Data> createBarChartData(String about) {
        ObservableList<BarChart.Data> lineData = FXCollections.observableArrayList();
        CakePersistence cakePersistence = new CakePersistence();
        Cakes cakes = cakePersistence.readFile();
        for (Cake cake : cakes.getCakeList()) {
            if (about.equals("Price")) {
                lineData.add(new BarChart.Data(cake.getName(), cake.getPrice()));
            } else {
                if (about.equals("Disponibility")) {
                    lineData.add(new BarChart.Data(cake.getName(), cake.getDisponibility()));
                } else {
                    if (about.equals("Availability")) {
                        try {
                            Date currentDate = new Date(System.currentTimeMillis());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            String firstDate = dateFormat.format(currentDate);
                            String secondDate = dateFormat.format(cake.getAvailability());
                            Date firstDate1 = dateFormat.parse(firstDate);
                            Date secondDate1 = dateFormat.parse(secondDate);
                            long diffMillies = Math.abs(secondDate1.getTime() - firstDate1.getTime());
                            long diff = TimeUnit.DAYS.convert(diffMillies, TimeUnit.MILLISECONDS);
                            lineData.add(new BarChart.Data(cake.getName(), diff));
                        } catch (Exception e) {

                        }
                    }
                }
            }
        }
        return lineData;
    }

    public void fillWithShops(UserGUI userGUI) {
        try {
            userGUI.getAllCakeShops().getItems().clear();
            CakeShops cakeShops = viewAllCakeShops();
            for (CakeShop cakeShop : cakeShops.getCakeShopList()) {
                userGUI.getAllCakeShops().getItems().add(cakeShop.getName());
            }
        } catch (Exception e) {

        }
    }

    public void fillWithShopsOptional(UserGUI userGUI) {
        userGUI.getOptionalComboBox().getItems().clear();
        CakeShops cakeShops = viewAllCakeShops();
        for (CakeShop cakeShop : cakeShops.getCakeShopList()) {
            userGUI.getOptionalComboBox().getItems().add(cakeShop.getName());
        }
    }

    public void fillSelectCakeComboBox(UserGUI userGUI) {
        try {
            userGUI.getSelectCakeComboBox().getItems().clear();
            CakePersistence cakePersistence = new CakePersistence();
            String name = cakePersistence.cakeShopWhereUserWorks(LogInP.getLoggedUser()).getName();
            Cakes cakes = cakePersistence.getCakesFromCakeShop(name);
            for (Cake cake : cakes.getCakeList()) {
                userGUI.getSelectCakeComboBox().getItems().add(cake.getName());
            }
        } catch (Exception e) {

        }
    }

    public void fillFields(UserGUI userGUI) {
        try {
            String s = (String) userGUI.getSelectCakeComboBox().getValue();
            if (s != null) {
                UserP userP = new UserP();
                Cake cake = userP.getCakeByName(s);
                userGUI.setNewCakeName(cake.getName());
                userGUI.setNewCakeDisponibility(String.valueOf(cake.getDisponibility()));

                DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
                userGUI.setNewCakeAvailability(formatter1.format(formatter.parse(cake.getAvailability().toString())));
                userGUI.setNewCakePrice(String.valueOf(cake.getPrice()));
            }
        } catch (Exception e) {

        }
    }
}
