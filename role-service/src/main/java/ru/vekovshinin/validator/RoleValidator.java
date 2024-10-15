package ru.vekovshinin.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.repository.RoleRepo;
import ru.vekovshinin.util.ValidationException;

@RequiredArgsConstructor
@Component
public class RoleValidator implements IRoleValidator {

  private final RoleRepo repo;

  @Override
  public void validate(Role role) {
    if (role.getId() == null) {
      if (repo.existsByName(role.getName())) {
        throw new ValidationException.RoleExists();
      }
    } else {
      if (repo.existsByName(role.getId(), role.getName())) {
        throw new ValidationException.RoleExists();
      }
    }
  }

}
