package opencl.pi;

public class GpuPiCalculatorTest {

  public static void main(String[] args) {
    long nrOfTrials = 500000000L;
    System.out.println("---------- GPU Calculator:");
    calculatePi(new GpuPiCalculator(), nrOfTrials);
    System.out.println("---------- Normal Calculator:");
    calculatePi(new PiCalculator(), nrOfTrials);
  }

  private static void calculatePi(Calculator calculator, long nrOfTrials) {
    long start = System.currentTimeMillis();
    double pi = calculator.calculate(nrOfTrials);
    long time = System.currentTimeMillis() - start;
    System.out.println("PI: " + pi);
    System.out.println("Time (ms): " + time);
  }
}
