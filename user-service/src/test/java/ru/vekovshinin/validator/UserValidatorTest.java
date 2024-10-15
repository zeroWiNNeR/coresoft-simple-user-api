package ru.vekovshinin.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.repository.UserRepo;
import ru.vekovshinin.util.ValidationException;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

  @InjectMocks
  private UserValidator validator;
  @Mock
  private UserRepo repo;

  private User entity;

  @BeforeEach
  void setUp() {
    entity = User.builder()
        .id(1L)
        .username("test")
        .email("test@mail.ru")
        .build();
  }

  @Test
  void validate_IdIsNullExistsByUsernameOrEmail_Throws() {
    entity.setId(null);

    when(repo.existsByUsernameOrEmail(entity.getUsername(), entity.getEmail()))
        .thenReturn(true);

    assertThrows(ValidationException.UserExists.class, () -> validator.validate(entity));
  }

  @Test
  void validate_IdIsNotNullExistsByUsernameOrEmail_Throws() {
    when(repo.existsByUsernameOrEmail(entity.getId(), entity.getUsername(), entity.getEmail()))
        .thenReturn(true);

    assertThrows(ValidationException.UserExists.class, () -> validator.validate(entity));
  }

  @Test
  void validate_IncorrectEmail_Throws() {
    entity.setEmail("test");

    assertThrows(ValidationException.UserEmailIncorrect.class, () -> validator.validate(entity));
  }

}
