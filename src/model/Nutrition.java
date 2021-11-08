package model;

import exception.ObligatoryValueException;
import util.Util;

public class Nutrition {
    private String personId;
    private String label;

    public Nutrition(String personId, String label) throws ObligatoryValueException {
        setPersonId(personId);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) throws ObligatoryValueException {
        Util.validatePseudo(personId);
        this.personId = personId;
    }
}
