package opencl.util;

import org.junit.Test;

public class RandomCalculatorTest {
  private RandomCalculator randomCalculator;

  @Test
  public void testSeed1() {
    randomCalculator = new RandomCalculator(1);

    for(int i=0; i<100; i++) {
      System.out.println(randomCalculator.nextDouble());
    }
  }
}
