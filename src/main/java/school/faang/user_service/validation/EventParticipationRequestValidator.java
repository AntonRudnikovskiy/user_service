package school.faang.user_service.validation;

import org.springframework.stereotype.Component;
import school.faang.user_service.exception.RequestValidationException;

import java.util.Arrays;


@Component
public class EventParticipationRequestValidator {

    public void validate(Object obj) throws RequestValidationException {
        if (obj == null || (obj instanceof String && obj.toString().isBlank())) {
            throw new RequestValidationException("Object cannot be null or empty");
        }
    }

    public void validate(Object... params) throws RequestValidationException {
        Arrays.stream(params).forEach(this::validate);
    }
}