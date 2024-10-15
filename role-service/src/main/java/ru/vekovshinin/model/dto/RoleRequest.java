package ru.vekovshinin.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

@Schema(description = "Запрос на сохранение/изменение роли")
@Validated
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RoleRequest {

  @Schema(description = "Название роли (уникальное)")
  @NotBlank
  private String name;

  @Schema(description = "Описание роли")
  private String description;

}
