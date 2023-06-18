package xpeho3em.myProject.exception;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(String message) {
        super(message);
    }
}
