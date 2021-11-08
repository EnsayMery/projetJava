package exception.crud;

public class AddException extends Exception{
    private String details;

    public AddException(String details) {
        this.details = details;
    }

    @Override
    public String getMessage() {
        return "Erreur lors de l'ajout " + details;
    }
}
