package ru.vekovshinin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vekovshinin.converter.IRoleConverter;
import ru.vekovshinin.model.domain.Role;
import ru.vekovshinin.model.dto.RoleRequest;
import ru.vekovshinin.model.dto.RoleResponse;
import ru.vekovshinin.service.IRoleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
@Validated
public class RoleController {

  private final IRoleConverter converter;
  private final IRoleService service;

  @Operation(description = "Получение списка всех ролей")
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<RoleResponse> getAll() {
    List<Role> roles = service.findAll();
    return converter.toDtos(roles);
  }

  @Operation(description = "Получение роли по id")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public RoleResponse getById(
      @Parameter(description = "id получаемой роли") @PathVariable Long id
  ) {
    Role role = service.findById(id);
    return converter.toDto(role);
  }

  @Operation(description = "Создание новой роли")
  @PostMapping(value = "",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public RoleResponse save(@Valid @RequestBody RoleRequest dto) {
    Role role = converter.toEntity(dto);
    service.save(role);
    return converter.toDto(role);
  }

  @Operation(description = "Обновление роли по id")
  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public RoleResponse update(
      @Parameter(description = "id изменяемой роли") @PathVariable Long id,
      @Valid @RequestBody RoleRequest dto
  ) {
    Role role = converter.toEntity(id, dto);
    service.save(role);
    return converter.toDto(role);
  }

  @Operation(description = "Удаление роли по id")
  @DeleteMapping(value = "/{id}")
  public void deleteById(
      @Parameter(description = "id удаляемой роли") @PathVariable Long id
  ) {
    service.deleteById(id);
  }

}


