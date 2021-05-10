package hospitalsystem.model;

import java.io.Serializable;

public abstract class Person implements Serializable {

    private static final long serialVersionUID = -2522224918893531132L;

    private String code;
    private String name;
    private String cpf;
    private String phoneNumber;
    private String email;


    public Person(String code){
        this.code = code;
    }




    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
