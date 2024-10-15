package ru.vekovshinin.converter;

import java.util.List;
import java.util.stream.StreamSupport;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.model.dto.UserRequest;
import ru.vekovshinin.model.dto.UserResponse;

public interface IUserConverter {

  User toEntity(UserRequest dto);

  User toEntity(Long id, UserRequest dto);

  UserResponse toDto(User entity);

  default List<UserResponse> toDtos(final Iterable<User> entities) {
    return StreamSupport.stream(entities.spliterator(), false)
        .map(this::toDto)
        .toList();
  }

}
