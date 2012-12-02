package opencl.pi;

import opencl.util.RandomCalculator;

public class PiCalculator implements Calculator {

  @Override
  public float calculate(long nrTrials) {
    RandomCalculator random = new RandomCalculator(1);
    int nrInCircle = 0;
    for(long i=0; i<nrTrials; i++) {
      if(isRandomPointInCircle(random)) {
        nrInCircle++;
      }
    }
    return 4.0F * nrInCircle / nrTrials;
  }

  private boolean isRandomPointInCircle(RandomCalculator randomGenerator) {
    double x = 2 * randomGenerator.nextDouble() - 1;
    double y = 2 * randomGenerator.nextDouble() - 1;
    return x * x + y * y < 1;
  }
}
