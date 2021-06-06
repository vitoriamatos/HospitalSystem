package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorService implements HospitalTopics<Doctor>, Serializable {

    private static final long serialVersionUID = 8343466128152662851L;
    private final Map<String, Doctor> doctors = new HashMap<>();
    //private final List<Doctor> doctors = new ArrayList<>();


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
    public Doctor find(String email) {
        if(doctors.containsKey(email)){
            System.out.println("Doctor code: " + doctors.get(email).getPassword());
            return doctors.get(email);
        }
        return null;
    }

    @Override
    public List<Doctor> list() {
        List<Doctor> doctorsList = new ArrayList<>(doctors.values());
        return doctorsList;
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
        if (doctors.containsKey(doctor.getEmail())) {
        	throw new DuplicatedEntryException(doctor);
        } else {
        	doctors.put(doctor.getEmail(),doctor);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(String email) {
        doctors.remove(email);
    }

}
