package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.entities.Urgency;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.PatientService;
import hospitalsystem.model.service.UrgencyService;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import hospitalsystem.model.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminPatientController  implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private PatientService patientService;
    private ExamesService examesService;
    private UrgencyService urgencyService;

    // ======== MAIN PANES =======
    @FXML
    private AnchorPane root;

    // ======== register =======
    @FXML
    private Pane homePane;
    @FXML
    private Pane registerPane;
    @FXML
    private Pane solicitationPane;
    @FXML
    private Pane listAllPane;
    @FXML
    private Pane urgencyPane;
    @FXML
    private Pane urgencyRegisterPane;


    // ======== LIST =======

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> emailColumn;
    @FXML
    private TableColumn<Patient, String> nameColumn;

    // ======== SOLICITATION =======

    @FXML
    private TableView<Exames> solicitationTable;
    @FXML
    private TableColumn<Exames, String> solicitationColumn;
    @FXML
    private TableColumn<Exames, String> patientColumn;
    @FXML
    private TableColumn<Exames, String> cpfColumn;
    @FXML
    private Text examesid;


    // ======== URGENCY =======
    @FXML
    private TextField namePatientUrgency;
    @FXML
    private TextArea symptoms;
    @FXML
    private TextField date;
    @FXML
    private TextField time;
    @FXML
    private TextField cpfArea;
    @FXML
    private ComboBox<String> priority;

    @FXML
    private Text registerUrgencyOutput;

    // ====== WINDOWS =======

    // Register

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

    // ======= STANDART =======

    @FXML
    private void back(ActionEvent event) throws IOException {
        utils.back(root, event);
    }


    // ======= MODULES FUNCTIONS =======
    @FXML
    void doctorModule(ActionEvent event) throws IOException {
        showModule(event, "AdminDoctorView");
    }

    @FXML
    void patientModule(ActionEvent event) throws IOException {
        showModule(event, "AdminPatientView");
    }

    // ======= MENU FUNCTIONS =======
    @FXML
    private void showsolicitationPane() {
        utils.showPane("solicitationPane");
        populateTableSolicitation();
    }
    @FXML
    private void showRegisterPane() {
        utils.showPane("registerPane");

    }
    @FXML
    private void showListAllPane() {
        utils.showPane("listAllPane");
        populateTable();

    }

    @FXML
    private void showUrgencyPane() {
        utils.showPane("urgencyPane");
        populateTable();

    }


    // ======= FORMAT AND VALIDATE FUNCTIONS =======
    @FXML
    private void cpfFieldRegister() {
        formatCpf(cpfRegister);
    }
    @FXML
    private void phoneNumberFieldRegister() {
        formatPhoneNumber(phoneNumberRegister);
    }


    private void formatCpf(TextField textField) {
        formatTextField(textField, "###.###.###-## ", "0123456789");
    }

    private void formatPhoneNumber(TextField textField) {
        formatTextField(textField, "(##)#####-####", "0123456789");
    }


    private boolean isInputValid(TextField cpf, TextField name,  TextField email) {
        // Assert invalid inputs
        String invalidValues = "";
        if (!Validation.isCpfValid(cpf.getText())) {
            invalidValues += "CPF invalido\n";
        }
        if (!Validation.isNameValid(name.getText())) {
            invalidValues += "Nome invalido\n";
        }
        if (!Validation.isEmailValid(email.getText())) {
            invalidValues += "Email invalido";
        }
        // Show invalid inputs alert
        if (!invalidValues.isEmpty()) {
            utils.showInvalidInputsAlertToUser(invalidValues);
            return false;
        }

        return true;
    }
    private void formatTextField(TextField textField, String mask, String validCharacters) {
        academiccontrolsystem.utils.TextFieldFormatter textFieldFormatter = new academiccontrolsystem.utils.TextFieldFormatter();
        textFieldFormatter.setMask(mask);
        textFieldFormatter.setValidCharacters(validCharacters);
        textFieldFormatter.setTextField(textField);
        textFieldFormatter.formatter();
    }


    // ======= HOSPITAL TOPICS =======

    // Register Patient

    @FXML
    private void register() {
        try {
            if (isInputValid(cpfRegister, nameRegister, emailRegister)) {

                Patient patient = setPatientAttributes(cpfRegister, nameRegister,  phoneNumberRegister, emailRegister);

                Exception registryException = null;
                try {
                    patientService.register(patient);
                } catch (DuplicatedEntryException e) {
                    registryException = e;
                }

                utils.outputRegistrationResultToUser(registerOutput, registryException, "Paciente");
            }

        } catch (NullPointerException e) {
            utils.showMissingFieldAlert();
        }
    }



    private Patient setPatientAttributes(TextField cpf, TextField name, TextField phoneNumber,TextField emailRegister) {
         String code;
         code = cpf.getText().trim();


        Patient patient = new Patient(code.substring(0, 3));
        patient.setName(utils.toTitleCase(utils.formatName(name.getText())));
        patient.setCpf(cpf.getText().trim());
        patient.setPhoneNumber(phoneNumber.getText().trim());
        patient.setEmail(emailRegister.getText().trim());

        return patient;
    }

    // Find Patient

    @FXML
    private void findPatient() {
        Patient patient =  patientService.findCpf(cpfArea.getText().trim());

        if(patient != null) {
            urgencyRegisterPane.setVisible(true);
            namePatientUrgency.setText(patient.getName());
            loadCourseLoad(priority);

        }else{
            utils.showPacientNotFound("Paciente");
        }
    }

    // Register Urgency
