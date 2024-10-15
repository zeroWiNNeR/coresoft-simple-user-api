package ru.vekovshinin.util;

public class ValidationException extends RuntimeException {

  private ValidationException(String message) {
    super(message);
  }

  public static class UserExists extends ValidationException {

    public UserExists() {
      super(MessageUtil.get("warn.user.unique.violation"));
    }
  }

  public static class UserEmailIncorrect extends ValidationException {

    public UserEmailIncorrect() {
      super(MessageUtil.get("warn.user.email.incorrect"));
    }
  }

}
