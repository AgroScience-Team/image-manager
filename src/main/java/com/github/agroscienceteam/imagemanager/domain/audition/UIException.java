package com.github.agroscienceteam.imagemanager.domain.audition;

public class UIException extends RuntimeException {

  public UIException(Throwable cause) {
    super(cause.getMessage(), cause);
  }

}
