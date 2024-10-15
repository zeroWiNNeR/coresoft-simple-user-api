package ru.vekovshinin.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.model.dto.RoleRequest;
import ru.vekovshinin.model.dto.RoleResponse;
import ru.vekovshinin.service.IRoleService;

@RequiredArgsConstructor
@Component
public class RoleConverter implements IRoleConverter {

  private final IRoleService service;

  @Override
  public Role toEntity(RoleRequest dto) {
    return toEntity(null, dto);
  }

  @Override
  public Role toEntity(Long id, RoleRequest dto) {
    Role entity;
    if (id == null) {
      entity = new Role();
    } else {
      entity = service.findById(id);
    }

    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());

    return entity;
  }

  @Override
  public RoleResponse toDto(Role entity) {
    return RoleResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .build();
  }

}
