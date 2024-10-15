package ru.vekovshinin.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "Ответ с данными пользователя")
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserResponse {

  @Schema(description = "Id пользователя")
  private Long id;

  @Schema(description = "Имя пользователя")
  private String username;

  @Schema(description = "Email пользователя")
  private String email;

  @Schema(description = "Когда добавлен пользователь")
  private LocalDateTime createdAt;

  @Schema(description = "Когда изменен пользователь")
  private LocalDateTime changedAt;

}
