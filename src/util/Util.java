package util;

import exception.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    private static final String DATE= "01-01-1900";
    private static final String ENDING_DATE = "01-01-" + (Calendar.getInstance().get(Calendar.YEAR)-12);

    private static final int HEIGHT_MIN = 100;
    private static final int HEIGHT_MAX = 250;
    private static final double WEIGHT_MAX = 170.0;
    private static final double WEIGHT_MIN = 30.0;

    private static final SimpleDateFormat FORMAT =new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);

    public static String getDATE() {
        return DATE;
    }

    public static String getEndingDate() {
        return ENDING_DATE;
    }

    public static SimpleDateFormat getFormat() {
        return FORMAT;
    }


    public static void validateLastname(String lastname)throws ObligatoryValueException,FormException{
        if(isBlankOrEmpty(lastname))
            throw new ObligatoryValueException("nom");
        if(!isString(lastname))
            throw new FormException("nom","uniquement des lettres, espaces et des tirets");
    }
    public static void validateFirstname(String firstname)throws FormException {
        if(firstname != null && !isBlankOrEmpty(firstname) && !isString(firstname))
            throw new FormException("prénom","uniquement des lettres, espaces et des tirets");
    }
    public static void validateMail(String mail)throws FormException {
        if(mail != null && !isBlankOrEmpty(mail) && !mail.matches("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"))
            throw new FormException("mail","à une adresse mail valide");
    }
    public static void validateHeight(int height) throws FormException {
        if(height > HEIGHT_MAX || height < HEIGHT_MIN)
            throw new FormException("La taille",String.valueOf(height),String.valueOf(HEIGHT_MIN),String.valueOf(HEIGHT_MAX));
    }
    public static void validateWeight(double weight) throws FormException {
        if(weight > WEIGHT_MAX || weight < WEIGHT_MIN)
            throw new FormException("Le poids",String.valueOf(weight),String.valueOf(WEIGHT_MIN),String.valueOf(WEIGHT_MAX));
    }
    public static void validatePseudo(String pseudo) throws ObligatoryValueException {
        if(isBlankOrEmpty(pseudo))
            throw new ObligatoryValueException("pseudo");
    }
    public static void validateNationality(String nationality) throws FormException {
        List<String> countryList = Arrays.stream(Locale.getISOCountries()).toList();
        if(!countryList.contains(nationality))
            throw new FormException("La nationalité","une nationalité valide");
    }
    public static void validateBirthdate(GregorianCalendar date) throws ParseException, FormException {
        GregorianCalendar startingDate = new GregorianCalendar();
        startingDate.setTime(FORMAT.parse(DATE));

        GregorianCalendar endingDate = new GregorianCalendar();
        endingDate.setTime(FORMAT.parse(ENDING_DATE));

        if (date.compareTo(endingDate) > 0 || date.compareTo(startingDate) < 0)
            throw new FormException("La date", "une date valide");

    }

    public static void validateGender(String gender) throws FormException {
        if(!gender.equals("m") && !gender.equals("f"))
            throw new FormException("Le sexe", "m ou f");
    }

    public static void validateDishId(int dish_id) throws FormException {
        if(dish_id < 1)
            throw new FormException("Le numéro du plat", "ne peut pas être plus petit que 1");
    }

    public static void validateCocahId(Integer coach_id) throws FormException {
        if(coach_id != null && coach_id < 1)
            throw new FormException("Le coach","un numéro de coach valide");
    }

    public static GregorianCalendar tranformDateToGregorianCalendar(Date date) {
        GregorianCalendar gregoiranValue = new GregorianCalendar();
        gregoiranValue.setTime(date);
        return gregoiranValue;
    }

    public static Boolean isNumber(String string){
        return string.matches("\\d+.?\\d+");
    }
    public static Boolean isString(String string){
        return string.matches("^[-'a-zA-ZÀ-ÖØ-öø-ÿ]+ ?[-'a-zA-ZÀ-ÖØ-öø-ÿ]+?$");
    }
    public static Boolean isBlankOrEmpty(String string){
        return string.isEmpty() || string.isBlank();
    }
}
