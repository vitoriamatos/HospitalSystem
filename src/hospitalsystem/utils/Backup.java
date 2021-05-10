package hospitalsystem.utils;



import hospitalsystem.interfaces.HospitalTopics;
import hospitalsystem.service.PatientService;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backup {

    public static final String BACKUP_DIRECTORY = "HS";
    public static final String BACKUP_FILE = "Hospital System Backup.bkp";


    public static void backup() throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(getBackupFile()));
        writer.writeObject(getData());
        writer.close();
    }

    private static File getAcademicDirectory() {
        File documentsDirectory = getUserDocumentsDirectory();
        File[] subdirectories = documentsDirectory.listFiles();
        File academicDirectory = null;

        // Get academic directory if exists
        if (subdirectories != null) {
            for (File directory : subdirectories) {
                if (directory.isDirectory() && directory.getName().equals(BACKUP_DIRECTORY)) {
                    academicDirectory = directory;
                    break;
                }
            }
        }

        // Create academic DIRECTORY if it doesn't exists
        if (academicDirectory == null) {
            academicDirectory = new File(documentsDirectory, BACKUP_DIRECTORY);
            academicDirectory.mkdir();
        }

        return academicDirectory;
    }

    private static File getBackupFile() throws IOException {
        File academicDirectory = getAcademicDirectory();
        File[] subdirectories = academicDirectory.listFiles();
        File backupFile = null;

        // Get backup file if exists
        if (subdirectories != null) {
            for (File file : subdirectories) {
                if (file.isFile() && file.getName().equals(BACKUP_FILE)) {
                    backupFile = file;
                    break;
                }
            }
        }

        // Create backup file if it doesn't exists
        if (backupFile == null) {
            backupFile = new File(academicDirectory, BACKUP_FILE);
            backupFile.createNewFile();
        }

        return backupFile;

    }

    private static List<?> getData() {

        PatientService patientService = PatientService.getInstance();

        return new ArrayList<>(Arrays.asList(patientService));


      //   return null;
    }


    // ======= HELPER METHODS =======

    private static File getUserDocumentsDirectory() {
        return new JFileChooser().getFileSystemView().getDefaultDirectory();
    }


    public static void restore() {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(getBackupFile()))) {

            // Read save file
            List<?> database = (List<?>) reader.readObject();

            // Restore information
            for (Object service : database) {
                if (service instanceof HospitalTopics<?>) {
                    if (service instanceof PatientService) {
                        PatientService.setInstance((PatientService) service);
                    }
                }
            }

        } catch (EOFException ignored) {

        } catch (ClassNotFoundException e) {
            new Utils<>().showErrorWhileReadingSaveFileAlert();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
