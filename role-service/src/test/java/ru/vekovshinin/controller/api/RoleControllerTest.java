package ru.vekovshinin.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vekovshinin.converter.IRoleConverter;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.model.dto.RoleRequest;
import ru.vekovshinin.model.dto.RoleResponse;
import ru.vekovshinin.service.IRoleService;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

  @MockBean
  private IRoleConverter converter;
  @MockBean
  private IRoleService service;
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper mapper;

  private Role role;
  private RoleRequest roleRequest;
  private RoleResponse roleResponse;

  @BeforeEach
  void setUp() {
    role = Role.builder()
        .id(1L)
        .name("test")
        .description("testDescr")
        .build();
    roleRequest = RoleRequest.builder()
        .name("test")
        .description("testDescr")
        .build();
    roleResponse = RoleResponse.builder()
        .id(1L)
        .name("test")
        .description("testDescr")
        .build();
  }

  @Test
  void getAll() throws Exception {
    when(service.findAll()).thenReturn(Collections.singletonList(role));
    when(converter.toDtos(Collections.singletonList(role)))
        .thenReturn(Collections.singletonList(roleResponse));

    mvc.perform(MockMvcRequestBuilders.get("/api/roles")
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk())
        .andExpect(content()
            .json(mapper.writeValueAsString(Collections.singletonList(roleResponse))));
  }

  @Test
  void getById() throws Exception {
    when(service.findById(role.getId())).thenReturn(role);
    when(converter.toDto(role)).thenReturn(roleResponse);

    mvc.perform(MockMvcRequestBuilders.get("/api/roles/{id}", role.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(roleResponse)));
  }

  @Test
  void save() throws Exception {
    when(converter.toEntity(roleRequest)).thenReturn(role);
    when(converter.toDto(role)).thenReturn(roleResponse);

    mvc.perform(MockMvcRequestBuilders.post("/api/roles")
            .header("Authorization", "Basic dGVzdDp0ZXN0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(roleRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(roleResponse)));
  }

  @Test
  void update() throws Exception {
    when(converter.toEntity(role.getId(), roleRequest)).thenReturn(role);
    when(converter.toDto(role)).thenReturn(roleResponse);

    mvc.perform(MockMvcRequestBuilders.put("/api/roles/{id}", role.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(roleRequest)))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(roleResponse)));
  }

  @Test
  void deleteById() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/api/roles/{id}", role.getId())
            .header("Authorization", "Basic dGVzdDp0ZXN0"))
        .andExpect(status().isOk());
  }

}
