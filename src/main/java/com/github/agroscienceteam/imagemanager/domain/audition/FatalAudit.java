package com.github.agroscienceteam.imagemanager.domain.audition;

import lombok.NonNull;

public record FatalAudit(@NonNull String system,
                         @NonNull Class<? extends Throwable> cause,
                         @NonNull String exceptionMessage) {

}
