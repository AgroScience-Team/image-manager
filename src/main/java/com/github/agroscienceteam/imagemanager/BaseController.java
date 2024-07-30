package com.github.agroscienceteam.imagemanager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

  @GetMapping("/get")
  public String base() {
    return "base";
  }

}
