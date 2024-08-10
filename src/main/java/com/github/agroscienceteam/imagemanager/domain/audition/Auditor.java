package com.github.agroscienceteam.imagemanager.domain.audition;

import org.aspectj.lang.JoinPoint;

public interface Auditor {

  String SYSTEM_NAME = "image-manager";
  String SUCCESS_KEY = "SUCCESS";
  String ERROR_KEY = "ERROR";

  void auditInfoBefore(JoinPoint jp);

  default void auditInfoAfter(JoinPoint jp) {

  }

  default void auditInfoAfter(JoinPoint jp, Object result) {

  }

  void auditError(JoinPoint jp, Exception e);

  String getMyKey();

}
