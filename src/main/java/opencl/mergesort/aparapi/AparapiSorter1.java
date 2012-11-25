package opencl.mergesort.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import opencl.mergesort.Sorter;
import opencl.mergesort.util.Math2;

import static opencl.mergesort.util.Math2.*;

public class AparapiSorter1 implements Sorter {
  @Override
  public int[] sort(int[] array) {
    return bottomUpSort(array);
  }

  private int[] bottomUpSort(int[] itemsToSort) {
    int numberOfItems = itemsToSort.length;
    int numberOfMerges = log2(numberOfItems) + 1;

    for (int i=0; i<numberOfMerges; i++) {
      int width = Math2.power2(i);
      MergeKernel mergeKernel = new MergeKernel(itemsToSort, width);
      Range range = Range.create(calculateNumberOfAreasToMerge(numberOfItems, width));
      mergeKernel.execute(range);

      itemsToSort = mergeKernel.getResult();
    }
    return itemsToSort;
  }

  private static int calculateNumberOfAreasToMerge(int numberOfItems, int width) {
    return numberOfItems / (2 * width) + (numberOfItems % (2 * width) == 0 ? 0 : 1);
  }

  private class MergeKernel extends Kernel {
    private final int[] unsorted;
    private final int[] result;
    private final int width;

    private MergeKernel(int[] unsorted, int width) {
      this.unsorted = new int[unsorted.length];
      System.arraycopy(unsorted, 0, this.unsorted, 0, unsorted.length);
      this.result = new int[unsorted.length];
      this.width = width;
    }

    public int[] getResult() {
      return result;
    }

    @Override
    public void run() {
      int id = getGlobalId();
      // calculate which area of the array to merge, based on the globalId
      int numberOfItems = unsorted.length;
      int startOfLeftRange = width * 2 * id;
      int startOfRightRange = Math.min(startOfLeftRange + width, numberOfItems);
      int endOfRanges = Math.min(startOfLeftRange + 2 * width, numberOfItems);
      mergeAndSort(startOfLeftRange, startOfRightRange, endOfRanges);
    }

    private void mergeAndSort(int startOfLeftRange, int startOfRightRange, int endOfRanges) {
      int leftIndex = startOfLeftRange;
      int rightIndex = startOfRightRange;
      for (int i = startOfLeftRange; i < endOfRanges; i++) {
        if (leftIndex < startOfRightRange && (rightIndex >= endOfRanges || unsorted[leftIndex] <= unsorted[rightIndex])) {
          result[i] = unsorted[leftIndex];    // this apparently triggers the bytecode analyser to copy the array to the buffer
          leftIndex += 1;
        } else {
          result[i] = unsorted[rightIndex];   // idem
          rightIndex += 1;
        }
      }
    }
  }

}
