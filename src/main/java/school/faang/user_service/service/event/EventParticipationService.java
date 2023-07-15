package school.faang.user_service.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.faang.user_service.dto.UserDto;
import school.faang.user_service.mapper.UserMapper;
import school.faang.user_service.repository.event.EventParticipationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipationService {

    private final EventParticipationRepository eventParticipationRepository;
    private final UserMapper userMapper;

    public int getParticipantsCount(Long eventId) {
        return eventParticipationRepository.countParticipants(eventId);
    }

    public List<UserDto> getAllParticipants(Long eventId) {
        return userMapper.toDtoList(eventParticipationRepository.findAllParticipantsByEventId(eventId));
    }
}
