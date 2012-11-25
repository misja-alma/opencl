package opencl.mergesort.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import opencl.mergesort.Sorter;

import static opencl.mergesort.util.Math2.log2;


// TODO still slow. Probably because of transferring data to and from gpu all the time.
// we would like to: copy data only once back and forth.
public class AparapiSorter2 implements Sorter {

  @Override
  public int[] sort(int[] array) {
    return bottomUpSort(array);
  }

  private int[] bottomUpSort(int[] itemsToSort) {
    final int[] unsorted = itemsToSort;
    final int[] workArray = new int[unsorted.length];
    final int numberOfItems = itemsToSort.length;
    int numberOfMerges = log2(numberOfItems) + 1;

    Kernel mergeKernel = new Kernel() {

      @Override
      public void run() {
        int id = getGlobalId();
        int passId = getPassId();
        int width = power2(passId);
        int numberOfItemsToMerge = localCalculateNumberOfAreasToMerge(numberOfItems, width);
        if (id > numberOfItemsToMerge - 1) {
          return;
        }

        // calculate which area of the array to merge, based on the globalId
        int startOfLeftRange = width * 2 * id;
        int startOfRightRange = Math.min(startOfLeftRange + width, numberOfItems);
        int endOfRanges = Math.min(startOfLeftRange + 2 * width, numberOfItems);

        // swap source and target array on every new pass
        if (passId % 2 == 0) {
          mergeAndSort(startOfLeftRange, startOfRightRange, endOfRanges, unsorted, workArray);
        } else {
          mergeAndSort(startOfLeftRange, startOfRightRange, endOfRanges, workArray, unsorted);
        }
      }

      private void mergeAndSort(int startOfLeftRange, int startOfRightRange, int endOfRanges, int[] sourceArray, int[] targetArray) {
        int leftIndex = startOfLeftRange;
        int rightIndex = startOfRightRange;
        for (int i = startOfLeftRange; i < endOfRanges; i++) {
          if (leftIndex < startOfRightRange && (rightIndex >= endOfRanges || sourceArray[leftIndex] <= sourceArray[rightIndex])) {
            targetArray[i] = sourceArray[leftIndex];
            leftIndex += 1;
          } else {
            targetArray[i] = sourceArray[rightIndex];
            rightIndex += 1;
          }
        }
      }

      private int power2(int number) {
        int result = 1;
        for(int i=0; i<number; i++) {
          result += result;
        }
        return result;
      }

      private int localCalculateNumberOfAreasToMerge(int numberOfItems, int width) {
        return numberOfItems / (2 * width) + (numberOfItems % (2 * width) == 0 ? 0 : 1);
      }
    };

    int maxAreas = calculateNumberOfAreasToMerge(numberOfItems, 1);

    Range range = Range.create(maxAreas);
    mergeKernel.execute(range, numberOfMerges);

    if (numberOfMerges % 2 == 0) {
      return unsorted;
    } else {
      return workArray;
    }
  }

  private int calculateNumberOfAreasToMerge(int numberOfItems, int width) {
    return numberOfItems / (2 * width) + (numberOfItems % (2 * width) == 0 ? 0 : 1);
  }
}
