package com.github.agroscienceteam.imagemanager.infra.output;


import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.PHOTOS_INDEXES;
import static asavershin.generated.package_.tables.Indexes.INDEXES;

import com.github.agroscienceteam.imagemanager.domain.Photo;
import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.ProcessedPhoto;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.TableRecord;
import org.jooq.impl.TableImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PhotoRepositoryImpl implements PhotoRepository {

  @NonNull
  private final DSLContext dsl;
  @NonNull
  private final ModelMapper mapper;

  private static final Map<Class<?>, TableImpl<? extends TableRecord<?>>> tables = Map.of(
          Photo.class, PHOTOS,
          ProcessedPhoto.class, PHOTOS_INDEXES
  );

  @Override
  public <T> void save(T entity) {
    log.info("SAVING {}", mapper.map(entity, tables.get(entity.getClass()).getType()).toString());
    dsl.executeInsert(mapper.map(entity, tables.get(entity.getClass()).getType()));
  }

  @Override
  public @NonNull List<String> findAllIndexes() {
    return dsl.select().from(INDEXES).fetch(INDEXES.INDEX_NAME, String.class);
  }

}
