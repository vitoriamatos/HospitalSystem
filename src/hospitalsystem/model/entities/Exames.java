package hospitalsystem.model.entities;

import java.io.Serializable;
import java.util.List;

public class Exames implements Serializable {


    private static final long serialVersionUID = 4276128555363091686L;


    private String name;
    private String time;
    private String code;
    private int status;
    private Doctor doctor;
    private Patient patient;
    private String patientName;
    private String patientCpf;
    private String result;



    public Exames(String code) {
        this.code=code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getPatientName() {
        return patient.getName();
    }

    public String getPatientCpf() {
        return patient.getCpf();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
