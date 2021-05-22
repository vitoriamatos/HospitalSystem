package hospitalsystem.controller;

import hospitalsystem.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicialController {

    private final Utils<?> utils = new Utils<>();


    // ======= MODULES FUNCTIONS =======

    @FXML
    void doctorModule(ActionEvent event) throws IOException {
        showModule(event, "LoginDoctorView");
    }

    @FXML
    void patientModule(ActionEvent event) throws IOException {
        showModule(event, "LoginPatientView");
    }

    @FXML
    void adminModule(ActionEvent event) throws IOException {
        showModule(event, "AdminView");
    }




    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    public void back(ActionEvent actionEvent) {
    }


    // ======= HELPER METHODS =======



}
