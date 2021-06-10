package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Attendance;
import hospitalsystem.model.entities.Doctor;
import hospitalsystem.model.entities.Urgency;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService implements HospitalTopics<Attendance>, Serializable {


    private static final long serialVersionUID = -6904124841677124035L;
    private final List<Attendance> attendancies = new ArrayList<>();

    private static AttendanceService singleton;



    protected AttendanceService(){}


    public static String getName(){
        return "UrgencyService";
    }

    public static AttendanceService getInstance() {
        if (singleton == null){
            singleton = new AttendanceService();
        }
        return singleton;
    }

    public static void setInstance(AttendanceService instance){
        singleton = instance;
    }

    @Override
    public Attendance find(String code) {
        for (Attendance attendance : attendancies) {
            if (code.equals(attendance.getCode())) {
                System.out.println("Atendimento code:" + attendance.getCode());
                return attendance;
            }
        }
        return null;
    }

    @Override
    public List<Attendance> list() {
        return attendancies;
    }

    @Override
    public void modify(Attendance aux) throws MissingEntryException {
    	Attendance attendance = find(aux.getPatient().getCpf());
        if (attendance== null) {
            throw new MissingEntryException(aux);
        } else {
           attendance.setReport(aux.getReport());
           attendance.setPrescription(aux.getReport());

        }
    }

    @Override
    public void register(Attendance attendance) throws DuplicatedEntryException {
        System.out.println("Chegou no register");
        if (attendancies.contains(attendance)) {
        	throw new DuplicatedEntryException(attendance);
        } else {
        	attendancies.add(attendance);
        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(Attendance attendance) {

        //attendancies.remove(new Attendance(code));
    }

}
