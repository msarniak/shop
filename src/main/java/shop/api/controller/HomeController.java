package shop.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirect from "/" to swagger
 */
@Slf4j
@Controller
public class HomeController {

  @GetMapping(value = "/")
  public String index() {
    log.debug("Redirecting home to swagger-ui.html.");
    return "redirect:swagger-ui.html";
  }

}