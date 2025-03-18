package com.github.agroscienceteam.imagemanager.domain.photo;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PhotoWithWorkersResults extends Photo {

  private final List<WorkerResult> workerResults;

  public PhotoWithWorkersResults(
          UUID id, UUID contourId, Instant date, String extension, List<WorkerResult> workerResults
  ) {
    super(id, contourId, date, extension);
    this.workerResults = workerResults;
  }

}