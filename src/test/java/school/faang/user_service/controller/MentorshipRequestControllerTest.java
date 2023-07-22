package school.faang.user_service.controller;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import school.faang.user_service.entity.RequestStatus;
import school.faang.user_service.dto.filter.RequestFilterDto;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import school.faang.user_service.dto.mentorship.MentorshipRequestDto;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import school.faang.user_service.service.mentorship.MentorshipRequestService;

@ExtendWith(MockitoExtension.class)
public class MentorshipRequestControllerTest {

    @Mock
    private MentorshipRequestService mentorshipRequestService;

    @InjectMocks
    private MentorshipRequestController mentorshipRequestController;

    private Long requestId;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private MentorshipRequestDto mentorshipRequestDto;
    private MentorshipRequestDto badMentorshipRequestDto;

    RequestFilterDto requestFilterDto;
    RequestFilterDto badRequestFilterDto;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mentorshipRequestController).build();

        objectMapper = new ObjectMapper();

        mentorshipRequestDto = MentorshipRequestDto.builder()
                .requesterId(1L)
                .receiverId(2L)
                .description("Wanna code like you bruh")
                .build();

        badMentorshipRequestDto = MentorshipRequestDto.builder() // No mandatory field 'description' in this one
                .requesterId(1L)
                .receiverId(1L)
                .build();

        requestFilterDto = RequestFilterDto.builder()
                .requesterId(1L)
                .receiverId(2L)
                .description("Wanna be a good coder")
                .requestStatus(RequestStatus.PENDING)
                .build();

        badRequestFilterDto = RequestFilterDto.builder()
                .requesterId(1L)
                .description("")
                .build();

        requestId = 1L;
    }

    @Test
    @DisplayName("Request Mentorship: Valid request, should return 201")
    public void testRequestMentorship200isOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mentorship/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mentorshipRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Request Mentorship: Bad request, should return 400")
    public void testRequestMentorship400badRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mentorship/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badMentorshipRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Get requests: Valid request, should return 200")
    public void testGetRequests200IsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mentorship/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFilterDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Get requests: Bad request, should return 400")
    public void testGetRequests400BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mentorship/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestFilterDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Accept request: Positive scenario")
    public void testAcceptRequest200IsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/mentorship/request/" + requestId + "/accept")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Accept request: Bad request")
    public void testAcceptRequest400BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/mentorship/request/" + null + "/accept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}