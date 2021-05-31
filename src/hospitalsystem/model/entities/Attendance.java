package hospitalsystem.model.entities;

import javax.print.Doc;
import java.io.Serializable;
import java.util.List;

public class Attendance implements Serializable {


    private static final long serialVersionUID = 4276128555363091686L;

    private List<String> allocatedClasses;
    private String code;
    private Patient patient;
    private Urgency urgency;
    private Doctor doctor;
    private String report;
    private String prescription;


    public Attendance(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
}
