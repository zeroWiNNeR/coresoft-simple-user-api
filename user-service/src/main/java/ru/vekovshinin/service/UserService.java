package ru.vekovshinin.service;

import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.vekovshinin.model.domain.User;
import ru.vekovshinin.repository.UserRepo;
import ru.vekovshinin.util.NotFoundException;
import ru.vekovshinin.validator.IUserValidator;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

  private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

  private final UserRepo repo;
  private final IUserValidator validator;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll() {
    return repo.findAll();
  }

  @Override
  public User findById(Long id) {
    return repo.findById(id).orElseThrow(() -> new NotFoundException.User(id));
  }

  @Override
  public void save(User entity) {
    validator.validate(entity);

    repo.save(entity);
  }

  @Override
  public void deleteById(Long id) {
    repo.deleteById(id);
  }

  @Override
  public boolean existsByBasicToken(String token) {
    String basicCredentials = new String(BASE64_DECODER.decode(token));
    String[] credentials = basicCredentials.split(":");
    String username = credentials[0];
    String password = credentials[1];

    if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
      return false;
    }

    User user = repo.findUserByUsername(username);
    if (user == null) {
      return false;
    }

    return passwordEncoder.matches(password, user.getPassword());
  }

}
