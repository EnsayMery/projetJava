package exception.crud;

public class UpdateException extends Exception{
    private String details;

    public UpdateException(String details) {
        this.details = details;
    }

    @Override
    public String getMessage() {
        return "Erreur lors de la modification " + details;
    }
}
