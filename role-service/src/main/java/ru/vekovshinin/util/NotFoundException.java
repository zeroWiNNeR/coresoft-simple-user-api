package ru.vekovshinin.util;

public class NotFoundException extends RuntimeException {

  private NotFoundException(String message) {
    super(message);
  }

  public static class Role extends NotFoundException {

    public Role(Long id) {
      super(MessageUtil.get("warn.role.not.found.by.id", id));
    }
  }

}
