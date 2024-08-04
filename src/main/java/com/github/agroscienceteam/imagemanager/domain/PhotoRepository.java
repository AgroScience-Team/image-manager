package com.github.agroscienceteam.imagemanager.domain;

import java.util.List;

public interface PhotoRepository {

  void save(Photo photo);

  List<String> findAllIndexes();

}
