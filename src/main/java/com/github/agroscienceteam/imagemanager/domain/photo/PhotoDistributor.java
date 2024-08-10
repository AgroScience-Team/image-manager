package com.github.agroscienceteam.imagemanager.domain.photo;

@FunctionalInterface
public interface PhotoDistributor {

  void distribute(Photo photo);

}
