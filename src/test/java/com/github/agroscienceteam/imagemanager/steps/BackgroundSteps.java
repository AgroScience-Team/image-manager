package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.Constants.tables;
import static java.util.stream.Collectors.toMap;

import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
@Slf4j
public class BackgroundSteps {

  @NonNull
  private final DSLContext dsl;

  private Map<String, TestListener> listeners;

  @Given("Table {string} is empty")
  public void givenDbTableIsClear(String tableName) {
    delete(tableName);
  }

  @And("Kafka topic {string} is clear")
  public void topicIsClear(String topic) {
    listeners.get(topic).clear();
  }

  @And("Table {string} is empty too")
  public void andDbTableIsClear(String tableName) {
    delete(tableName);
  }

  private void delete(String tableName) {
    dsl.delete(tables.get(tableName)).execute();
  }

  @Autowired
  public void setListeners(List<TestListener> listeners) {
    this.listeners = listeners.stream().collect(toMap(TestListener::getTopic, Function.identity()));
  }
}
