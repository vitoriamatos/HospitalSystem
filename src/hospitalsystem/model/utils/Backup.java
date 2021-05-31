package hospitalsystem.model.utils;

import hospitalsystem.model.service.*;

import java.io.*;

public class Backup implements Serializable{
    private File archive;
    private PatientService patientService;
    private ExamesService examesService;
    private UrgencyService urgencyService;
    private SolicitationService solicitationService;
    private DoctorService doctorService;
    private AttendanceService attendanceService;

    public Backup(){
        archive = new File("arquivo.obj");
        patientService = PatientService.getInstance();
        examesService = ExamesService.getInstance();
        urgencyService = UrgencyService.getInstance();
        solicitationService = SolicitationService.getInstance();
        doctorService = DoctorService.getInstance();
        attendanceService = AttendanceService.getInstance();
    }

    public void backup(Backup b) {
        //salvar todos os service
        //Backup b = new Backup();
        try{
            if (!archive.exists()){
                archive.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(archive);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(b);
            oos.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restore() {
        //recuperar os service
        try{
            if (!archive.exists()){
                archive.createNewFile();
            }
            FileInputStream fin = new FileInputStream(archive);
            ObjectInputStream ois = new ObjectInputStream(fin);
            Backup b = (Backup) ois.readObject();
            ois.close();
            fin.close();
            DoctorService.setInstance(b.doctorService);
            PatientService.setInstance(b.patientService);
            ExamesService.setInstance(b.examesService);
            UrgencyService.setInstance(b.urgencyService);
            SolicitationService.setInstance(b.solicitationService);
            AttendanceService.setInstance(b.attendanceService);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
