package opencl.hash.aparapi;

import opencl.hash.Hasher;
import opencl.hash.classic.ClassicHasher;

import java.util.Random;

public class HasherPerformanceTest {
  private static Hasher hasher = new ClusteredAparapiHasher();

  public static void main(String[] args) {
    int[] items = createItems(50000);

    long startTime = System.currentTimeMillis();
    int[] sortedItems = hasher.hash(items);
    long time = System.currentTimeMillis() - startTime;
    System.out.println("Time (ms): " + time);
  }

  private static int[] createItems(int number) {
    int[] result = new int[number];
    Random random = new Random();
    for(int i=0; i<number; i++) {
      result[i] = random.nextInt();
    }
    return result;
  }
}
