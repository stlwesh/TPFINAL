package Exception;

public class EvenementDejaExistantException extends RuntimeException {
    public EvenementDejaExistantException(String message) {
        super(message);
    }
}
