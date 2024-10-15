package ru.vekovshinin.configuration.properties;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties implements InitializingBean {

  /*
   * Конфигурация БД
   */
  private String dbDriver;
  private String dbHost;
  private String dbPort;
  private String dbName;
  private String dbParam;
  private String dbSchema;
  private String dbUsername;
  private String dbPassword;

  /*
   * Конфигурация логов
   */
  private String logLevel;

  /*
   * Конфигурация приложения
   */
  private String serverPort;
  private String appName;


  @Override
  public void afterPropertiesSet() {
    StringBuilder sb = new StringBuilder();
    getProperties().forEach((k, v) -> sb.append('\n').append(k).append('=').append(v).append(','));
    log.info("Application properties: {}", sb);
  }

  private Map<String, String> getProperties() {
    Field[] fields = this.getClass().getDeclaredFields();
    Map<String, String> props = new LinkedHashMap<>(fields.length);

    for (Field field : fields) {
      try {
        if (Logger.class.isAssignableFrom(field.getType())) {
          continue;
        }

        Object value = field.get(this);
        Secret secretAnno = field.getAnnotation(Secret.class);
        String formattedValue = secretAnno == null ? value.toString() : "*****";
        props.put(field.getName(), formattedValue);
      } catch (Exception e) {
        log.error("Couldn't read field value " + field.getName(), e);
      }
    }

    return props;
  }

}
