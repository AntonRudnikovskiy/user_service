package school.faang.user_service.controller.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.service.event.EventParticipationService;
import school.faang.user_service.validation.EventParticipationRequestValidator;

import java.util.List;


@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventParticipationController {

    private final EventParticipationService eventParticipationService;
    private final EventParticipationRequestValidator validator;


    @GetMapping("{eventId}/participants/count")
    public ResponseEntity<Integer> getParticipantsCount(@PathVariable Long eventId) {
        validator.validate(eventId);
        int count = eventParticipationService.getParticipantsCount(eventId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("{eventId}/participants")
    public ResponseEntity<List<UserDto>> getAllParticipants(@PathVariable Long eventId) {
        validator.validate(eventId);
        List<UserDto> userDtoList = eventParticipationService.getAllParticipants(eventId);
        return ResponseEntity.ok(userDtoList);
    }
}
