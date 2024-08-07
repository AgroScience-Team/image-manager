package com.github.agroscienceteam.imagemanager.steps;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

import com.github.agroscienceteam.imagemanager.infra.input.PhotoController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;



@RequiredArgsConstructor
public class ControllersSteps {

  private final PhotoController controller;
  @Value("${server.port}")
  @Setter
  public Integer port;
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
    assertEquals(expectedJson, actualJson.get(), LENIENT);
  }

}
