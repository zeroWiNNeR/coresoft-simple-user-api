package ru.vekovshinin.converter;

import java.util.List;
import java.util.stream.StreamSupport;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.model.dto.RoleRequest;
import ru.vekovshinin.model.dto.RoleResponse;

public interface IRoleConverter {

  Role toEntity(RoleRequest dto);

  Role toEntity(Long id, RoleRequest dto);

  RoleResponse toDto(Role entity);

  default List<RoleResponse> toDtos(final Iterable<Role> entities) {
    return StreamSupport.stream(entities.spliterator(), false)
        .map(this::toDto)
        .toList();
  }

}
