package ru.vekovshinin.runner;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.repository.UserRepo;

@AllArgsConstructor
@Component
public class InitialRunner implements ApplicationRunner {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(ApplicationArguments args) {
    if (userRepo.hasNoEntries()) {
      var user = User.builder()
          .username("admin")
          .password(passwordEncoder.encode("admin"))
          .build();

      userRepo.save(user);
    }
  }

}
