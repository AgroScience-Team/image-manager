package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.Constants.tables;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestRepo {

  private final DSLContext dsl;
  private final ModelMapper mapper;

  public void save(List<Map<String, String>> list, String tableName) {
    list.forEach(m -> dsl.executeInsert(mapper.map(m, tables.get(tableName).getType())));
  }

  public @NotNull List<Record> getData(String tableName) {
    return dsl.select().from(tables.get(tableName)).fetch();
  }
  
  public void delete(String tableName) {
    dsl.delete(tables.get(tableName)).execute();
  }

}
