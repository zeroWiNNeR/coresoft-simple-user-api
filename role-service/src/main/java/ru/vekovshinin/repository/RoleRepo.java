package ru.vekovshinin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vekovshinin.model.domain.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  boolean existsByName(String name);

  @Query(value = """
      select case when count(*) = 1 then true else false end
      from roles r
      where r.entity_id != ?1 and r.role_name like ?2
      limit 1
      """, nativeQuery = true)
  boolean existsByName(Long id, String name);

}
