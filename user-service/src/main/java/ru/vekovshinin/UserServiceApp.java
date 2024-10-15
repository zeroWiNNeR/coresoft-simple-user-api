package ru.vekovshinin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.vekovshinin.configuration.filter.RequestIdFilter;

@SpringBootApplication
@EnableFeignClients
@Import(RequestIdFilter.class)
public class UserServiceApp {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApp.class, args);
  }

}
