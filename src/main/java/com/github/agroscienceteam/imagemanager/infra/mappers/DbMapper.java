package com.github.agroscienceteam.imagemanager.infra.mappers;

import asavershin.generated.package_.tables.records.PhotosRecord;
import asavershin.generated.package_.tables.records.WorkersResultsRecord;
import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DbMapper {

  WorkersResultsRecord map(WorkerResult workerResult);

  PhotosRecord map(Photo workerResult);

}