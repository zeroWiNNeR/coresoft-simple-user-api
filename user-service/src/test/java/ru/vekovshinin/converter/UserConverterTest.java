package ru.vekovshinin.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.model.dto.UserRequest;
import ru.vekovshinin.model.dto.UserResponse;
import ru.vekovshinin.service.IUserService;

@ExtendWith(MockitoExtension.class)
class UserConverterTest {

  @InjectMocks
  private UserConverter converter;
  @Mock
  private IUserService service;
  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  private User user;
  private UserRequest userRequest;
  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
    LocalDateTime localDateTime = LocalDateTime.of(2024, 10, 14, 16, 20, 20);
    user = User.builder()
        .id(1L)
        .username("test")
        .email("test@mail.ru")
        .build();
    userRequest = UserRequest.builder()
        .username("test")
        .email("test@mail.ru")
        .password("test")
        .build();
    userResponse = UserResponse.builder()
        .id(1L)
        .username("test")
        .email("test@mail.ru")
        .changedAt(localDateTime)
        .createdAt(localDateTime)
        .build();
  }

  @Test
  void toEntity_WithoutId() {
    user.setId(null);
    user.setPassword("EncodedPassword");

    when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("EncodedPassword");

    User result = converter.toEntity(userRequest);

    assertEquals(user, result);
  }

  @Test
  void toEntity_WithId() {
    when(service.findById(user.getId())).thenReturn(user);

    User result = converter.toEntity(user.getId(), userRequest);

    assertEquals(user, result);
  }

  @Test
  void toDto() {
    LocalDateTime localDateTime = LocalDateTime.of(2024, 10, 14, 16, 20, 20);
    user.setCreatedAt(localDateTime);
    user.setChangedAt(localDateTime);

    UserResponse result = converter.toDto(user);

    assertEquals(userResponse, result);
  }

}
