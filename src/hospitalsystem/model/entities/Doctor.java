package hospitalsystem.model.entities;
import java.util.List;

public class Doctor extends Person  {

    private static final long serialVersionUID = -8583777517936355438L;

    private List<String> allocatedClasses;
    private Patient patients;


    public Doctor(String code) {
        super(code);
    }

    public Patient getPatients() {
        return patients;
    }

    public void setPatients(Patient patients) {
        this.patients = patients;
    }

    public List<String> getAllocatedClasses() {
        return allocatedClasses;
    }

    public void setAllocatedClasses(List<String> allocatedClasses) {
        this.allocatedClasses = allocatedClasses;
    }
}
