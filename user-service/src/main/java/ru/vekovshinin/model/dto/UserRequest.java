package ru.vekovshinin.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

@Schema(description = "Запрос на сохранение/изменение пользователя")
@Validated
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserRequest {

  @Schema(description = "Имя пользователя (уникальное)")
  @NotBlank
  private String username;

  @Schema(description = "Email пользователя")
  @NotBlank
  private String email;

  @Schema(description = "Пароль пользователя")
  @NotBlank
  private String password;

}
