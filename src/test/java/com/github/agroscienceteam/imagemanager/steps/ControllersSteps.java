package com.github.agroscienceteam.imagemanager.steps;

import static com.github.agroscienceteam.imagemanager.steps.TestUtils.alignIdFields;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
public class ControllersSteps {

  private final ObjectMapper objectMapper;
  private final ThreadLocal<String> actualJson = ThreadLocal.withInitial(String::new);
  private final RestTemplate restTemplate = new RestTemplate();

  @When("UI send get request with url: {string}")
  @SneakyThrows
  public void UISend(String url) {
    actualJson.set(restTemplate.getForEntity(new URI(url), String.class).getBody());
  }

  @Then("UI receive response")
  @SneakyThrows
  public void UIReceive(String expectedJson) {
    var expectedNode = objectMapper.readTree(expectedJson);
    var actualNode = objectMapper.readTree(actualJson.get());

    alignIdFields(actualNode, expectedNode, "id");
    alignIdFields(actualNode, expectedNode, "jobId");

    assertEquals(expectedNode, actualNode, "JSON responses do not match");
  }

}
