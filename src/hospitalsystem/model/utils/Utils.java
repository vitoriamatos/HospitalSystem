package hospitalsystem.model.utils;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.model.interfaces.HospitalTopics;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Utils<T> {
    private Pane root;

    private List<Pane> panesList;


    // ======= WINDOW FUNCTIONS =======

    public void back(Pane root, ActionEvent event) throws IOException {
        Backup b = new Backup();
        b.backup(b);

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../../view/fxmls/InicialView.fxml"));

        Stage stage = getStageFromEvent(event);

//		moveWindow(root, stage);
        stage.setScene(new Scene(fxmlloader.load()));
        stage.show();
    }

    public void close() throws IOException {
        Backup b = new Backup();
        b.backup(b);
        System.exit(0);
    }

    public String formatName(String name) {
        // Trim string and make name title case
        name = name.trim();
        name = toTitleCase(name);

        // Make prepositions lower case
        name = name
                .replace(" E ", " e ")
                .replace(" Da ", " da ")
                .replace(" De ", " de ")
                .replace(" Di ", " di ")
                .replace(" Do ", " do ")
                .replace(" Du ", " du ");

        // Remove extra whitespace
        StringBuilder nameBuilder = new StringBuilder();
        char[] charArray = name.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!(charArray[i] == ' ' && charArray[i - 1] == ' ')) {
                nameBuilder.append(charArray[i]);
            }
        }

        return nameBuilder.toString();
    }


    // ======= MENU FUNCTIONS =======

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    // ======= ACADEMIC TOPICS =======

    public void notFoundMessage(Pane pane, Text notFoundField, String entity) {
        pane.setVisible(false);

        notFoundField.setText(toTitleCase(entity) + " nao encontrado");
        notFoundField.setFill(Color.DARKRED);
    }

    public void scheduleClashMessage(Text scheduleField, String entity) {
        scheduleField.setText("Conflito de horario para este " + entity + ".");
        scheduleField.setFill(Color.DARKRED);
    }

    public void outputRegistrationResultToUser(Text outputText, Exception exception, String entity) {
        if (exception instanceof DuplicatedEntryException) {
        	outputText.setText(toTitleCase(entity) + " ja cadastrado(a)");
            outputText.setFill(Color.DARKRED);
        } else {
        	outputText.setText(toTitleCase(entity) + " cadastrado(a) com sucesso!");
            outputText.setFill(Color.DARKGREEN);
        }
    }

    public T search(HospitalTopics<T> service, TextField searchField) {
        // Get id
        String id = searchField.getText().trim();
        System.out.println("id: " + id);
        System.out.println("service: " + service);
        // Search by id
        return service.find(id);
    }

    public void setPanesList(List<Pane> panesList){
        this.panesList = panesList;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void showDeletedRegisterAlertToUser(String entity) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cadastro deletado");
        alert.setHeaderText("");
        alert.setContentText(toTitleCase(entity) + " deletado(a)");
        alert.initOwner(root.getScene().getWindow());
        alert.show();
    }
    public void showPacientNotFound(String entity) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Usuario nao encontrado");
        alert.setHeaderText("");
        alert.setContentText(toTitleCase(entity) + " nao encontrado, favor solicitar cadastro.");
        //alert.initOwner(root.getScene().getWindow());
        alert.show();
    }

    public void showInvalidInputsAlertToUser(String invalidValues) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Informacoes invalidas");
        alert.setHeaderText("");
        alert.setContentText(invalidValues);
        alert.initOwner(root.getScene().getWindow());
        alert.show();
    }

    public void showMissingFieldAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("CAMPOS EM BRANCO!");
        alert.setHeaderText("");
        alert.setContentText("Por favor, preencha todos os campos.");
       // alert.initOwner(root.getScene().getWindow());
        alert.show();
    }

    public void showErrorWhileReadingSaveFileAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro ao recuperar os dados!");
        alert.setHeaderText("");
        alert.setContentText("Um erro ocorreu ao tentar recuperar os dados deste módulo.");
        alert.initOwner(root.getScene().getWindow());
        alert.show();
    }

    // Random
    public void showModifydRegisterAlertToUser(String entity) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cadastro alterado");
        alert.setHeaderText("");
        alert.setContentText(toTitleCase(entity) + " alterado(a)");
        alert.initOwner(root.getScene().getWindow());
        alert.show();
    }


    public void showCodeExame(String code) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("CÓDIGO DO EXAME");
        alert.setHeaderText("");
        alert.setContentText("O Codigo do seu exame e: " + code +" . Use-o para consultar o resultado.");
       // alert.initOwner(root.getScene().getWindow());
        alert.show();
    }


    // ======= HELPER METHODS =======
    public void showPane(String id) {
        panesList.forEach(pane -> pane.setVisible(id.equals(pane.getId())));
    }

    public void toggleRadioButton(ToggleGroup toggleGroup, String option) {
        ObservableList<Toggle> toggles = toggleGroup.getToggles();
        for (Toggle toggle : toggles) {
            if (((RadioButton) toggle).getText().equals(option)) {
                toggleGroup.selectToggle(toggle);
                break;
            }
        }
    }

    public String toTitleCase(String str) {
        String[] words = str.toLowerCase().split(" ");
        char first_letter;

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {

            if (word.isEmpty()) {
                stringBuilder.append(" ");
            } else {
                // Make the first letter upper case
                first_letter = Character.toUpperCase(word.charAt(0));
                word = first_letter + word.substring(1);

                stringBuilder.append(word).append(" ");
            }
        }

        return stringBuilder.toString().trim();
    }

}
