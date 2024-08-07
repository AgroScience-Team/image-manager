package com.github.agroscienceteam.imagemanager.domain;

import java.util.List;

public interface PhotoRepository {

  <T> void save(T photo);

  List<String> findAllIndexes();

}
