package com.github.agroscienceteam.imagemanager.domain.audition;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class AuditEntityWithResult extends AuditEntity {

  private final String result;

  public AuditEntityWithResult(@NonNull String system,
                               @NonNull Class<?> activeClass,
                               @NonNull String activeMethod,
                               @NonNull Object[] params, String result) {
    super(system, activeClass, activeMethod, params);
    this.result = result;
  }

}
