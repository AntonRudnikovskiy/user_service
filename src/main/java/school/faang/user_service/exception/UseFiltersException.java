package school.faang.user_service.exception;

public class UseFiltersException extends RuntimeException{
    public UseFiltersException() {
    }

    public UseFiltersException(String message) {
        super(message);
    }
}