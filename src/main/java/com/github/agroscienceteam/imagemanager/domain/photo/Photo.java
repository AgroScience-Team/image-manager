package com.github.agroscienceteam.imagemanager.domain.photo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Value object that represents db table photo.
 *
 * @param id        uuid of photo, received from external service
 * @param contourId contour id from agro-fields-service
 * @param date      date of making photo
 * @param extension extension like TIFF, BIL, etc
 */
@Getter
@RequiredArgsConstructor
public class Photo {

  @JsonAlias({"photoId"})
  @JsonProperty("id")
  private final UUID id;
  private final UUID contourId;
  private final Instant date;
  private final String extension;

}