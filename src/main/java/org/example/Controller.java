package org.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private static final String NUMBER_SEPARATOR = ",";
  private static final NumberConverter NUMBER_CONVERTER = new NumberConverter();

  @GetMapping("/calc")
  public ResponseEntity<Map<String, Object>> calculate(@RequestParam("method") String method,
      @RequestParam("numbers") String numbersArg) {
    try {
      Operation operation = Operation.findByName(method);
      if (operation == null) {
        return new ResponseEntity<>(
            Collections.singletonMap("error", "Unsupported method: '" + method + "'"),
            HttpStatus.BAD_REQUEST);
      }

      List<BigDecimal> numbers = Arrays.stream(numbersArg.split(NUMBER_SEPARATOR))
          .map(NUMBER_CONVERTER).collect(Collectors.toList());

      operation.getValidationMethod().accept(numbers);

      BigDecimal result = operation.getCalcMethod().apply(numbers);
      return new ResponseEntity<>(Collections.singletonMap("result", result), HttpStatus.OK);
    } catch (ValidationException ex) {
      return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()),
          HttpStatus.BAD_REQUEST);
    } catch (Exception ex) {
      LOG.error("Internal server error, method=" + method + ", numbers=" + numbersArg, ex);
      return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}


