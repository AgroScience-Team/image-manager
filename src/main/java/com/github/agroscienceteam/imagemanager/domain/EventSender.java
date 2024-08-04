package com.github.agroscienceteam.imagemanager.domain;

@FunctionalInterface
public interface EventSender<T> {

  void send(String destination, T eventObject);

}
