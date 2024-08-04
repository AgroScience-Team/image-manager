package com.github.agroscienceteam.imagemanager.domain;

@FunctionalInterface
public interface PhotoDistributor {

  void distribute(Photo photo);

}
