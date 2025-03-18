package com.github.agroscienceteam.imagemanager.domain.photo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PhotoRepository {

  void save(Object object);

  void save(WorkerResult workerResult);

  List<PhotoWithWorkersResults> findByFieldId(UUID contourId, Instant pageNumber, Instant pageSize);

  WorkerResult find(UUID photoId, String type);

  UUID generateJobId();

}