package hospitalsystem.controller;

import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.*;
import hospitalsystem.model.service.DoctorService;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.UrgencyService;
import hospitalsystem.model.utils.Backup;
import hospitalsystem.model.utils.Utils;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DoctorController implements Initializable {

    private final Utils<?> utils = new Utils<>();
    private DoctorService doctorService;
    public ExamesService examesService;
    public UrgencyService urgencyService;

    Doctor doctorMain;
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
    private Pane patientPane;
    @FXML
    private Pane attendencePane;
    @FXML
    private Pane prescripitionPane;
    @FXML
    private Pane editPane;
    @FXML
    private Pane passwordPane;
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
    @FXML
    private VBox patientTable;
    @FXML
    private Label priorityLabel;
    // ======== SOLICITATION =======
    @FXML
    private TableView<Urgency> solicitationTable;
    @FXML
    private TableColumn<Urgency, String> priorityColumn;
    @FXML
    private TableColumn<Urgency, String> nameColumn;
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
    private TextField priority;
    @FXML
    private TextArea report;
    @FXML
    private TextArea prescription;
    @FXML
    private Button saveAttendance;

    // ====== EDIT PASSWORD =======
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirme;
    @FXML
    private Text outputMessage;

    // ======== SOLICITATION =======

    @FXML
    private TableView<Exames> exameTable;

    @FXML
    private TableColumn<Exames, String> patientColumn;
    @FXML
    private TableColumn<Exames, String> examesColumn;


    // ======= CONSTRUCTOR ======
    public DoctorController(Doctor doctorMain) {
        this.doctorMain = doctorMain;
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
    private void patientView() {
        utils.showPane("patientPane");
    }

    // ======= DOCTOR FUNCTIONS =======

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
        doctorMain = setDoctorAttributes( cpfRegister, nameRegister, phoneNumberRegister, emailRegister);

        try {
            doctorService.modify(doctorMain);
            nameRegister.editableProperty().setValue(false);
            cpfRegister.editableProperty().setValue(false);
            phoneNumberRegister.editableProperty().setValue(false);
            emailRegister.editableProperty().setValue(false);
        } catch (MissingEntryException ignored) {
        }
        setTextFieldsWithAttributes(nameRegister,cpfRegister,phoneNumberRegister,emailRegister);
        editPane.setVisible(false);
    }

    private Doctor setDoctorAttributes(TextField cpf, TextField name, TextField phoneNumber,TextField emailRegister) {

        doctorMain.setName(utils.toTitleCase(utils.formatName(name.getText())));
        doctorMain.setCpf(cpf.getText().trim());
        doctorMain.setPhoneNumber(phoneNumber.getText().trim());
        doctorMain.setEmail(emailRegister.getText().trim());

        return doctorMain;
    }

    private void setTextFieldsWithAttributes(TextField name, TextField cpf, TextField phoneNumber, TextField email) {

        nameRegister.setText(doctorMain.getName());
        cpfRegister.setText(doctorMain.getCpf());
        phoneNumberRegister.setText(doctorMain.getPhoneNumber());
        emailRegister.setText(doctorMain.getEmail());

    }


    @FXML
    private void saveNewPassword() throws MissingEntryException {

        if(password.getText().compareTo(passwordConfirme.getText())==0){
            doctorMain.setPassword(password.getText());
            doctorService.modifyPassword(doctorMain);
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

    private void populatePatient() {
        // Associate columns to Exames attributes
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        // Fill table
        solicitationTable.setItems(FXCollections.observableArrayList(urgencyService.list()));
        addButtonToTable();
    }



    private void addButtonToTable() {
        TableColumn<Urgency, Void> colBtn = new TableColumn("Atendimento");

        Callback<TableColumn<Urgency, Void>, TableCell<Urgency, Void>> cellFactory = new Callback<TableColumn<Urgency, Void>, TableCell<Urgency, Void>>() {
            @Override
            public TableCell<Urgency, Void> call(final TableColumn<Urgency, Void> param) {
                final TableCell<Urgency, Void> cell = new TableCell<Urgency, Void>() {

                    private final Button btn = new Button("ATENDER");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Urgency data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getPatient().getName());

                            AttendenceController ac = new AttendenceController(data, doctorMain);
                            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/AttendenceView.fxml"));
                            fxmlloader.setController(ac);

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            stage.setTitle("Hospital H+");
                            try {
                                stage.setScene(new Scene(fxmlloader.load()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            stage.show();

                        });
                    }



                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        solicitationTable.getColumns().add(colBtn);
    }

    public void callStage(ActionEvent event, FXMLLoader fxmlloader) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle("Hospital H+");
        stage.setScene(new Scene(fxmlloader.load()));

        stage.show();
    }
    @FXML
    private void pescription(){
        prescripitionPane.setVisible(true);
    }
    @FXML
    private void cancelUrgency(){
        attendencePane.setVisible(false);

    }
    @FXML
    private void savePrescription(){
        prescripitionPane.setVisible(false);
    }




    // ======= EXAMES =================

    private void populateExames() {
        // Associate columns to Exames attributes
        examesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));

        // Fill table
        exameTable.setItems(FXCollections.observableArrayList(examesService.list()));
        addButtonToExameTable();
    }

    private void addButtonToExameTable() {
        TableColumn<Exames, Void> colBtn = new TableColumn("Atendimento");

        Callback<TableColumn<Exames, Void>, TableCell<Exames, Void>> cellFactory = new Callback<TableColumn<Exames, Void>, TableCell<Exames, Void>>() {
            @Override
            public TableCell<Exames, Void> call(final TableColumn<Exames, Void> param) {
                final TableCell<Exames, Void> cell = new TableCell<Exames, Void>() {

                    private final Button btn = new Button("ATENDER");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Exames data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data.getPatient().getName());

                            ExameController ac = new ExameController(data, doctorMain);
                            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/ExameView.fxml"));
                            fxmlloader.setController(ac);

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            stage.setTitle("Hospital H+");
                            try {
                                stage.setScene(new Scene(fxmlloader.load()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            stage.show();

                        });
                    }



                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

       exameTable.getColumns().add(colBtn);
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
        nameRegister.setText(doctorMain.getName());
        cpfRegister.setText(doctorMain.getCpf());
        phoneNumberRegister.setText(doctorMain.getPhoneNumber());
        emailRegister.setText(doctorMain.getEmail());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Pass window root pane
        utils.setRoot(root);
        // Load and control background panes view
        List<Pane> panesList = new ArrayList<>(
                Arrays.asList( homePane, regiterDataPane, examePane, patientPane));
        utils.setPanesList(panesList);
        showHomeView();
        // Load instance
        Backup b = new Backup();
        b.restore();
        doctorService = DoctorService.getInstance();
        examesService = ExamesService.getInstance();
        urgencyService = UrgencyService.getInstance();
        //For lists
        populatePatient();
        populateExames();
    }
}
