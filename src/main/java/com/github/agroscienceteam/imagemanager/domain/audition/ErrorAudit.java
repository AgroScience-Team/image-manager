package com.github.agroscienceteam.imagemanager.domain.audition;

import lombok.Getter;

@Getter
public class ErrorAudit extends AuditEntity {

  private final Class<? extends Throwable> cause;
  private final String exceptionMessage;

  public ErrorAudit(String system,
                    Class<?> activeClass,
                    String activeMethod,
                    Object[] params,
                    Class<? extends Throwable> cause,
                    String exceptionMessage) {
    super(system, activeClass, activeMethod, params);
    this.cause = cause;
    this.exceptionMessage = exceptionMessage;
  }

}
