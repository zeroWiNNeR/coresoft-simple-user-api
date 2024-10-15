package ru.vekovshinin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vekovshinin.model.dto.UserRoleResponse;

@FeignClient(name = "role-service-client")
public interface RoleClient {

  @GetMapping("/api/roles/{id}")
  UserRoleResponse getById(@PathVariable("id") long id);

}
