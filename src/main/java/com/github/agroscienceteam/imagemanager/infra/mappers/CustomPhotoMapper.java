package com.github.agroscienceteam.imagemanager.infra.mappers;

import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.WORKERS_RESULTS;

import com.github.agroscienceteam.imagemanager.domain.photo.PhotoWithWorkersResults;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerResult;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;

@Component
public class CustomPhotoMapper {

  public List<PhotoWithWorkersResults> map(Result<Record> res) {
    Map<UUID, List<Record>> groupedByPhotoId = res.stream()
            .collect(Collectors.groupingBy(
                    r -> r.get(WORKERS_RESULTS.PHOTO_ID),
                    LinkedHashMap::new,
                    Collectors.toList()
            ));

    List<PhotoWithWorkersResults> result = new ArrayList<>();

    for (Map.Entry<UUID, List<Record>> entry : groupedByPhotoId.entrySet()) {
      List<Record> records = entry.getValue();

      List<WorkerResult> workerResults = records.stream()
              .map(rec -> new WorkerResult(
                      rec.get(WORKERS_RESULTS.ID),
                      rec.get(WORKERS_RESULTS.PHOTO_ID),
                      rec.get(WORKERS_RESULTS.JOB_ID),
                      rec.get(WORKERS_RESULTS.WORKER_NAME),
                      rec.get(WORKERS_RESULTS.TYPE),
                      rec.get(WORKERS_RESULTS.PATH),
                      rec.get(WORKERS_RESULTS.SUCCESS)
              ))
              .toList();

      Record firstRecord = records.getFirst();
      result.add(new PhotoWithWorkersResults(
              firstRecord.get(WORKERS_RESULTS.PHOTO_ID),
              firstRecord.get(PHOTOS.CONTOUR_ID),
              firstRecord.get(PHOTOS.DATE).toInstant(),
              firstRecord.get(PHOTOS.EXTENSION),
              workerResults
      ));
    }

    return result;
  }

}
