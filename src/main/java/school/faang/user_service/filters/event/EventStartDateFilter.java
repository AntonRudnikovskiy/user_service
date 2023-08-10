package school.faang.user_service.filters.event;

import org.springframework.stereotype.Component;
import school.faang.user_service.dto.event.EventFilterDto;
import school.faang.user_service.entity.event.Event;
import school.faang.user_service.filters.event.EventFilter;

import java.util.stream.Stream;

@Component
public class EventStartDateFilter implements EventFilter {
    @Override
    public boolean isApplicable(EventFilterDto filter) {
        return filter.getStartDate() != null;
    }

    @Override
    public Stream<Event> apply(Stream<Event> events, EventFilterDto filter) {
        return events.filter(event -> event.getStartDate().isAfter(filter.getStartDate()));
    }
}