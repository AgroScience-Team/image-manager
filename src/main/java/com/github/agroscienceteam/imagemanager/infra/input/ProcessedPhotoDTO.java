package com.github.agroscienceteam.imagemanager.infra.input;

import java.util.UUID;
import lombok.NonNull;

public record ProcessedPhotoDTO(@NonNull UUID photoId, @NonNull String result) {

}
