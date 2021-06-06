package hospitalsystem.controller;

import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.service.DoctorService;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginDoctorController implements Initializable {

    private final Utils<Doctor> utils = new Utils<>();
    private DoctorService doctorService;
    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;

    // ======== PACIENTS TEXTS AREA =======
    @FXML
    private TextField loginArea;
    @FXML
    private PasswordField passwordArea;


    // ======= STANDART =======

    @FXML
    private void back(ActionEvent event) throws IOException {
        utils.back(root, event);
    }

    @FXML
    void viewFind(ActionEvent event) throws IOException {
        Doctor doctor = utils.search(doctorService, loginArea);

        if(!doctor.getPassword().equals(passwordArea.getText())) {
            System.out.println("senha errada");
            doctor = null;
        }

        if(doctor!= null) {
            DoctorController pc = new DoctorController(doctor);
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/DoctorView.fxml"));
            fxmlloader.setController(pc);

            callStage(event, fxmlloader);
        }else{
            utils.showPacientNotFound("Médico");
            }
    }


    public void callStage(ActionEvent event, FXMLLoader fxmlloader) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Hospital H+");
        stage.setScene(new Scene(fxmlloader.load()));

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Backup b = new Backup();
        b.restore();
        doctorService = DoctorService.getInstance();
    }


    // ======= HELPER METHODS =======



}
