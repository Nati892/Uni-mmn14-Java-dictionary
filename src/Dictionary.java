import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class
Dictionary extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("Dictionary.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);//disable resizing for screen
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
