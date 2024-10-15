package ru.vekovshinin.model.domain;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

  public static final String BASE_SEQ = "base_seq";

  public abstract T getId();

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass() || !this.getClass().isInstance(o)) {
      return false;
    }

    try {
      BaseEntity<T> that = (BaseEntity<T>) o;
      return Objects.equals(getId(), that.getId());
    } catch (ClassCastException e) {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getId() != null ? getId().hashCode() : 0;
  }

}
