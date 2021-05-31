package hospitalsystem.controller;

import hospitalsystem.model.entities.Admin;
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

public class LoginAdminController implements Initializable {

    private final Utils<Admin> utils = new Utils<>();
    private final String email = "admin@emal.com";
    private final String senha = "admin";
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
        if(!email.equals(loginArea.getText()) && !senha.equals(passwordArea.getText())){
            utils.showPacientNotFound("Adm");
        }else {
            showModule(event, "AdminView");
        }
    }

    private void showModule(ActionEvent event, String fxmlName) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/fxmls/" + fxmlName + ".fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Backup b = new Backup();
        b.restore();
    }
}
