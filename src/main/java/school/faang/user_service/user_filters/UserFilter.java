package school.faang.user_service.user_filters;

import school.faang.user_service.dto.UserFilterDto;
import school.faang.user_service.entity.User;

import java.util.stream.Stream;

public interface UserFilter {
    boolean isApplicable(UserFilterDto filters);

    Stream<User> apply(Stream<User> users, UserFilterDto filters);
}