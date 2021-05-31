package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.entities.Attendance;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.entities.Urgency;
import hospitalsystem.model.service.AttendanceService;
import hospitalsystem.model.service.DoctorService;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.UrgencyService;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AttendenceController implements Initializable {

    private final Utils<?> utils = new Utils<>();

    public UrgencyService urgencyService;
    public AttendanceService attendanceService;

    Urgency urgencyMain;
    Doctor doctorMain;

    // ======== PANES =======
    @FXML
    private Pane prescripitionPane;
    @FXML
    private AnchorPane root;
    // ======== register =======
    @FXML
    private TextField namePatientUrgency;
    @FXML
    private TextArea symptoms;
    @FXML
    private TextField date;
    @FXML
    private TextField time;
    @FXML
    private TextField priority;
    @FXML
    private TextArea report;
    @FXML
    private TextArea prescription;
    @FXML
    private Text registerOutput;


    // ======= CONSTRUCTOR ======
    public AttendenceController(Urgency urgencyMain, Doctor doctorMain) {
        this.urgencyMain = urgencyMain;
        this.doctorMain = doctorMain;
    }

    // ======= STANDART =======
    @FXML
    void back(ActionEvent event) throws IOException {
        DoctorController pc = new DoctorController(doctorMain);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/DoctorView.fxml"));
        fxmlloader.setController(pc);

        callStage(event, fxmlloader);
    }

    // ======= MENU FUNCTIONS =======
    private void preencherAtendimento() {
        namePatientUrgency.setText(urgencyMain.getPatient().getName());
        symptoms.setText(urgencyMain.getSymptoms());
        date.setText(urgencyMain.getDate());
        time.setText(urgencyMain.getTime());
        priority.setText(urgencyMain.getPriority());
    }

    @FXML
    private void save() {

        try {
            Random random = new Random();
            int numero = random.nextInt(100);
            String code = String.valueOf(numero);

            Attendance attendance = new Attendance(code);
            attendance.setPatient(urgencyMain.getPatient());
            attendance.setUrgency(urgencyMain);
            attendance.setDoctor(doctorMain);
            attendance.setReport(report.getText().trim());
            attendance.setPrescription(prescription.getText().trim());

                Exception registryException = null;
                try {
                    attendanceService.register(attendance);

                } catch (DuplicatedEntryException e) {
                    registryException = e;
                }
                urgencyService.remove(urgencyMain.getPatient().getCpf());
                utils.outputRegistrationResultToUser(registerOutput, registryException, "Atendimentp");

        } catch (NullPointerException e) {
            utils.showMissingFieldAlert();
        }

    }

    @FXML
    private void pescription() {
        prescripitionPane.setVisible(true);
        System.out.println("CLICOU AQUI");
    }

    @FXML
    private void savePrescription() {
        prescripitionPane.setVisible(false);
    }

    // ======= MODULES FUNCTIONS =======
    public void callStage(ActionEvent event, FXMLLoader fxmlloader) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Simulation");
        stage.setScene(new Scene(fxmlloader.load()));

        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pass window root pane
        utils.setRoot(root);
        // Load and control background panes view
        preencherAtendimento();
        // Load instance
        Backup b = new Backup();
        b.restore();

        urgencyService = UrgencyService.getInstance();
        attendanceService = AttendanceService.getInstance();


    }
}
