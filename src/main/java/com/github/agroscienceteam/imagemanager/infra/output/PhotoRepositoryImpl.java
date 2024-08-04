package com.github.agroscienceteam.imagemanager.infra.output;


import static asavershin.generated.package_.tables.Indexes.INDEXES;

import com.github.agroscienceteam.imagemanager.domain.Photo;
import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import com.github.agroscienceteam.imagemanager.infra.mappers.PhotoMapper;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository("photoRepositoryImpl")
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

  @NonNull
  private final DSLContext dslContext;
  @NonNull
  private final PhotoMapper mapper;

  @Override
  public void save(Photo photo) {
    dslContext.executeInsert(mapper.map(photo));
  }

  @Override
  public @NonNull List<String> findAllIndexes() {
    return dslContext.select().from(INDEXES).fetch(INDEXES.INDEX_NAME, String.class);
  }

}
