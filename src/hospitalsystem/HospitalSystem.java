package hospitalsystem;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HospitalSystem extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/fxmls/InicialView.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        // stage.getIcons().add(new Image("academiccontrolsystem/view/images/icon.png"));
        stage.show();
    }

}
