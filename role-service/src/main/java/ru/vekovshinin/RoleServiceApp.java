package ru.vekovshinin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.vekovshinin.configuration.filter.RequestIdFilter;

@SpringBootApplication
@Import(RequestIdFilter.class)
public class RoleServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(RoleServiceApp.class, args);
  }

}
