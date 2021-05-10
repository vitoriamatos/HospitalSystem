package hospitalsystem.controller;

import hospitalsystem.model.Patient;
import hospitalsystem.service.PatientService;
import hospitalsystem.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginController {

    private final Utils<Patient> utils = new Utils<>();
    private PatientService patientService;
    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;

    // ======== PACIENTS TEXTS AREA =======
    @FXML
    private TextField loginArea;
    @FXML
    private TextField passwordArea;


    // ======= STANDART =======

    @FXML
    private void back(ActionEvent event) throws IOException {
        utils.back(root, event);
    }


    @FXML
    void viewFind() {
    Patient patient = utils.search(patientService, passwordArea);

        if(patient != null){
            System.out.println("eba");
        }else{
            utils.showPacientNotFound("Patient");
        }
    }

    // ======= PATIENTS FUNCTIONS =======

    //FIND


    // ======= MODULES FUNCTIONS =======


    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }


    // ======= HELPER METHODS =======



}
