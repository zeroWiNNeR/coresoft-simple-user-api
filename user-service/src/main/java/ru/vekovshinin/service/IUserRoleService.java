package ru.vekovshinin.service;

import java.util.List;

public interface IUserRoleService {

  List<Long> findByUserId(Long userId);

  void saveUserRole(Long userId, Long roleId);

  void deleteUserRole(Long userId, Long roleId);

}
