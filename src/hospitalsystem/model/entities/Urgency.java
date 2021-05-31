package hospitalsystem.model.entities;

import java.io.Serializable;
import java.util.List;

public class Urgency implements Serializable {


    private static final long serialVersionUID = 4276128555363091686L;

    private List<String> allocatedClasses;
    private Patient patient;
    private String code;
    private String symptoms;
    private String priority;
    private String date;
    private String time;
    private String patientName;
    private String patientCpf;



    public Urgency(String Code) {
        this.code= code;
    }

    public String getPatientName() {
        return patient.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPatientCpf() {
        return patient.getCpf();
    }
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
