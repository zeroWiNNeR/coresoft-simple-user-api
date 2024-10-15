package ru.vekovshinin.model.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role_lnk")
public class UserRole extends BaseEntity<UserRoleId> {

  @EmbeddedId
  private UserRoleId id;

}
