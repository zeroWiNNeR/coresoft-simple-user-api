package ru.vekovshinin.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.repository.RoleRepo;
import ru.vekovshinin.util.NotFoundException;
import ru.vekovshinin.validator.IRoleValidator;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService {

  private final RoleRepo repo;
  private final IRoleValidator validator;

  @Override
  public List<Role> findAll() {
    return repo.findAll();
  }

  @Override
  public Role findById(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException.Role(id));
  }

  @Override
  public void save(Role entity) {
    validator.validate(entity);

    repo.save(entity);
  }

  @Override
  public void deleteById(Long id) {
    repo.deleteById(id);
  }

}
