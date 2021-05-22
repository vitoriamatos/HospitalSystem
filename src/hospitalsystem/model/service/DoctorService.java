package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DoctorService implements HospitalTopics<Doctor>, Serializable {

    private static final long serialVersionUID = -1990993300966043997L;

    private final List<Doctor> doctors = new ArrayList<>();

    private static DoctorService singleton;



    protected DoctorService(){}


    public static String getName(){
        return "DoctorService";
    }

    public static DoctorService getInstance() {
        if (singleton == null){
            singleton = new DoctorService();
        }
        return singleton;
    }

    public static void setInstance(DoctorService instance){
        singleton = instance;
    }



    @Override
    public Doctor find(String code) {
        for (Doctor doctor : doctors) {
            if (code.equals(doctor.getCode())) {
                System.out.println("Doctor code:" + doctor.getCode());
                return doctor;
            }
        }
        return null;
    }

    @Override
    public List<Doctor> list() {
        return doctors;
    }

    @Override
    public void modify(Doctor aux) throws MissingEntryException {
    	Doctor doctor = find(aux.getCpf());
        if (doctor== null) {
            throw new MissingEntryException(aux);
        } else {
            doctor.setName(aux.getName());
            doctor.setPhoneNumber(aux.getPhoneNumber());

        }
    }

    @Override
    public void register(Doctor doctor) throws DuplicatedEntryException {
        if (doctors.contains(doctor)) {
        	throw new DuplicatedEntryException(doctor);
        } else {
        	doctors.add(doctor);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(String cpf) {
        doctors.remove(new Doctor(cpf));
    }

}
