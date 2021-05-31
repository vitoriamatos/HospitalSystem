package hospitalsystem.controller;

import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.entities.*;
import hospitalsystem.model.service.DoctorService;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.service.PatientService;
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

    // ======= PATIENTS FUNCTIONS =======
    @FXML
    private void scheduleExame() {
        Random random = new Random();
        int numero = random.nextInt(100);
        String code = String.valueOf(numero);
        Exames exame = new Exames(code);

        exame.setName("Exame de sangue");
        //exame.setPatient(doctorMain);

        try {
            examesService.register(exame);
        } catch (DuplicatedEntryException e) {
            e.printStackTrace();
        }
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

                            stage.setTitle("Simulation");
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

        stage.setTitle("Simulation");
        stage.setScene(new Scene(fxmlloader.load()));

        stage.show();
    }
    @FXML
    private void pescription(){
        prescripitionPane.setVisible(true);
        System.out.println("CLICOU AQUI");
    }
    @FXML
    private void cancelUrgency(){
        attendencePane.setVisible(false);

    }
    @FXML
    private void savePrescription(){
        prescripitionPane.setVisible(false);
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
    }
}
