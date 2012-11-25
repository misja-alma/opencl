package opencl.square;

import opencl.square.aparapi.AparapiSquarer;
import opencl.square.classic.ClassicSquarer;

import java.util.Random;

public class SquarerPerformanceTest {
  private static Squarer squarer = new AparapiSquarer();

  public static void main(String[] args) {
    float[] items = createItems(500000);

    long startTime = System.currentTimeMillis();
    squarer.square(items);
    long time = System.currentTimeMillis() - startTime;
    System.out.println("Time (ms): " + time);
  }

  private static float[] createItems(int number) {
    float[] result = new float[number];
    Random random = new Random();
    for(int i=0; i<number; i++) {
      result[i] = (float)random.nextDouble();
    }
    return result;
  }
}
