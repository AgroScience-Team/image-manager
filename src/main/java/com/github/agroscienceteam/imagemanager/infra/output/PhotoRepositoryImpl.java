package com.github.agroscienceteam.imagemanager.infra.output;


import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.PHOTOS_INDEXES;
import static asavershin.generated.package_.tables.Indexes.INDEXES;

import com.github.agroscienceteam.imagemanager.domain.Photo;
import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.PhotoWithProcessedPhotos;
import com.github.agroscienceteam.imagemanager.domain.ProcessedPhoto;
import com.github.agroscienceteam.imagemanager.infra.mappers.CustomPhotoMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.TableRecord;
import org.jooq.impl.TableImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

  @NonNull
  private final DSLContext dsl;
  @NonNull
  private final ModelMapper mapper;
  @NonNull
  private final CustomPhotoMapper customMapper;

  @Override
  public List<PhotoWithProcessedPhotos> findByFieldId(@NonNull Long fieldId,
                                                      @NonNull LocalDate from,
                                                      @NonNull LocalDate to) {
    return customMapper.map(dsl.select(
                    PHOTOS.FIELD_ID,
                    PHOTOS.PHOTO_DATE,
                    PHOTOS.PHOTO_EXTENSION
            ).select(PHOTOS_INDEXES.fields())
            .from(PHOTOS)
            .leftJoin(PHOTOS_INDEXES)
            .on(PHOTOS.PHOTO_ID.eq(PHOTOS_INDEXES.PHOTO_ID))
            .where(PHOTOS.FIELD_ID.eq(fieldId).and(PHOTOS.PHOTO_DATE.between(from, to)))
            .orderBy(PHOTOS.PHOTO_DATE.desc())
            .fetch());
  }

  private static final Map<Class<?>, TableImpl<? extends TableRecord<?>>> tables = Map.of(
          Photo.class, PHOTOS,
          ProcessedPhoto.class, PHOTOS_INDEXES
  );

  @Override
  public <T> void save(T entity) {
    dsl.executeInsert(mapper.map(entity, tables.get(entity.getClass()).getType()));
  }

  @Override
  public @NonNull List<String> findAllIndexes() {
    return dsl.select().from(INDEXES).fetch(INDEXES.INDEX_NAME, String.class);
  }

}
