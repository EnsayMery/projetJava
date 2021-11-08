package model;

import exception.FormException;
import exception.ObligatoryValueException;
import util.Util;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class Person {
    private String pseudo;
    private String lastName;
    private String firstName;
    private String nationality;
    private GregorianCalendar birthday;
    private String gender;
    private String email;
    private Integer height;
    private Double weight;
    private Integer isHighLevelAthlete;
    private String coachName;
    private Integer coach_id;

    public Person(String pseudo,String lastName, String firstName, String nationality,
                  GregorianCalendar birthday, String gender, String email, Integer height, Double weight,
                  Integer isHighLevelAthlete,String coachName, Integer coach_id) throws ObligatoryValueException, FormException, ParseException {
        setPseudo(pseudo);
        setLastName(lastName);
        setFirstName(firstName);
        setNationality(nationality);
        setBirthday(birthday);
        setGender(gender);
        setEmail(email);
        setHeight(height);
        setWeight(weight);
        setIsHighLevelAthlete(isHighLevelAthlete);
        setCoach_id(coach_id);
        setCoachName(coachName);
    }

    public Person(String pseudo, String lastName, String nationality,
                  GregorianCalendar birthday, String gender, Integer height, Double weight,
                  Integer isHighLevelAthlete) throws ObligatoryValueException, ParseException, FormException {
        this(pseudo,lastName,null,nationality,birthday,gender,
                null,height,weight,isHighLevelAthlete,null,null);
    }

    public String getPseudo(){
        return pseudo;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNationality() {
        return nationality;
    }

    public GregorianCalendar getBirthday() {
        return birthday;
    }

    public Integer getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public Integer getHighLevelAthlete() {
        return isHighLevelAthlete;
    }

    public String getIsHighLevelAthlete(){
        if(isHighLevelAthlete == 1)
            return "V";
        return "X";
    }

    public String getCoachName() {
        return coachName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCoach_id() {
        return coach_id;
    }

    public void setPseudo(String pseudo) throws ObligatoryValueException {
        Util.validatePseudo(pseudo);
        this.pseudo = pseudo;
    }

    public void setLastName(String lastName) throws ObligatoryValueException, FormException {
        Util.validateLastname(lastName);
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) throws FormException {
        Util.validateFirstname(firstName);
        this.firstName = firstName;
    }

    public void setEmail(String email) throws FormException {
        Util.validateMail(email);
        this.email = email;
    }

    public void setBirthday(GregorianCalendar birthday) throws ParseException, FormException {
        Util.validateBirthdate(birthday);
        this.birthday = birthday;
    }

    public void setNationality(String nationality) throws FormException {
        Util.validateNationality(nationality);
        this.nationality = nationality;
    }

    public void setGender(String gender) throws FormException {
        Util.validateGender(gender);
        this.gender = gender;
    }

    public void setHeight(Integer height) throws FormException {
        Util.validateHeight(height);
        this.height = height;
    }

    public void setIsHighLevelAthlete(Integer isHighLevelAthlete) {
        if(isHighLevelAthlete < 0 || isHighLevelAthlete > 1)
            this.isHighLevelAthlete = 0;
        else
            this.isHighLevelAthlete = isHighLevelAthlete;
    }

    public void setWeight(Double weight) throws FormException {
        Util.validateWeight(weight);
        this.weight = weight;
    }

    public void setCoach_id(Integer coach_id) throws FormException {
        Util.validateCocahId(coach_id);
        this.coach_id = coach_id;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }
}
