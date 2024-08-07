package com.github.agroscienceteam.imagemanager.domain;

import java.time.LocalDate;
import java.util.List;

public interface PhotoRepository {

  <T> void save(T photo);

  List<PhotoWithProcessedPhotos> findByFieldId(Long fieldId, LocalDate pageNumber, LocalDate pageSize);

  List<String> findAllIndexes();

}
