package hospitalsystem.model.entities;

import java.io.Serializable;
import java.util.List;

public class Solicitations implements Serializable {

    private static final long serialVersionUID = -8583777517936355438L;


    private List<String> allocatedClasses;
    private String name;
    private String code;
    private int status;
    private Doctor doctor;
    private Patient patient;


    public Solicitations(String code) {
        this.code=code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
