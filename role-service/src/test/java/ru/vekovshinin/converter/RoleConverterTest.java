package ru.vekovshinin.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.model.dto.RoleRequest;
import ru.vekovshinin.model.dto.RoleResponse;
import ru.vekovshinin.service.IRoleService;

@ExtendWith(MockitoExtension.class)
class RoleConverterTest {

  @InjectMocks
  private RoleConverter converter;
  @Mock
  private IRoleService service;

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
  void toEntity_WithoutId() {
    role.setId(null);

    Role result = converter.toEntity(roleRequest);

    assertEquals(role, result);
  }

  @Test
  void toEntity_WithId() {
    when(service.findById(role.getId())).thenReturn(role);

    Role result = converter.toEntity(role.getId(), roleRequest);

    assertEquals(role, result);
  }

  @Test
  void toDto() {
    RoleResponse result = converter.toDto(role);

    assertEquals(roleResponse, result);
  }

}
