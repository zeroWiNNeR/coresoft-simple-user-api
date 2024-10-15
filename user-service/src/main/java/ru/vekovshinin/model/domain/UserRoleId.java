package ru.vekovshinin.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Table(name = "user_role_lnk")
public class UserRoleId implements Serializable {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "role_id")
  private Long roleId;

}
