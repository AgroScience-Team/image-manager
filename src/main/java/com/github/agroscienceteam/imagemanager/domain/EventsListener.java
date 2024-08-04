package com.github.agroscienceteam.imagemanager.domain;

@FunctionalInterface
public interface EventsListener {

  void receive(String message);

}
