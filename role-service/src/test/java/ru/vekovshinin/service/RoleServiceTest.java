package ru.vekovshinin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.repository.RoleRepo;
import ru.vekovshinin.util.ValidationException;
import ru.vekovshinin.validator.IRoleValidator;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

  @InjectMocks
  private RoleService service;
  @Mock
  private RoleRepo repo;
  @Mock
  private IRoleValidator validator;

  private Role entity;

  @BeforeEach
  void setUp() {
    entity = Role.builder()
        .id(1L)
        .name("test")
        .description("testDescr")
        .build();
  }

  @Test
  void findAll() {
    when(repo.findAll()).thenReturn(Collections.singletonList(entity));

    List<Role> result = service.findAll();

    assertEquals(1, result.size());
    assertEquals(entity, result.get(0));
  }

  @Test
  void findById() {
    when(repo.findById(entity.getId())).thenReturn(Optional.of(entity));

    Role result = service.findById(entity.getId());

    assertEquals(entity, result);
  }

  @Test
  void save() {
    service.save(entity);

    verify(repo, times(1)).save(entity);
  }

  @Test
  void save_ExistsByName() {
    doThrow(ValidationException.RoleExists.class).when(validator).validate(entity);

    assertThrows(ValidationException.RoleExists.class, () -> service.save(entity));
  }

  @Test
  void deleteById() {
    service.deleteById(entity.getId());

    verify(repo, times(1)).deleteById(entity.getId());
  }

}
