package exception.crud;

public class DeleteException extends Exception{
    private String details;

    public DeleteException(String details) {
        this.details = details;
    }

    @Override
    public String getMessage() {
        return "Erreur lors de la suppression " + details;
    }
}
