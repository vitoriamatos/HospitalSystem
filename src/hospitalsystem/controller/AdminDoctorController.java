package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.service.DoctorService;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import hospitalsystem.model.utils.Validation;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDoctorController  implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private DoctorService doctorService;
    private ExamesService examesService;

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
    private TableView<Doctor> doctorTable;
    @FXML
    private TableColumn<Doctor, String> emailColumn;
    @FXML
    private TableColumn<Doctor, String> nameColumn;

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
        showModule(event, "AdminView");
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
    private void showHomePane() {
        utils.showPane("homePane");

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

                Doctor doctor = setDoctorAttributes(cpfRegister, nameRegister,  phoneNumberRegister, emailRegister);

                Exception registryException = null;
                try {
                    doctorService.register(doctor);
                } catch (DuplicatedEntryException e) {
                    registryException = e;
                }

                utils.outputRegistrationResultToUser(registerOutput, registryException, "Doctor");
            }

        } catch (NullPointerException e) {
            utils.showMissingFieldAlert();
        }
    }



    private Doctor setDoctorAttributes(TextField cpf, TextField name, TextField phoneNumber, TextField emailRegister) {
        String code;
        code = cpf.getText().trim();


        Doctor doctor = new Doctor(code.substring(0, 3));
        doctor.setName(utils.toTitleCase(utils.formatName(name.getText())));
        doctor.setCpf(cpf.getText().trim());
        doctor.setPhoneNumber(phoneNumber.getText().trim());
        doctor.setEmail(emailRegister.getText().trim());

        return doctor;
    }


    private void populateTable() {
        // Associate columns to Patients attributes
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Fill table
        doctorTable.setItems(FXCollections.observableArrayList(doctorService.list()));
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
                Arrays.asList( homePane,registerPane, listAllPane));
        utils.setPanesList(panesList);
        //showRegisterPane();
        showHomePane();
        // Load instance
        Backup b = new Backup();
        b.restore();
        doctorService = DoctorService.getInstance();
        examesService = ExamesService.getInstance();
    }

}
