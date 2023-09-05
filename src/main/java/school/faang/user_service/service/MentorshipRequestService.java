package school.faang.user_service.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import school.faang.user_service.dto.MentorshipRequestDto;
import school.faang.user_service.dto.filter.RequestFilterDto;
import school.faang.user_service.service.mentorship.filter.MentorshipRequestFilter;
import school.faang.user_service.entity.MentorshipRequest;
import school.faang.user_service.entity.User;
import school.faang.user_service.mapper.MentorshipRequestMapper;
import school.faang.user_service.repository.UserRepository;
import school.faang.user_service.repository.mentorship.MentorshipRequestRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

import static school.faang.user_service.entity.RequestStatus.ACCEPTED;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MentorshipRequestService {
    private final List<MentorshipRequestFilter> mentorshipRequestFilters;
    private final UserRepository userRepository;
    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final MentorshipRequestMapper mapper;


    public MentorshipRequestDto requestMentorship(@Valid MentorshipRequestDto mentorshipRequestDto) {
        MentorshipRequest mentorshipRequest = mapper.toEntity(mentorshipRequestDto);
        validator.validateRequest(mentorshipRequest);

        Long requesterId = mentorshipRequest.getRequester().getId();
        Long receiverId = mentorshipRequest.getReceiver().getId();
        String description = mentorshipRequest.getDescription();

        if (!userRepository.existsById(requesterId)) {
            throw new IndexOutOfBoundsException("Requester must be registered");
        }
        if (!userRepository.existsById(receiverId)) {
            throw new IndexOutOfBoundsException("Receiver must be registered");
        }
        if (requesterId == receiverId) {
            throw new IndexOutOfBoundsException("A requester cannot be a receiver fo itself");
        }

        Optional<MentorshipRequest> optionalLatestRequest = mentorshipRequestRepository.findLatestRequest(requesterId, receiverId);

        if (optionalLatestRequest.isPresent()) {
            MentorshipRequest latestRequest = optionalLatestRequest.get();
            if (latestRequest.getUpdatedAt().isAfter(LocalDateTime.now().minusMonths(3))) {
                throw new RuntimeException("Request can only be made once every 3 months");
            }
        }

        MentorshipRequest newRequest = mentorshipRequestRepository.create(requesterId, receiverId, description);
        return mapper.toDto(newRequest);


   }

   public void acceptRequest(long requestId) {
        validator.validateAcceptRequest(requestId);
        MentorshipRequest request = repository.findById(requestId).get();
        User requester = request.getRequester();
        User receiver = request.getReceiver();

        if(requester.getMentors() == null) {
            requester.setMentors(List.of(receiver));
        } else {
            requester.getMentors().add(receiver);
        }
        request.setStatus(ACCEPTED);
   }
}
