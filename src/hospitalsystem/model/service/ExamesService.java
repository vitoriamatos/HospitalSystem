package hospitalsystem.model.service;


import hospitalsystem.exceptions.DuplicatedEntryException;
import hospitalsystem.exceptions.MissingEntryException;
import hospitalsystem.model.entities.Exames;
import hospitalsystem.model.entities.Patient;
import hospitalsystem.model.interfaces.HospitalTopics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ExamesService implements HospitalTopics<Exames>, Serializable {


    private static final long serialVersionUID = -6689278344379965829L;
    private final List<Exames> exames = new ArrayList<>();

    private static ExamesService singleton;



    protected ExamesService(){}


    public static String getName(){
        return "ExamesService";
    }

    public static ExamesService getInstance() {
        if (singleton == null){
            singleton = new ExamesService();
        }
        return singleton;
    }

    public static void setInstance(ExamesService instance){
        singleton = instance;
    }



    @Override
    public Exames find(String code) {
        for (Exames exame : exames) {
            if (code.equals(exame.getCode())) {
                System.out.println("paciente code:" + exame.getCode());
                return exame;
            }
        }
        return null;
    }

    @Override
    public List<Exames> list() {
        return exames;
    }

    @Override
    public void modify(Exames aux) throws MissingEntryException {
    	Exames exame = find(aux.getCode());
        if (exame== null) {
            throw new MissingEntryException(aux);
        } else {
            exame.setResult(aux.getResult());
            exame.setStatus(aux.getStatus());
            System.out.println("Status no service: "+ aux.getStatus());

        }
    }


    public void modifyExame(Exames aux) throws MissingEntryException{
        for(int i=0; i < exames.size(); i++){
            if(exames.get(i).getCode() == aux.getCode()){
                exames.get(i).setResult(aux.getResult());
                exames.get(i).setStatus(aux.getStatus());
                System.out.println("Status no service: "+ aux.getStatus());
            }

        }

    }
    public void changeStatus(Exames aux) throws MissingEntryException{
        if(find(aux.getCode()) == null){
            throw new MissingEntryException(aux);
        } else {

        }
    }

    @Override
    public void register(Exames exame) throws DuplicatedEntryException {
        if (exames.contains(exame)) {
        	throw new DuplicatedEntryException(exame);
        } else {
        	exames.add(exame);

        	System.out.println("Cadastrou");
        }
    }

    @Override
    public void remove(Exames aux) {
        for (int i = 0; i < exames.size(); i++) {
            if (exames.get(i).getCode().equals(aux.getCode())) {
                exames.remove(i);
                System.out.println("Exame cancelado.");
               break;
            }
        }
    }
}
