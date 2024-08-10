package com.github.agroscienceteam.imagemanager.domain.photo;

import java.util.UUID;
import lombok.NonNull;

public record ProcessedPhoto(@NonNull String indexName,
                             @NonNull UUID photoId,
                             @NonNull String result,
                             @NonNull String extension) {

}
