package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.entities.Urgency;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UrgencyService implements HospitalTopics<Urgency>, Serializable {


    private static final long serialVersionUID = -6904124841677124035L;
    private final List<Urgency> urgencies = new ArrayList<>();

    private static UrgencyService singleton;



    protected UrgencyService(){}


    public static String getName(){
        return "UrgencyService";
    }

    public static UrgencyService getInstance() {
        if (singleton == null){
            singleton = new UrgencyService();
        }
        return singleton;
    }

    public static void setInstance(UrgencyService instance){
        singleton = instance;
    }



    @Override
    public Urgency find(String code) {
        for (Urgency urgency : urgencies) {
            if (code.equals(urgency.getPatient().getCpf())) {
                System.out.println("Doctor code:" + urgency.getPatient().getCpf());
                return urgency;
            }
        }
        return null;
    }

    @Override
    public List<Urgency> list() {
        return urgencies;
    }

    @Override
    public void modify(Urgency aux) throws MissingEntryException {
    	Urgency urgency = find(aux.getPatient().getCpf());
        if (urgency== null) {
            throw new MissingEntryException(aux);
        } else {
            urgency.setSymptoms(aux.getSymptoms());
            //urgency.setPriority(aux.getPriority());
            urgency.setPatient(aux.getPatient());
            urgency.setDate(aux.getDate());
            urgency.setTime(aux.getTime());

        }
    }

    @Override
    public void register(Urgency urgency) throws DuplicatedEntryException {
        System.out.println("Chegou no register");
        if (urgencies.contains(urgency)) {
        	throw new DuplicatedEntryException(urgency);
        } else {
        	urgencies.add(urgency);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(Urgency aux) {
        for (int i = 0; i < urgencies.size(); i++) {
            if (urgencies.get(i).getCode() == aux.getCode()) {
                urgencies.remove(i);
                System.out.println("Exame cancelado.");
                break;
            }
        }
    }

}
