package com.github.agroscienceteam.imagemanager.infra.mappers;

import asavershin.generated.package_.tables.records.PhotosRecord;
import com.github.agroscienceteam.imagemanager.domain.Photo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

  PhotosRecord map(Photo photo);

}
