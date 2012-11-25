package opencl.mergesort;

import opencl.mergesort.aparapi.AparapiSorter1;
import opencl.mergesort.aparapi.AparapiSorter2;

import java.util.Random;

public class SorterPerformanceTest {
  private static Sorter sorter = new AparapiSorter2();

  public static void main(String[] args) {
   int[] items = createItems(500000);

    long startTime = System.currentTimeMillis();
    int[] sortedItems = sorter.sort(items);
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
