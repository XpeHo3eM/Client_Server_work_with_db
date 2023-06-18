package xpeho3em.myProject.exception;

public class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}
