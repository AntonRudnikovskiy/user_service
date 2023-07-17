package school.faang.user_service.filter.requestfilter;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.RequestFilterDto;
import school.faang.user_service.entity.recommendation.RecommendationRequest;

import java.util.stream.Stream;

@Component
public class CreateAtFilter implements RequestFilter {
    @Override
    public boolean isApplicable(RequestFilterDto filter) {
        return filter.getCreatedAt() != null;
    }

    @Override
    public Stream<RecommendationRequest> apply(Stream<RecommendationRequest> recommendationRequestStream, RequestFilterDto filters) {
        return recommendationRequestStream.filter(recommendationRequest -> {
            if (recommendationRequest.getCreatedAt() == null) {
                return false;
            } else {
                return recommendationRequest.getCreatedAt().isEqual(filters.getCreatedAt());
            }
        });
    }
}
