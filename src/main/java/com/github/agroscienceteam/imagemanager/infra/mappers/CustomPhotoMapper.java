package com.github.agroscienceteam.imagemanager.infra.mappers;

import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.PHOTOS_INDEXES;

import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoWithProcessedPhotos;
import com.github.agroscienceteam.imagemanager.domain.photo.ProcessedPhoto;
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

  public List<PhotoWithProcessedPhotos> map(Result<Record> rec) {
    Map<UUID, List<Record>> groupedByPhotoId = rec.stream()
            .collect(Collectors.groupingBy(
                    r -> r.get(PHOTOS.PHOTO_ID),
                    LinkedHashMap::new,
                    Collectors.toList()
            ));

    List<PhotoWithProcessedPhotos> result = new ArrayList<>();

    for (Map.Entry<UUID, List<Record>> entry : groupedByPhotoId.entrySet()) {
      List<Record> records = entry.getValue();

      Record firstRecord = records.get(0);
      Photo photo = new Photo(
              firstRecord.get(PHOTOS.PHOTO_ID),
              firstRecord.get(PHOTOS.FIELD_ID),
              firstRecord.get(PHOTOS.PHOTO_DATE),
              firstRecord.get(PHOTOS.PHOTO_EXTENSION)
      );

      List<ProcessedPhoto> processedPhotos = records.stream()
              .map(rec -> new ProcessedPhoto(
                      rec.get(PHOTOS_INDEXES.INDEX_NAME),
                      rec.get(PHOTOS.PHOTO_ID),
                      rec.get(PHOTOS_INDEXES.RESULT),
                      rec.get(PHOTOS_INDEXES.EXTENSION)
              ))
              .toList();

      result.add(new PhotoWithProcessedPhotos(photo, processedPhotos));
    }

    return result;
  }

}
