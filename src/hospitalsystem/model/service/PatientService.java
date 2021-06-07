package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.interfaces.HospitalTopics;
import hospitalsystem.model.entities.Patient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientService implements HospitalTopics<Patient>, Serializable {

    private static final long serialVersionUID = -1990993300966043997L;

    private final Map<String, Patient> patients = new HashMap<>();
    //private final List<Patient> patients = new ArrayList<>();

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
    public Patient find(String email) {
        if(patients.containsKey(email)){
            System.out.println("paciente code: " + patients.get(email).getPassword());
            return patients.get(email);
        }
        return null;
    }


    public Patient findCpf(String code) {
        List<Patient> patientList = new ArrayList<>(patients.values());
        for (Patient patient : patientList) {
            if (code.equals(patient.getCpf())) {
                String email = patient.getEmail();
                System.out.println("paciente code:" + patient.getCpf());
                return patients.get(email);
            }
        }
        return null;
    }



    @Override
    public List<Patient> list() {
        List<Patient> patientsList = new ArrayList<>(patients.values());
        return patientsList;
    }

    @Override
    public void modify(Patient aux) throws MissingEntryException {
    	Patient patient = find(aux.getEmail());
        if (patient == null) {
            throw new MissingEntryException(aux);
        } else {
            patient.setName(aux.getName());
            patient.setPhoneNumber(aux.getPhoneNumber());

        }
    }

    public void modifyPassword(Patient aux) throws MissingEntryException{
        if(find(aux.getEmail()) == null){
            throw new MissingEntryException(aux);
        } else {
            patients.get(aux.getEmail()).setPassword(aux.getPassword());
        }
    }

    @Override
    public void register(Patient patient) throws DuplicatedEntryException {

        if (patients.containsKey(patient.getEmail())) {

        	throw new DuplicatedEntryException(patient);
        } else {
            patients.put(patient.getEmail(), patient);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(String email) {
        patients.remove(email);
    }

}
