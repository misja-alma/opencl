package opencl.util;

public class RandomCalculator {
  private static final long MAX_32BIT = 0xFFFFFFFFL;
  private long seed;

  public RandomCalculator(long seed) {
    this.seed = seed;
  }

  public double nextDouble() {
    long higherWord = seed >>> 32;
    long lowerWord = seed & MAX_32BIT;
    seed  = lowerWord * 4294883355L + higherWord;
    long random32Bit = lowerWord^higherWord & MAX_32BIT;
    return (double)random32Bit / MAX_32BIT;
  }
}
