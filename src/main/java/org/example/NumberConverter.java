package org.example;

import java.math.BigDecimal;
import java.util.function.Function;

public class NumberConverter implements Function<String, BigDecimal> {

  @Override
  public BigDecimal apply(String number) {
    try {
      return new BigDecimal(number);
    } catch (NumberFormatException ex) {
      throw new ValidationException("Fails to parse number: '" + number + "'");
    }
  }
}
