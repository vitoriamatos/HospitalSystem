package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.PatientService;
import hospitalsystem.model.utils.ArquivoExames;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private Pane editPane;
    @FXML
    private Pane passwordPane;
    @FXML
    private Pane positiveResult;
    @FXML
    private Pane negativeResult;
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
    // ====== EXAME SCHEDULE =======
    @FXML
    private ComboBox<String> exame;
    @FXML
    private ComboBox<String> time;
    @FXML
    private Text registerExameOutput;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButtom;
    @FXML
    private Button editButtom;
    // ====== EDIT PASSWORD =======
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirme;
    @FXML
    private Text outputMessage;
    //=======EXAME RESULT=============
    @FXML
    private TextField exameFind;
    @FXML
    private Text nameResult;
    @FXML
    private  Text typeResult;
    @FXML
    private TextArea exameResult;
    @FXML
    private TextField scheduleExameData;

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
    private void editData(){

        editPane.setVisible(true);

        nameRegister.editableProperty().setValue(true);
        cpfRegister.editableProperty().setValue(true);
        phoneNumberRegister.editableProperty().setValue(true);
        emailRegister.editableProperty().setValue(true);

    }

    @FXML
    private void cancelEdit(){

        setTextFieldsWithAttributes(nameRegister,cpfRegister,phoneNumberRegister,emailRegister);
        editPane.setVisible(false);
    }

    @FXML
    private void save(){
        patientMain = setPatientAttributes( cpfRegister, nameRegister, phoneNumberRegister, emailRegister);

        try {
            patientService.modify(patientMain);
            nameRegister.editableProperty().setValue(false);
            cpfRegister.editableProperty().setValue(false);
            phoneNumberRegister.editableProperty().setValue(false);
            emailRegister.editableProperty().setValue(false);
        } catch (MissingEntryException ignored) {
        }
        setTextFieldsWithAttributes(nameRegister,cpfRegister,phoneNumberRegister,emailRegister);
        editPane.setVisible(false);
    }

    private Patient setPatientAttributes(TextField cpf, TextField name, TextField phoneNumber,TextField emailRegister) {

        patientMain.setName(utils.toTitleCase(utils.formatName(name.getText())));
        patientMain.setCpf(cpf.getText().trim());
        patientMain.setPhoneNumber(phoneNumber.getText().trim());
        patientMain.setEmail(emailRegister.getText().trim());

        return patientMain;
    }

    private void setTextFieldsWithAttributes(TextField name, TextField cpf, TextField phoneNumber, TextField email) {

        nameRegister.setText(patientMain.getName());
        cpfRegister.setText(patientMain.getCpf());
        phoneNumberRegister.setText(patientMain.getPhoneNumber());
        emailRegister.setText(patientMain.getEmail());

    }

    @FXML
    private void saveNewPassword() throws MissingEntryException {
        System.out.println(password.getText());
        System.out.println(passwordConfirme.getText());

        if(password.getText().compareTo(passwordConfirme.getText())==0){
            patientMain.setPassword(password.getText());
            patientService.modifyPassword(patientMain);
            outputMessage.setText("Senha alterada com suesso");
        }else{
            outputMessage.setText("A nova senha e a confirmação de senha devem ser iguais");
        }

    }

    @FXML
    private void cancelPassword() {
        passwordPane.setVisible(false);

    }
    @FXML
    private void editPassword() {
        passwordPane.setVisible(true);

    }


    @FXML
    private void scheduleExame() {
        try {


            Exames exames = setExameAttributes(exame, time,scheduleExameData);
            Exception exception = null;
            try {
                examesService.register(exames);

            } catch (DuplicatedEntryException e) {
                exception = e;
            }
            utils.outputRegistrationResultToUser(registerExameOutput, exception, "Exame");
            utils.showCodeExame(exames.getCode());
            System.out.println("CODIGO EXAME "+ exames.getCode());
        } catch (NullPointerException e) {
            utils.showMissingFieldAlert();
        }

    }

    private Exames setExameAttributes(ComboBox<String> exame, ComboBox<String>time, TextField date){
        Random random = new Random();

        int numero = random.nextInt(100);
        String code = String.valueOf(numero);

        Exames exames = new Exames(code);

        exames.setName(exame.getSelectionModel().getSelectedItem().trim());
        exames.setTime(time.getSelectionModel().getSelectedItem().trim());
        exames.setDay(date.getText());
        exames.setPatient(patientMain);

        return exames;
    }

    private void loadTime(ComboBox<String> time) {
        ObservableList<String> loadList = FXCollections.observableArrayList("Manha", "Tarde");
        time.setItems(loadList);
    }

    private void loadExame(ComboBox<String> exame) {
        ObservableList<String> loadList = FXCollections.observableArrayList("Exame de Sangue", "Exame de Urina", "Raio X");
        exame.setItems(loadList);
    }
    @FXML
    private void dateFormat() {
        formatDate(scheduleExameData);
    }
    private void formatDate(TextField textField) {
        formatTextField(textField, "##/##/####", "0123456789");
    }

    private void formatTextField(TextField textField, String mask, String validCharacters) {
        academiccontrolsystem.utils.TextFieldFormatter textFieldFormatter = new academiccontrolsystem.utils.TextFieldFormatter();
        textFieldFormatter.setMask(mask);
        textFieldFormatter.setValidCharacters(validCharacters);
        textFieldFormatter.setTextField(textField);
        textFieldFormatter.formatter();
    }

    //======= EXAME RESULT ============

    @FXML
    private void findResultExame(){
        if(positiveResult.isVisible() || negativeResult.isVisible()){
            positiveResult.setVisible(false);
            negativeResult.setVisible(false);
        }

        Exames exames = examesService.find(exameFind.getText());

        if(exames != null){

            if(exames.getStatus() == 1) {
                positiveResult.setVisible(true);
                nameResult.setText(exames.getPatient().getName());
                typeResult.setText(exames.getName());
                exameResult.setText(exames.getResult());
            }else{

                negativeResult.setVisible(true);
            }

        }else{
            utils.showPacientNotFound("Exame");
        }
    }

    @FXML
    private  void imprimir(){

        Exames exames = examesService.find(exameFind.getText());
        ArquivoExames arquivoExames = new ArquivoExames();
        arquivoExames.imprimirExame(exames);
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
        loadTime(time);
        loadExame(exame);
        // Load instance
        Backup b = new Backup();
        b.restore();
        patientService = PatientService.getInstance();
        examesService = ExamesService.getInstance();
    }
}
