package hospitalsystem.model.entities;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import hospitalsystem.model.service.ExamesService;
import hospitalsystem.model.enums.Schedule;

public class Agenda implements Serializable {
    
    private List<Exames> exames;
            
    public Agenda() {
        List<Exames> exames = new ArrayList<>();
    }
    
    public void bookExam(String name, Doctor doctor) {
       Exames exam = null;
       exam.setName(name);
       exam.setDoctor(doctor);  
       exames.add(exam);
       
    }
    
    public boolean cancelExam(String name, Doctor doctor) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).getName().equals(name) && exames.get(i).getDoctor().equals(doctor)) {
                exames.remove(exames.get(i));
                return true;
            }
        }
        return false;
    }
    
    public Exames checkExam(String name, Doctor doctor) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).getName().equals(name) && exames.get(i).getDoctor().equals(doctor)) {
                return exames.get(i);
            }
        }
        return null;
    }
    
    public List<Exames> seeAllExams() {
        return exames;
    }
}
