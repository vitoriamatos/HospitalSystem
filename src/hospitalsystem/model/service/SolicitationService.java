package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolicitationService implements HospitalTopics<Patient>, Serializable {

    private static final long serialVersionUID = -1990993300966043997L;

    private final List<Patient> patients = new ArrayList<>();

    private static SolicitationService singleton;



    protected SolicitationService(){}


    public static String getName(){
        return "PatientService";
    }

    public static SolicitationService getInstance() {
        if (singleton == null){
            singleton = new SolicitationService();
        }
        return singleton;
    }

    public static void setInstance(SolicitationService instance){
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
