package com.github.agroscienceteam.imagemanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;
import lombok.NonNull;

/**
 * Value object that represents db table photo.
 *
 * @param photoId        uuid of photo, received from external service
 * @param fieldId        field id from fields-and-crops service
 * @param photoDate      date of making photo
 * @param photoExtension extension like TIFF, BIL, etc
 */
public record Photo(@NonNull UUID photoId,
                    @NonNull Long fieldId,
                    @NonNull LocalDate photoDate,
                    @NonNull String photoExtension) {

}
