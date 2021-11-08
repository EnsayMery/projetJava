package exception;

public class EmptyArrayListException extends Exception{
    private String details;

    public EmptyArrayListException(String details) {
        this.details = details;
    }


    @Override
    public String getMessage() {
        return details;
    }
}
