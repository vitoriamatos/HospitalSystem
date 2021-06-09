package hospitalsystem.model.utils;

import hospitalsystem.HospitalSystem;
import hospitalsystem.controller.ExameController;
import hospitalsystem.model.entities.Exames;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.*;

public class ArquivoExames {

    private File arquivo;
    private FileChooser fileChooser;

    public ArquivoExames(){
        this.arquivo = new File("Exame");
        this.fileChooser = new FileChooser();
    }

    public void imprimirExame(Exames exame){
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivo TXT", "*.txt"));
        fileChooser.setInitialFileName("*.txt");
        fileChooser.setInitialDirectory(arquivo);
        arquivo = fileChooser.showSaveDialog(new Stage());

        try {
            if (!arquivo.exists()){
                arquivo.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(exame);
            System.out.println("exame salvo!");

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
