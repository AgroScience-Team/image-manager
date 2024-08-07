package com.github.agroscienceteam.imagemanager.steps;

import static asavershin.generated.package_.Tables.INDEXES;
import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.PHOTOS_INDEXES;

import java.util.Map;
import org.jooq.TableRecord;
import org.jooq.impl.TableImpl;

public class Constants {

  public static final Map<String, TableImpl<? extends TableRecord<?>>> tables = Map.of(
          "photos", PHOTOS,
          "photos_indexes", PHOTOS_INDEXES,
          "indexes", INDEXES
  );

}
