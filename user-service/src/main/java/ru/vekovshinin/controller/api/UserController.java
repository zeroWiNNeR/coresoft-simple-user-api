package ru.vekovshinin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.vekovshinin.converter.IUserConverter;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.model.dto.UserAuthRequest;
import ru.vekovshinin.model.dto.UserRequest;
import ru.vekovshinin.model.dto.UserResponse;
import ru.vekovshinin.service.IUserRoleService;
import ru.vekovshinin.service.IUserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

  private final IUserConverter converter;
  private final IUserService service;
  private final IUserRoleService userRoleService;

  @Operation(description = "Получение списка всех пользователей")
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<UserResponse> getAll() {
    List<User> users = service.findAll();
    return converter.toDtos(users);
  }

  @Operation(description = "Получение пользователя по id")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserResponse getById(
      @Parameter(description = "id получаемого пользователя") @PathVariable Long id
  ) {
    User user = service.findById(id);
    return converter.toDto(user);
  }

  @Operation(description = "Создание нового пользователя")
  @PostMapping(value = "",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public UserResponse save(@Valid @RequestBody UserRequest dto) {
    User user = converter.toEntity(dto);
    service.save(user);
    return converter.toDto(user);
  }

  @Operation(description = "Обновление пользователя по ID")
  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Transactional
  public UserResponse update(
      @Parameter(description = "id изменяемого пользователя") @PathVariable Long id,
      @Valid @RequestBody UserRequest dto
  ) {
    User user = converter.toEntity(id, dto);
    service.save(user);
    return converter.toDto(user);
  }

  @Operation(description = "Удаление пользователя по ID")
  @DeleteMapping(value = "/{id}")
  public void deleteById(
      @Parameter(description = "id удаляемого пользователя") @PathVariable Long id
  ) {
    service.deleteById(id);
  }

  @Operation(description = "Получение пользователя по basicToken")
  @PostMapping(value = "/get-authenticated",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean getAuthenticated(
      @RequestBody UserAuthRequest request
  ) {
    return service.existsByBasicToken(request.getBasicToken());
  }

  @Operation(description = "Получение списка ролей пользователя")
  @GetMapping(value = "/{userId}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Long> getUserRoles(
      @Parameter(description = "id пользователя") @PathVariable Long userId
  ) {
    return userRoleService.findByUserId(userId);
  }

  @Operation(description = "Назначение роли пользователю")
  @PostMapping(value = "/{userId}/roles/{roleId}")
  @Transactional
  public void saveUserRole(
      @Parameter(description = "id пользователя") @PathVariable Long userId,
      @Parameter(description = "id роли") @PathVariable Long roleId
  ) {
    userRoleService.saveUserRole(userId, roleId);
  }

  @Operation(description = "Удаление роли у пользователя")
  @DeleteMapping(value = "/{userId}/roles/{roleId}")
  @Transactional
  public void deleteUserRole(
      @Parameter(description = "id пользователя") @PathVariable Long userId,
      @Parameter(description = "id роли") @PathVariable Long roleId
  ) {
    userRoleService.deleteUserRole(userId, roleId);
  }

}
