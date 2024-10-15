package ru.vekovshinin.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vekovshinin.converter.IUserConverter;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.model.dto.UserRequest;
import ru.vekovshinin.model.dto.UserResponse;
import ru.vekovshinin.service.IUserRoleService;
import ru.vekovshinin.service.IUserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @MockBean
  private IUserConverter converter;
  @MockBean
  private IUserService service;
  @MockBean
  private IUserRoleService userRoleService;
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper mapper;

  private User user;
  private UserRequest userRequest;
  private UserResponse userResponse;

  @BeforeEach
  void setUp() {
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
        .changedAt(LocalDateTime.of(2024, 10, 14, 16, 20, 20))
        .createdAt(LocalDateTime.of(2024, 10, 14, 16, 20, 20))
        .build();
  }

  @Test
  void getAll() throws Exception {
    when(service.findAll()).thenReturn(Collections.singletonList(user));
    when(converter.toDtos(Collections.singletonList(user)))
        .thenReturn(Collections.singletonList(userResponse));

    mvc.perform(MockMvcRequestBuilders.get("/api/users")
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk())
        .andExpect(content()
            .json(mapper.writeValueAsString(Collections.singletonList(userResponse))));
  }

  @Test
  void getById() throws Exception {
    when(service.findById(user.getId())).thenReturn(user);
    when(converter.toDto(user)).thenReturn(userResponse);

    mvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", user.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(userResponse)));
  }

  @Test
  void save() throws Exception {
    when(converter.toEntity(userRequest)).thenReturn(user);
    when(converter.toDto(user)).thenReturn(userResponse);

    mvc.perform(MockMvcRequestBuilders.post("/api/users")
            .header("Authorization", "Basic dGVzdDp0ZXN0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(userResponse)));
  }

  @Test
  void update() throws Exception {
    when(converter.toEntity(user.getId(), userRequest)).thenReturn(user);
    when(converter.toDto(user)).thenReturn(userResponse);

    mvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", user.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(userResponse)));
  }

  @Test
  void deleteById() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", user.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk());
  }

}
