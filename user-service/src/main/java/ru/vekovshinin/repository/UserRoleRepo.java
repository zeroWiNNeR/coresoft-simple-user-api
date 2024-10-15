package ru.vekovshinin.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vekovshinin.model.domain.UserRole;
import ru.vekovshinin.model.domain.UserRoleId;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, UserRole> {

  @Query("select ur.id.roleId from UserRole ur where ur.id.userId=?1")
  List<Long> findRoleIdsByUserId(Long userId);

  void removeById(UserRoleId id);

}
