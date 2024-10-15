package ru.vekovshinin.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.repository.UserRepo;
import ru.vekovshinin.util.RegExp;
import ru.vekovshinin.util.ValidationException;

@RequiredArgsConstructor
@Component
public class UserValidator implements IUserValidator {

  private final UserRepo repo;

  @Override
  public void validate(User user) {
    if (user.getId() == null) {
      if (repo.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
        throw new ValidationException.UserExists();
      }
    } else {
      if (repo.existsByUsernameOrEmail(user.getId(), user.getUsername(), user.getEmail())) {
        throw new ValidationException.UserExists();
      }
    }

    if (!user.getEmail().matches(RegExp.EMAIL_RFC_5322_PATTERN_STRING)) {
      throw new ValidationException.UserEmailIncorrect();
    }
  }

}
