package model;

import exception.FormException;
import exception.ObligatoryValueException;
import util.Util;

public class Coach {
    private Integer coachId;
    private String firstname;
    private String lastname;

    public Coach(Integer coachId,String firstname,String lastname) throws FormException, ObligatoryValueException {
        setCoachId(coachId);
        setFirstname(firstname);
        setLastname(lastname);
    }

    public void setLastname(String lastname) throws ObligatoryValueException, FormException {
        Util.validateLastname(lastname);
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) throws ObligatoryValueException, FormException {
        if(firstname == null)
            throw new ObligatoryValueException("Le prénom");
        Util.validateFirstname(firstname);
        this.firstname = firstname;
    }

    public void setCoachId(Integer coachId) throws FormException {
        Util.validateCocahId(coachId);
        this.coachId = coachId;
    }

    public String getFirstname() {
        return firstname;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public String getLastname() {
        return lastname;
    }
}
