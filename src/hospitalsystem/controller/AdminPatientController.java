package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.Patient;
import hospitalsystem.service.PatientService;
import hospitalsystem.utils.Backup;
import hospitalsystem.utils.Utils;
import hospitalsystem.utils.Validation;
import javafx.collections.FXCollections;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPatientController  implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private PatientService patientService;

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


    // ======== LIST =======

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> emailColumn;
    @FXML
    private TableColumn<Patient, String> nameColumn;

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


    // ======= HOSPITAL TOPICS =======

    // Register

    private void formatTextField(TextField textField, String mask, String validCharacters) {
        academiccontrolsystem.utils.TextFieldFormatter textFieldFormatter = new academiccontrolsystem.utils.TextFieldFormatter();
        textFieldFormatter.setMask(mask);
        textFieldFormatter.setValidCharacters(validCharacters);
        textFieldFormatter.setTextField(textField);
        textFieldFormatter.formatter();
    }

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
        Patient patient = new Patient(cpf.getText().trim());
        patient.setName(utils.toTitleCase(utils.formatName(name.getText())));
        patient.setCpf(cpf.getText().trim());
        patient.setPhoneNumber(phoneNumber.getText().trim());
        patient.setEmail(emailRegister.getText().trim());

        return patient;
    }


    private void populateTable() {
        // Associate columns to Student attributes

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Fill table
        patientTable.setItems(FXCollections.observableArrayList(patientService.list()));
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
                Arrays.asList( registerPane, solicitationPane,listAllPane));
        utils.setPanesList(panesList);
        //showRegisterPane();

        // Load instance
        Backup.restore();
        patientService = PatientService.getInstance();
    }

}
