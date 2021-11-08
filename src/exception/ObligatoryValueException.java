package exception;

public class ObligatoryValueException extends Exception{
    private String field;

    public ObligatoryValueException(String field) {
        this.field = field;
    }

    @Override
    public String getMessage() {
        return String.format("Le champ %s est obligatoire",field);
    }
}
