package hospitalsystem.controller;

import hospitalsystem.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    private final Utils<?> utils = new Utils<>();

    @FXML
    private AnchorPane root;
    // ======= MODULES FUNCTIONS =======
    @FXML
    void doctorModule(ActionEvent event) throws IOException {
        showModule(event, "AdminDoctorView");
    }

    @FXML
    void patientModule(ActionEvent event) throws IOException {
        showModule(event, "AdminPatientView");
    }

    @FXML
     void back(ActionEvent event) throws IOException {
        utils.back(root, event);
    }


    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }


    // ======= HELPER METHODS =======



}
