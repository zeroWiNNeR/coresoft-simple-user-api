package ru.vekovshinin.service;

import java.util.List;
import ru.vekovshinin.model.domain.Role;

public interface IRoleService {

  List<Role> findAll();

  Role findById(Long id);

  void save(Role entity);

  void deleteById(Long id);

}
