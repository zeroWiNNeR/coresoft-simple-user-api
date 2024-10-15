package ru.vekovshinin.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vekovshinin.client.RoleClientFacade;
import ru.vekovshinin.model.domain.UserRole;
import ru.vekovshinin.model.domain.UserRoleId;
import ru.vekovshinin.model.dto.UserRoleResponse;
import ru.vekovshinin.repository.UserRoleRepo;

@RequiredArgsConstructor
@Service
public class UserRoleService implements IUserRoleService {

  private final UserRoleRepo repo;
  private final RoleClientFacade roleClientFacade;

  @Override
  public List<Long> findByUserId(Long userId) {
    return repo.findRoleIdsByUserId(userId);
  }

  @Override
  public void saveUserRole(Long userId, Long roleId) {
    UserRoleResponse role = roleClientFacade.getRole(roleId);
    repo.save(new UserRole(new UserRoleId(userId, role.getId())));
  }

  @Override
  public void deleteUserRole(Long userId, Long roleId) {
    repo.removeById(new UserRoleId(userId, roleId));
  }

}
