package com.github.agroscienceteam.imagemanager.steps;

import static asavershin.generated.package_.Tables.PHOTOS;

import java.util.Map;
import org.jooq.impl.TableImpl;

public class Constants {

  public static final Map<String, TableImpl<?>> tables = Map.of(
          "photos", PHOTOS
  );

}