@FXML
private void registerUrgency(){


    try {

        Patient patient =  patientService.findCpf(cpfArea.getText().trim());

        Urgency urgency = setUrgencyAttributes(patient, symptoms, priority, date, time );

        Exception exception = null;
        try {
            System.out.println("entrou aqui");
            System.out.println("u+ "+  urgency.getPatient().getName());
            urgencyService.register(urgency);
        } catch (DuplicatedEntryException e) {
            exception = e;
        }

        utils.outputRegistrationResultToUser(registerUrgencyOutput, exception, "Urgency");
    } catch (NullPointerException e) {
        utils.showMissingFieldAlert();
    }
}



    private Urgency setUrgencyAttributes(Patient patient, TextArea symptoms, ComboBox<String> priority, TextField date, TextField time) {
        Urgency urgency = new Urgency(patient.getCpf());
        urgency.setPatient(patient);
        urgency.setSymptoms(symptoms.getText().trim());
        urgency.setPriority(priority.getSelectionModel().getSelectedItem().trim());
        urgency.setDate(date.getText().trim());
        urgency.setTime(time.getText().trim());

        return urgency;
    }

    private void loadCourseLoad(ComboBox<String> priority) {
        ObservableList<String> loadList = FXCollections.observableArrayList("Normal", "Alta");
        priority.setItems(loadList);
    }

    private void populateTable() {
        // Associate columns to Patients attributes
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Fill table
        patientTable.setItems(FXCollections.observableArrayList(patientService.list()));
    }


    private void populateTableSolicitation() {
        // Associate columns to Exames attributes
        solicitationColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("patientCpf"));


        // Fill table
        solicitationTable.setItems(FXCollections.observableArrayList(examesService.list()));
    }

    // ======= HELPER METHODS =======
    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pass window root pane
        utils.setRoot(root);

        // Load and control background panes view
        List<Pane> panesList = new ArrayList<>(
                Arrays.asList( registerPane, solicitationPane,listAllPane, urgencyPane));
        utils.setPanesList(panesList);

        //showRegisterPane();

        // Load instance
        Backup b = new Backup();
        b.restore();
        patientService = PatientService.getInstance();
        examesService = ExamesService.getInstance();
        urgencyService = UrgencyService.getInstance();
    }

}
