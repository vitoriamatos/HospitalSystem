package hospitalsystem.controller;

import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Urgency;
import hospitalsystem.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ExameController implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private Exames exameMain;
    private Doctor doctorMain;

    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;
    @FXML
    private TextField namePatient;

    // ======= CONSTRUCTOR ======
    public ExameController(Exames exameMain, Doctor doctorMain) {
        this.exameMain = exameMain;
        this.doctorMain = doctorMain;
    }

    private void findPatientName(){
        //namePatient.setText(exameMain.getPatient().getName());
            System.out.println("Name Patient:" + exameMain.getPatient().getName() );

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Pass window root pane
        utils.setRoot(root);
        //findPatientName();


    }
}