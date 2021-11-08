package exception;

public class FormException extends Exception {
    private String field;
    private String value;
    private String valueMin;
    private String valueMax;
    private String details;

    private FormException(String field, String value, String valueMin, String valueMax, String details) {
        this.field = field;
        this.value = value;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.details = details;
    }
    public FormException(String field, String details) {
        this(field,null,null,null,details);
    }

    public FormException(String field, String value, String valueMin, String valueMax) {
        this(field,value,valueMin,valueMax,null);
    }


        @Override
    public String getMessage() {
        if(valueMin != null && valueMax != null)
            return String.format("%s (%s) doit être entre %s et %s",field,value,valueMin,valueMax);
        return String.format("%s doit contenir %s", field,details);
    }
}
