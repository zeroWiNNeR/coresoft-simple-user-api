package ru.vekovshinin.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Ответ с данными роли")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class RoleResponse {

  @Schema(description = "Id роли")
  private Long id;

  @Schema(description = "Название роли")
  private String name;

  @Schema(description = "Описание роли")
  private String description;

}
