package com.github.agroscienceteam.imagemanager.infra.output;


import static asavershin.generated.package_.Tables.JOB_UUID_REGISTRY;
import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.WORKERS_RESULTS;
import static java.time.OffsetDateTime.ofInstant;
import static java.time.ZoneOffset.UTC;

import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.domain.photo.PhotoWithWorkersResults;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerResult;
import com.github.agroscienceteam.imagemanager.infra.mappers.CustomPhotoMapper;
import com.github.agroscienceteam.imagemanager.infra.mappers.DbMapper;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.TableRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoRepository {

  private final DSLContext dsl;
  private final CustomPhotoMapper customMapper;
  private final DefaultDSLContext dslContext;
  private final DbMapper dbMapper;

  @Override
  public List<PhotoWithWorkersResults> findByFieldId(
          final UUID contourId,
          final Instant from,
          final Instant to
  ) {
    return customMapper.map(
            dsl.select(
                            PHOTOS.CONTOUR_ID,
                            PHOTOS.DATE,
                            PHOTOS.EXTENSION
                    ).select(WORKERS_RESULTS.fields())
                    .from(PHOTOS)
                    .leftJoin(WORKERS_RESULTS)
                    .on(PHOTOS.ID.eq(WORKERS_RESULTS.PHOTO_ID))
                    .where(PHOTOS.CONTOUR_ID.eq(contourId).and(PHOTOS.DATE.between(
                            ofInstant(from, UTC), ofInstant(to, UTC)
                    )))
                    .orderBy(PHOTOS.DATE.desc())
                    .fetch()
    );
  }

  @Override
  public WorkerResult find(UUID photoId, String type) {
    return dsl.select(WORKERS_RESULTS.fields())
            .from(WORKERS_RESULTS)
            .where(WORKERS_RESULTS.PHOTO_ID.eq(photoId).and(WORKERS_RESULTS.TYPE.eq(type)))
            .fetchOneInto(WorkerResult.class);
  }

  @Override
  public UUID generateJobId() {
    UUID jobId;
    do {
      jobId = UUID.randomUUID();
    } while (jobIdExists(jobId));

    dsl.insertInto(JOB_UUID_REGISTRY)
            .set(JOB_UUID_REGISTRY.JOB_ID, jobId)
            .execute();

    return jobId;
  }

  @Override
  public void save(Object entity) {
    dsl.executeInsert((TableRecord<?>) entity);
  }

  @Override
  public void save(WorkerResult workerResult) {
    var map = dbMapper.map(workerResult).intoMap();
    map.remove("id");

    dslContext.insertInto(WORKERS_RESULTS)
            .set(map)
            .execute();
  }

  private boolean jobIdExists(UUID jobId) {
    return dsl.fetchExists(
            dsl.selectFrom(JOB_UUID_REGISTRY)
                    .where(JOB_UUID_REGISTRY.JOB_ID.eq(jobId))
    );
  }

}