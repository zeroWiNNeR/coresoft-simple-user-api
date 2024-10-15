package ru.vekovshinin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vekovshinin.model.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  boolean existsByUsernameOrEmail(String username, String email);

  @Query(value = """
      select case when count(*) = 1 then true else false end
      from users u
      where u.entity_id != ?1 and (u.username like ?2 or u.email like ?3)
      limit 1
      """, nativeQuery = true)
  boolean existsByUsernameOrEmail(Long id, String username, String email);

  User findUserByUsername(String username);

  @Query(value = """
      select case when count(*) = 0 then true else false end
      from users u
      limit 1
      """, nativeQuery = true)
  boolean hasNoEntries();

}
