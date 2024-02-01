package org.example;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public enum Operation {
  SUM("sum",
      x -> {},
      x -> x.stream().reduce(BigDecimal.ZERO, BigDecimal::add)),
  MULTIPLY("multiply",
      x -> {
          if (x.isEmpty()) {
              throw new ValidationException(
                  "Incorrect numbers' count for operation 'multiply': 0");
          }
      },
      x -> x.stream().reduce(BigDecimal.ONE, BigDecimal::multiply)),
  MULTIPLY_SUM("multiply_sum",
      x -> {
        if (x.size() != 3) {
          throw new ValidationException(
              "Incorrect numbers' count for operation 'multiply_sum': " + x.size());
        }
      },
      x -> x.get(0).multiply(x.get(1)).add(x.get(2))),
    MAX("max",
        x -> {
            if (x.isEmpty()) {
                throw new ValidationException(
                    "Incorrect numbers' count for operation 'max': 0");
            }
        },
        x -> x.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO));


  private final String name;
  private final Consumer<List<BigDecimal>> validationMethod;
  private final Function<List<BigDecimal>, BigDecimal> calcMethod;

  Operation(String name, Consumer<List<BigDecimal>> validationMethod,
      Function<List<BigDecimal>, BigDecimal> calcMethod) {
    this.name = name;
    this.validationMethod = validationMethod;
    this.calcMethod = calcMethod;
  }

  public String getName() {
    return name;
  }

  public Consumer<List<BigDecimal>> getValidationMethod() {
    return validationMethod;
  }

  public Function<List<BigDecimal>, BigDecimal> getCalcMethod() {
    return calcMethod;
  }

  public static Operation findByName(String name) {
      for (Operation operation : Operation.values()) {
          if (operation.name.equals(name)) {
              return operation;
          }
      }
      return null;
  }
}
