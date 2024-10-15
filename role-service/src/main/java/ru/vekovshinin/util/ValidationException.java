package ru.vekovshinin.util;

public class ValidationException extends RuntimeException {

  private ValidationException(String message) {
    super(message);
  }

  public static class RoleExists extends ValidationException {

    public RoleExists() {
      super(MessageUtil.get("warn.role.unique.violation"));
    }
  }

}
