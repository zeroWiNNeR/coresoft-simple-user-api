package ru.vekovshinin.service;

import java.util.List;
import ru.vekovshinin.model.domain.User;

public interface IUserService {

  List<User> findAll();

  User findById(Long id);

  void save(User entity);

  void deleteById(Long id);

  boolean existsByBasicToken(String token);

}
