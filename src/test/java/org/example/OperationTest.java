package org.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OperationTest {

  @Test
  public void validationSuccess() {
    Map<Operation, List<BigDecimal>> correctNumbersByOperation = new EnumMap<>(Operation.class);
    correctNumbersByOperation.put(Operation.SUM, Collections.emptyList());
    correctNumbersByOperation.put(Operation.MULTIPLY, Collections.singletonList(BigDecimal.ONE));
    correctNumbersByOperation.put(Operation.MAX, Collections.singletonList(BigDecimal.ONE));

    List<BigDecimal> multiplySumNumbers = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE,
        BigDecimal.ONE);
    correctNumbersByOperation.put(Operation.MULTIPLY_SUM, multiplySumNumbers);

    for (Map.Entry<Operation, List<BigDecimal>> entry : correctNumbersByOperation.entrySet()) {
      entry.getKey().getValidationMethod().accept(entry.getValue());
    }
  }

  @Test
  public void validationFails() {
    Map<Operation, List<BigDecimal>> correctNumbersByOperation = new EnumMap<>(Operation.class);
    correctNumbersByOperation.put(Operation.MULTIPLY, Collections.emptyList());
    correctNumbersByOperation.put(Operation.MAX, Collections.emptyList());

    List<BigDecimal> multiplySumNumbers = Arrays.asList(BigDecimal.ZERO, BigDecimal.ONE);
    correctNumbersByOperation.put(Operation.MULTIPLY_SUM, multiplySumNumbers);

    for (Map.Entry<Operation, List<BigDecimal>> entry : correctNumbersByOperation.entrySet()) {
      try {
        entry.getKey().getValidationMethod().accept(entry.getValue());
      } catch (ValidationException ex) {
        continue;
      }
      Assert.fail();
    }
  }

  @Test
  public void testMultiplyOperation() {
    Assert.assertEquals(BigDecimal.valueOf(20),
        Operation.MULTIPLY.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.valueOf(5), BigDecimal.valueOf(4))));
    Assert.assertEquals(BigDecimal.ZERO,
        Operation.MULTIPLY.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.ZERO, BigDecimal.valueOf(4))));
  }

  @Test
  public void testSumOperation() {
    Assert.assertEquals(BigDecimal.valueOf(20),
        Operation.SUM.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.valueOf(5), BigDecimal.valueOf(4),
                BigDecimal.valueOf(11))));
    Assert.assertEquals(BigDecimal.ZERO,
        Operation.SUM.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.valueOf(4), BigDecimal.valueOf(-4))));
  }

  @Test
  public void testMultiplySumOperation() {
    Assert.assertEquals(BigDecimal.valueOf(25),
        Operation.MULTIPLY_SUM.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.valueOf(5), BigDecimal.valueOf(4),
                BigDecimal.valueOf(5))));
    Assert.assertEquals(BigDecimal.ZERO,
        Operation.MULTIPLY_SUM.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.ZERO, BigDecimal.valueOf(4), BigDecimal.ZERO)));
  }

  @Test
  public void testMaxOperation() {
    Assert.assertEquals(BigDecimal.valueOf(1),
        Operation.MAX.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.ZERO, BigDecimal.valueOf(-1), BigDecimal.valueOf(1))));
    Assert.assertEquals(BigDecimal.valueOf(1e10),
        Operation.MAX.getCalcMethod()
            .apply(Arrays.asList(BigDecimal.valueOf(1), BigDecimal.valueOf(1e10))));
  }
}