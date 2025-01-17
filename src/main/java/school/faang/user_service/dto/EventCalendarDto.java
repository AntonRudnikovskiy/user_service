package school.faang.user_service.dto;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class EventCalendarDto {
    private List<Integer> participantsIds;

    @NotNull
    private Long authorId;

    @NotNull
    private String summary;

    @NotNull
    private String location;

    @NotNull
    private String description;

    private DateTime startDateTime;

    private DateTime endDateTime;

    private TimeZone timeZone;
}
