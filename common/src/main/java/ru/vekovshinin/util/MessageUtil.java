package ru.vekovshinin.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageUtil {

  private MessageUtil() {
    throw new UnsupportedOperationException();
  }

  private static class MessagesHolder {

    private static final ResourceBundle INSTANCE = ResourceBundle.getBundle("messages");
  }

  public static String get(String key, Object... params) {
    return MessageFormat.format(MessagesHolder.INSTANCE.getString(key), params);
  }

}
