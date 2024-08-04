package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.Constants.tables;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.agroscienceteam.imagemanager.domain.Photo;
import com.github.agroscienceteam.imagemanager.domain.PhotoRepository;
import com.github.agroscienceteam.imagemanager.listeners.TestListener;
import com.github.agroscienceteam.imagemanager.mappers.JsonTestMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class BasicTestSteps {

  private final KafkaTemplate<String, String> producer;
  private final PhotoRepository repo;
  private final List<TestListener> listeners;
  private final JsonTestMapper mapper;
  private final DSLContext dsl;


  @When("External system send message in topic {string}")
  public void sendMessage(String topic, String message) {
    producer.send(topic, message);
  }

  @SneakyThrows
  @Then("Workers kafka topics receives message with key {string} in {long} millis")
  public void receiveMessages(String expectedKey, long time, String expectedValue) {
    assertEquals("Number or indexes in DB and Initializer not equals",
            repo.findAllIndexes().size(),
            listeners.size()
    );
    listeners.forEach(l -> await()
            .atMost(time, MILLISECONDS)
            .untilAsserted(() -> {
                      var message = l.getMessage(100);
                      assertNotNull(message);
                      assertEquals(expectedKey, message.key());
                      assertEquals(expectedValue, message.value());
                    }
            ));
  }

  @SneakyThrows
  @And("Table {string} contains data")
  public void dbTest(String tableName, String messages) {
    var expected = mapper.parseList(messages, Photo.class);
    var actual = dsl.select().from(tables.get(tableName)).fetchInto(Photo.class);
    assertEquals(expected, actual);
  }

}