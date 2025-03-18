package com.github.agroscienceteam.imagemanager.infra.mappers;

import static java.time.ZoneOffset.UTC;

import asavershin.generated.package_.tables.records.PhotosRecord;
import asavershin.generated.package_.tables.records.WorkersResultsRecord;
import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerResult;
import java.time.Instant;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DbMapper {

  WorkersResultsRecord map(WorkerResult workerResult);

  @Mapping(target = "date", source = "date", qualifiedByName = "instantToOffsetDateTime")
  PhotosRecord map(Photo workerResult);

  @Named("instantToOffsetDateTime")
  static OffsetDateTime map(Instant value) {
    return value != null ? OffsetDateTime.ofInstant(value, UTC) : null;
  }

}