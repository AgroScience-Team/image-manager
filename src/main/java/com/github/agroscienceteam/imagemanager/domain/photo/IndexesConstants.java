package com.github.agroscienceteam.imagemanager.domain.photo;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexesConstants {

  public static final List<String> INDEXES = new ArrayList<>(List.of("ndvi"));

}