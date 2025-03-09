package com.github.agroscienceteam.imagemanager.domain;

import com.github.agroscienceteam.imagemanager.domain.photo.Photo;
import com.github.agroscienceteam.imagemanager.domain.photo.WorkerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

  @Mapping(target = "photoId", source = "photo.id")
  WorkerRequest toWorkerRequest(Photo photo, String jobId);

}