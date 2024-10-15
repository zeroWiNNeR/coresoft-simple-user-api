package ru.vekovshinin.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.repository.RoleRepo;
import ru.vekovshinin.util.ValidationException;

@ExtendWith(MockitoExtension.class)
public class RoleValidatorTest {

  @InjectMocks
  private RoleValidator validator;
  @Mock
  private RoleRepo repo;

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
  void validate_IdIsNullExistsByName_Throws() {
    entity.setId(null);

    when(repo.existsByName(entity.getName())).thenReturn(true);

    assertThrows(ValidationException.RoleExists.class, () -> validator.validate(entity));
  }

  @Test
  void validate_IdIsNotNullExistsByName_Throws() {
    when(repo.existsByName(entity.getId(), entity.getName())).thenReturn(true);

    assertThrows(ValidationException.RoleExists.class, () -> validator.validate(entity));
  }

}
