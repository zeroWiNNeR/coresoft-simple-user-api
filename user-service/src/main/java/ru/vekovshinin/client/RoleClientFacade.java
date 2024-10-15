package ru.vekovshinin.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.dto.UserRoleResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class RoleClientFacade {

  private final RoleClient roleClient;

  public UserRoleResponse getRole(Long id) {
    return roleClient.getById(id);
  }

}
