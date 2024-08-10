package com.github.agroscienceteam.imagemanager.domain.photo;

@FunctionalInterface
public interface PhotoSaver {

  void save(Photo photo);

}
