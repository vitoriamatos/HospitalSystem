package hospitalsystem.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.interfaces.HospitalTopics;
import hospitalsystem.model.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatientService implements HospitalTopics<Patient>, Serializable {

    private static final long serialVersionUID = -1990993300966043997L;

    private final List<Patient> patients = new ArrayList<>();

    private static PatientService singleton;



    protected PatientService(){}


    public static String getName(){
        return "PatientService";
    }

    public static PatientService getInstance() {
        if (singleton == null){
            singleton = new PatientService();
        }
        return singleton;
    }

    public static void setInstance(PatientService instance){
        singleton = instance;
    }



    @Override
    public Patient find(String code) {
        for (Patient patient : patients) {
            if (code.equals(patient.getCode())) {
                System.out.println("paciente code:" + patient.getCode());
                return patient;
            }
        }
        return null;
    }

    @Override
    public List<Patient> list() {
        return patients;
    }

    @Override
    public void modify(Patient aux) throws MissingEntryException {
    	Patient patient = find(aux.getCpf());
        if (patient == null) {
            throw new MissingEntryException(aux);
        } else {
            patient.setName(aux.getName());
            patient.setPhoneNumber(aux.getPhoneNumber());

        }
    }

    @Override
    public void register(Patient patient) throws DuplicatedEntryException {
        if (patients.contains(patient)) {
        	throw new DuplicatedEntryException(patient);
        } else {
        	patients.add(patient);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(String cpf) {
        patients.remove(new Patient(cpf));
    }

}
