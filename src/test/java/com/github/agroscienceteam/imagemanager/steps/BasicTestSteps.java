package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.Constants.tables;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.agroscienceteam.imagemanager.domain.photo.PhotoRepository;
import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class BasicTestSteps {

  private final KafkaTemplate<String, String> producer;
  private final PhotoRepository repo;
  private final Map<String, TestListener> listeners;
  private final TestRepo testRepo;
  private final ModelMapper mapper;


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
    var expected = dt.asMaps().stream().map(m -> mapper.map(m, tables.get(tableName).getRecordType())).toList();
    await().atMost(time, MILLISECONDS)
            .untilAsserted(() -> {
              var actual = testRepo.getData(tableName);
              assertEquals(expected, actual);
            });
  }

  @SneakyThrows
  @Then("Kafka topic {string} receives message with key {string} in {long} millis")
  public void topicReceivesMessage(String topic, String expectedKey, long time, String expectedValue) {
    assertListener(topic, expectedKey, time, expectedValue);
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
                assertEquals(expectedValue, message.value());
              }
            }
    );
  }

}