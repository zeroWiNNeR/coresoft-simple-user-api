package ru.vekovshinin.util;

public class NotFoundException extends RuntimeException {

  private NotFoundException(String message) {
    super(message);
  }

  public static class User extends NotFoundException {

    public User(Long id) {
      super(MessageUtil.get("warn.user.not.found.by.id", id));
    }
  }

}
