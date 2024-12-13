package khan.pet.exception;

public class UnknownWoodException extends RuntimeException {
    public UnknownWoodException() {
        super();
    }

    public UnknownWoodException(String message) {
        super(message);
    }
}
