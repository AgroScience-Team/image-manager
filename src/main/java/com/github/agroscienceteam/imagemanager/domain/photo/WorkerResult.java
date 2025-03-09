package com.github.agroscienceteam.imagemanager.domain.photo;

import java.util.UUID;

public record WorkerResult(
        UUID id,
        UUID photoId,
        UUID jobId,
        String workerName,
        String type,
        String path,
        boolean success
) {

}