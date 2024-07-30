package com.github.agroscienceteam.imagemanager.steps;

import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicTestStep {

    @When("User is working with the system")
    public void test(){
        log.info("BASIC_STEP_START");
        return;
    }

}
