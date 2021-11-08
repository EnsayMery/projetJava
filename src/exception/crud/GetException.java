package exception.crud;

public class GetException extends Exception{
    private String details;

    public GetException(String details) {
        this.details = details;
    }

    @Override
    public String getMessage() {
        return "Erreur lors de la récupération " + details;
    }
}
