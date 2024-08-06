package com.github.agroscienceteam.imagemanager.domain;

@FunctionalInterface
public interface EventsListener<T> {

  void receive(T message);

}
