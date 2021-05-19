package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.PatientService;
import hospitalsystem.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PatientController implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private PatientService patientService;
    public ExamesService examesService;

    Patient patientMain;
    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;
    // ======== register =======
    @FXML
    private Pane homePane;
    @FXML
    private Pane regiterDataPane;
    @FXML
    private Pane examePane;
    @FXML
    private Pane schedulePane;
    // ====== REGISTER DATA =======
    @FXML
    private Text registerOutput;
    @FXML
    private TextField cpfRegister;
    @FXML
    private TextField nameRegister;
    @FXML
    private TextField phoneNumberRegister;
    @FXML
    private TextField emailRegister;

    // ======= CONSTRUCTOR =======


    public PatientController(Patient patientMain) {
        this.patientMain = patientMain;
    }

    // ======= STANDART =======
    @FXML
    private void back(ActionEvent event) throws IOException {
        utils.back(root, event);
    }
    // ======= MENU FUNCTIONS =======
    @FXML
    private void showRegisterDataView() {
        utils.showPane("regiterDataPane");
        viewFind();
    }
    @FXML
    private void showHomeView() {
        utils.showPane("homePane");
    }
    @FXML
    private void showExameView() {
        utils.showPane("examePane");
    }
    @FXML
    private void scheduleExameView() {
        utils.showPane("schedulePane");
    }

    // ======= PATIENTS FUNCTIONS =======
    @FXML
    private void scheduleExame() {
        Random random = new Random();
        int numero = random.nextInt(100);
        String code = String.valueOf(numero);
        Exames exame = new Exames(code);

        exame.setName("Exame de sangue");
        exame.setPatient(patientMain);

        try {
            examesService.register(exame);
        } catch (DuplicatedEntryException e) {
            e.printStackTrace();
        }


    }


    // ======= MODULES FUNCTIONS =======
    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }
    // ======= BACKUP DATA METHODS =======
    private void viewFind() {
        //setTextFieldsWithAttributes(student, cpf, name, birthDate, email, phoneNumber, address);
    nameRegister.setText(patientMain.getName());
    cpfRegister.setText(patientMain.getCpf());
    phoneNumberRegister.setText(patientMain.getPhoneNumber());
    emailRegister.setText(patientMain.getEmail());


    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pass window root pane
        utils.setRoot(root);

        // Load and control background panes view
        List<Pane> panesList = new ArrayList<>(
                Arrays.asList( homePane, regiterDataPane, examePane,schedulePane));
        utils.setPanesList(panesList);
        showHomeView();

        // Load instance
        //Backup.restore();
        patientService = PatientService.getInstance();
        examesService = ExamesService.getInstance();
    }
}
