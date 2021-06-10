package hospitalsystem.controller;

import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Urgency;

import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.utils.ArquivoExames;

import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExameController implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private Exames exameMain;
    private Doctor doctorMain;

    public ExamesService examesService;

    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;
    @FXML
    private Text namePatient;
    @FXML
    private Text exameType;
    @FXML
    private TextArea exameResult;

    // ======= CONSTRUCTOR =====
    public ExameController(Exames exameMain, Doctor doctorMain) {
        this.exameMain = exameMain;
        this.doctorMain = doctorMain;
    }
        @FXML
        private void send() throws MissingEntryException {
                exameMain.setStatus(1);
                exameMain.setResult(exameResult.getText());
                //Method for service save
                examesService.modifyExame(exameMain);
                utils.showSendExame();
        }

    @FXML
    void back(ActionEvent event) throws IOException {
        Backup b = new Backup();
        b.backup(b);
        DoctorController pc = new DoctorController(doctorMain);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/DoctorView.fxml"));
        fxmlloader.setController(pc);
        callStage(event, fxmlloader);
    }

    // ======= MODULES FUNCTIONS =======
    public void callStage(ActionEvent event, FXMLLoader fxmlloader) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Hospital H+");
        stage.setScene(new Scene(fxmlloader.load()));

        stage.show();

    }

        @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(exameMain.getPatient().getName());
        namePatient.setText(exameMain.getPatient().getName());
        exameType.setText(exameMain.getName());

        // Pass window root pane
        utils.setRoot(root);
        //findPatientName();
        examesService = ExamesService.getInstance();

    }
}
