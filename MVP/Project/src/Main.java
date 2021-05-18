import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./login.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Cake Shop");
        primaryStage.setScene(new Scene(root, 700, 700));
        Image img = new Image("./c3.png");
        primaryStage.getIcons().add(img);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
