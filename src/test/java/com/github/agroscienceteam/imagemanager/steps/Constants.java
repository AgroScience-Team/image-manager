package com.github.agroscienceteam.imagemanager.steps;

import static asavershin.generated.package_.Tables.JOB_UUID_REGISTRY;
import static asavershin.generated.package_.Tables.PHOTOS;
import static asavershin.generated.package_.Tables.WORKERS_RESULTS;

import java.util.Map;
import org.jooq.TableRecord;
import org.jooq.impl.TableImpl;

public class Constants {

  public static final Map<String, TableImpl<? extends TableRecord<?>>> tables = Map.of(
          "photos", PHOTOS,
          "workers_results", WORKERS_RESULTS,
          "job_uuid_registry", JOB_UUID_REGISTRY
  );

}