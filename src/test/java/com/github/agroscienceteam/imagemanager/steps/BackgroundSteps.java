package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.domain.photo.IndexesConstants.INDEXES;
import static java.util.stream.Collectors.toMap;

import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;

public class BackgroundSteps {

  @Autowired
  private TestRepo repo;

  private Map<String, TestListener> listeners;

  @Given("Table {string} is empty")
  public void givenDbTableIsClear(String tableName) {
    repo.delete(tableName);
  }

  @And("Kafka topic {string} is clear")
  public void topicIsClear(String topic) {
    listeners.get(topic).clear();
  }

  @And("Db table {string} contains data:")
  public void dbTableContainsData(String tableName, DataTable dt) {
    repo.delete(tableName);
    repo.save(dt.asMaps(), tableName);
  }

  @Given("The following indexes exist:")
  public void thereExistTheFollowingIndexes(List<String> indexes) {
    INDEXES.clear();

    INDEXES.addAll(indexes);
  }

  @Autowired
  public void setListeners(List<TestListener> listeners) {
    this.listeners = listeners.stream().collect(toMap(TestListener::getTopic, Function.identity()));
  }

}