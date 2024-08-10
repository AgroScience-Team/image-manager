package com.github.agroscienceteam.imagemanager.domain.audition;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuditEntity {

  @NonNull protected final String system;
  @NonNull protected final Class<?> activeClass;
  @NonNull protected final String activeMethod;
  @NonNull protected final Object[] params;

}
