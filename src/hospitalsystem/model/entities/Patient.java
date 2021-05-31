package hospitalsystem.model.entities;

public class Patient extends Person {

    private static final long serialVersionUID = -8583777517936355438L;
    private Agenda agenda;


    public Patient(String code) {
        super(code);
        agenda = new Agenda();
    }
    
}
