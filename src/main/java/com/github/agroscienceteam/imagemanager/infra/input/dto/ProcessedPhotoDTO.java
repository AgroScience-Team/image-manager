package com.github.agroscienceteam.imagemanager.infra.input.dto;

import java.util.UUID;
import lombok.NonNull;

public record ProcessedPhotoDTO(@NonNull UUID photoId, @NonNull String result, @NonNull String extension) {

}
