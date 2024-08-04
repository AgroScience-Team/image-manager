package com.github.agroscienceteam.imagemanager.models;

import lombok.NonNull;

public record KafkaMessage(String key, @NonNull String value) {

}
