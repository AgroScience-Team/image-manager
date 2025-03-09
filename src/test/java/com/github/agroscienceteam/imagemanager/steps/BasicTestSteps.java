package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.TestUtils.alignIdFields;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class BasicTestSteps {

  private final KafkaTemplate<String, String> producer;
  private final Map<String, TestListener> listeners;
  private final ObjectMapper objectMapper;
  private final JdbcTemplate jdbcTemplate;

  @When("External system send message in topic {string}")
  public void sendMessage(String topic, String message) {
    producer.send(topic, message);
  }

  @When("External system send message in topic {string} with key {string}")
  public void sendMessage(String topic, String key, String message) {
    producer.send(topic, key, message);
  }

  @SneakyThrows
  @And("Table {string} receive data in {long} millis")
  public void dbTest(String tableName, long time, DataTable dt) {
    var expected = dt.asMaps(String.class, String.class);

    String whereClause = buildWhereClause(expected.get(0));

    String sql = "SELECT * FROM " + tableName + " WHERE " + whereClause;

    await().atMost(time, MILLISECONDS)
            .untilAsserted(() -> {
              List<Map<String, Object>> actual = jdbcTemplate.queryForList(sql);

              assertEquals(1L, actual.size());
            });
  }

  private String buildWhereClause(Map<String, String> record) {
    return record.entrySet().stream()
            .map(entry -> entry.getKey() + " = '" + entry.getValue() + "'")
            .collect(Collectors.joining(" AND "));
  }

  @SneakyThrows
  @Then("Kafka topic {string} receives message with key {string} in {long} millis")
  public void topicReceivesMessage(String topic, String expectedKey, long time, String expectedValue) {
    assertListener(topic, expectedKey, time, expectedValue);
  }

  @SneakyThrows
  @Then("Kafka topic {string} receives message in {long} millis")
  public void topicReceivesMessage(String topic, long time, String expectedValue) {
    assertListener(topic, null, time, expectedValue);
  }

  @SneakyThrows
  @Then("Kafka topic {string} receives audit message with key {string} in {long} millis")
  public void topicReceivesMessage2(String topic, String expectedKey, long time) {
    assertListener(topic, expectedKey, time, null);
  }

  private void assertListener(String topic, String expectedKey, long time, String expectedValue) {
    TestListener listener = listeners.get(topic);
    await().atMost(time, MILLISECONDS).untilAsserted(() -> {
              var message = listener.getMessage(100);
              assertNotNull(message);
              if (expectedKey != null) {
                assertEquals(expectedKey, message.key());
              }
              if (expectedValue != null) {
                var expected = objectMapper.readTree(expectedValue);
                var actual = objectMapper.readTree(message.value());

                alignIdFields(expected, actual, "jobId");

                assertEquals(expected, actual);
              }
            }
    );
  }

}