package ru.vekovshinin.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.model.dto.UserRequest;
import ru.vekovshinin.model.dto.UserResponse;
import ru.vekovshinin.service.IUserService;

@RequiredArgsConstructor
@Component
public class UserConverter implements IUserConverter {

  private final IUserService service;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User toEntity(UserRequest dto) {
    return toEntity(null, dto);
  }

  @Override
  public User toEntity(Long id, UserRequest dto) {
    User entity;
    if (id == null) {
      entity = new User();
      entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    } else {
      entity = service.findById(id);
    }

    entity.setUsername(dto.getUsername());
    entity.setEmail(dto.getEmail());

    return entity;
  }

  @Override
  public UserResponse toDto(User entity) {
    return UserResponse.builder()
        .id(entity.getId())
        .username(entity.getUsername())
        .email(entity.getEmail())
        .createdAt(entity.getCreatedAt())
        .changedAt(entity.getChangedAt())
        .build();
  }

}
