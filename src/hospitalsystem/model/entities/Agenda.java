package hospitalsystem.model.entities;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Agenda implements Serializable {
    
    private List<Exames> exams;
            
    public Agenda() {
        exams = new ArrayList<>();
    }
    
    public void bookExam(Exames exam) {
       exams.add(exam);
       System.out.println("Exame marcado.");
    }
    
    public boolean cancelExam(Exames exam) {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).equals(exam)) {
                exams.remove(exams.get(i));
                System.out.println("Exame cancelado.");
                return true;
            }
        }
        return false;
    }
    
    public Exames checkExam(Exames exam) {
        for (int i = 0; i < exams.size(); i++) {
            if (exams.get(i).equals(exam)) {
                return exams.get(i);
            }
        }
        return null;
    }
    
    public List<Exames> listExams() {
        return exams;
    }

}
