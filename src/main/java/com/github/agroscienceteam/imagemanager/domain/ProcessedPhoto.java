package com.github.agroscienceteam.imagemanager.domain;

import java.util.UUID;
import lombok.NonNull;

public record ProcessedPhoto(@NonNull String indexName, @NonNull UUID photoId, @NonNull String result) {

}
