package ftn.sep.webshop.exception;


public class EntityNotFoundException extends AppException {

    public EntityNotFoundException() {
        super("Entity is not found");
    }

}
