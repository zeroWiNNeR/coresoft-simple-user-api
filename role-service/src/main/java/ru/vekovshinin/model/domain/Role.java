package ru.vekovshinin.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class Role extends BaseEntity<Long> {

  @Id
  @SequenceGenerator(name = BASE_SEQ, sequenceName = BASE_SEQ, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = BASE_SEQ)
  @Column(name = "entity_id")
  private Long id;

  @Column(name = "role_name")
  private String name;

  @Column(name = "description")
  private String description;

}
