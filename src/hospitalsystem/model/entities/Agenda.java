package hospitalsystem.model.entities;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Agenda implements Serializable {
    
    private List<Exames> exams;
            
    public Agenda() {
        exams = new ArrayList<>();
    }
    
    public void bookExam(String name, Doctor doctor) {
       Exames exam = null;
       exam.setName(name);
       exam.setDoctor(doctor);  
       exams.add(exam);
       System.out.println("Exame marcado.");
    }
    
    public boolean cancelExam(String name, Doctor doctor) {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getName().equals(name) && exams.get(i).getDoctor().equals(doctor)) {
                exams.remove(exams.get(i));
                System.out.println("Exame cancelado.");
                return true;
            }
        }
        return false;
    }
    
    public Exames checkExam(String name, Doctor doctor) {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).getName().equals(name) && exams.get(i).getDoctor().equals(doctor)) {
                return exams.get(i);
            }
        }
        return null;
    }
    
    public List<Exames> listExams() {
        return exams;
    }

}
