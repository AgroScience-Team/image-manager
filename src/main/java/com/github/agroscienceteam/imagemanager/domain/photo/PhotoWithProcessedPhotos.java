package com.github.agroscienceteam.imagemanager.domain.photo;

import java.util.List;
import lombok.NonNull;

public record PhotoWithProcessedPhotos(@NonNull Photo photo, @NonNull List<ProcessedPhoto> processedPhotos) {

}
