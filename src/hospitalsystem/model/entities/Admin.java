package hospitalsystem.model.entities;

import java.util.List;

public class Admin extends Person {

    private static final long serialVersionUID = -8583777517936355438L;


    private List<String> allocatedClasses;

    private Doctor doctor;
    private Patient patient;

    public Admin(String code) {
        super(code);
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
