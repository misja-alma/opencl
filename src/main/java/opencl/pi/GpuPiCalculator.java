package opencl.pi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

public class GpuPiCalculator implements Calculator {

  @Override
  public float calculate(long nrTrials) {
    int nrKernels = 1000;
    int[] results = new int[nrKernels];

    CalculatorKernel kernel = new CalculatorKernel((int)(nrTrials / nrKernels), results, new long[nrKernels]);
    Range range = Range.create(nrKernels);
    kernel.execute(range);

    int totalInCircle = sum(results);
    return 4.0F * totalInCircle / nrTrials;
  }

  private static int sum(int[] array) {
    int result = 0;
    for(int n: array) {
      result += n;
    }
    return result;
  }

  private class CalculatorKernel extends Kernel {
    private final int nrTrials;
    private final int[] results;
    @Local
    private final long[] seeds;

    public CalculatorKernel(int nrTrials, int[] results, long[] seeds) {
      this.nrTrials = nrTrials;
      this.results = results;
      this.seeds = seeds;
    }

    @Override
    public void run() {
      int globalId = getGlobalId();
      seeds[globalId] = globalId + 1;
      int nrInCircle = 0;
      for (int i = 0; i < nrTrials; i++) {
        if(isRandomPointInCircle(globalId)) {
          nrInCircle++;
        }
      }
      results[globalId] = nrInCircle;
    }

    private boolean isRandomPointInCircle(int globalId) {
      float x = 2 * nextRandom(globalId) - 1;
      float y = 2 * nextRandom(globalId) - 1;
      return x * x + y * y < 1;
    }

    private static final long MAX_32BIT = 0xFFFFFFFFL;

    private float nextRandom(int globalId) {
      long seed = seeds[globalId];
      long higherWord = seed >>> 32;
      long lowerWord = seed & MAX_32BIT;
      seeds[globalId] = lowerWord * 4294883355L + higherWord;
      long random32Bit = lowerWord^higherWord & MAX_32BIT;
      return (float)random32Bit / MAX_32BIT;
    }
  }
}
