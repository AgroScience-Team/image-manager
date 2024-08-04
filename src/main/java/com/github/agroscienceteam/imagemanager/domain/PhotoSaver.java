package com.github.agroscienceteam.imagemanager.domain;

@FunctionalInterface
public interface PhotoSaver {

  void save(Photo photo);

}
