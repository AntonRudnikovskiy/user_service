package school.faang.user_service.controller.recommendation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.faang.user_service.dto.recommendation.RecommendationDto;
import school.faang.user_service.exception.DataValidationException;
import school.faang.user_service.service.recommendation.RecommendationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {
    @InjectMocks
    private RecommendationController recommendationController;
    @Mock
    private RecommendationService recommendationService;

    @Test
    public void testGiveRecommendation() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent("content");

        when(recommendationService.create(recommendationDto)).thenReturn(recommendationDto);

        RecommendationDto result = recommendationController.giveRecommendation(recommendationDto);

        verify(recommendationService, times(1)).create(recommendationDto);
        assertEquals(recommendationDto, result);
    }

    @Test
    public void testUpdateRecommendation() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent("Sample content");

        when(recommendationService.update(recommendationDto)).thenReturn(recommendationDto);

        RecommendationDto result = recommendationController.updateRecommendation(recommendationDto);

        verify(recommendationService, times(1)).update(recommendationDto);
        assertEquals(recommendationDto, result);
    }

    @Test
    public void testGiveRecommendation_blankContent_throwException() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent("   ");

        assertThrows(DataValidationException.class,
                () -> recommendationController.giveRecommendation(recommendationDto));
    }

    @Test
    public void testGiveRecommendation_nullContent_throwException() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent(null);

        assertThrows(DataValidationException.class,
                () -> recommendationController.giveRecommendation(recommendationDto));
    }

    @Test
    public void testUpdateRecommendation_blankContent_throwException() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent("   ");

        assertThrows(DataValidationException.class,
                () -> recommendationController.updateRecommendation(recommendationDto));
    }

    @Test
    public void testUpdateRecommendation_nullContent_throwException() {
        RecommendationDto recommendationDto = new RecommendationDto();
        recommendationDto.setContent(null);

        assertThrows(DataValidationException.class,
                () -> recommendationController.updateRecommendation(recommendationDto));
    }

    @Test
    public void testGetAllUserRecommendations() {
        long userId = 1L;

        RecommendationDto recommendation1 = new RecommendationDto();
        recommendation1.setId(1L);
        recommendation1.setAuthorId(2L);
        recommendation1.setReceiverId(userId);
        recommendation1.setContent("Content 1");

        RecommendationDto recommendation2 = new RecommendationDto();
        recommendation2.setId(2L);
        recommendation2.setAuthorId(3L);
        recommendation2.setReceiverId(userId);
        recommendation2.setContent("Content 2");

        List<RecommendationDto> recommendationList = new ArrayList<>();
        recommendationList.add(recommendation1);
        recommendationList.add(recommendation2);

        when(recommendationService.getAllUserRecommendations(userId)).thenReturn(recommendationList);

        List<RecommendationDto> result = recommendationController.getAllUserRecommendations(userId);

        assertEquals(2, result.size());
        assertEquals(recommendation1.getId(), result.get(0).getId());
        assertEquals(recommendation2.getId(), result.get(1).getId());
    }

    @Test
    public void testGetAllGivenRecommendations() {
        long userId = 1L;

        RecommendationDto recommendation1 = new RecommendationDto();
        recommendation1.setId(1L);
        recommendation1.setAuthorId(userId);
        recommendation1.setReceiverId(2L);
        recommendation1.setContent("Content 1");

        RecommendationDto recommendation2 = new RecommendationDto();
        recommendation2.setId(2L);
        recommendation2.setAuthorId(userId);
        recommendation2.setReceiverId(3L);
        recommendation2.setContent("Content 2");

        List<RecommendationDto> recommendationList = new ArrayList<>();
        recommendationList.add(recommendation1);
        recommendationList.add(recommendation2);

        when(recommendationService.getAllGivenRecommendations(userId)).thenReturn(recommendationList);

        List<RecommendationDto> result = recommendationController.getAllGivenRecommendations(userId);

        verify(recommendationService, times(1)).getAllGivenRecommendations(userId);
        assertEquals(2, result.size());
        assertEquals(recommendation1.getId(), result.get(0).getId());
        assertEquals(recommendation2.getId(), result.get(1).getId());
    }
}