package opencl.mergesort.classic;

import opencl.mergesort.Sorter;

public class ClassicSorter implements Sorter {

  @Override
  public int[] sort(int[] array) {
    return bottomUpSort(array, array.clone());
  }

  private int[] bottomUpSort(int[] itemsToSort, int[] workArray) {
    int numberOfItems = itemsToSort.length;
    /* each 1-element run in itemsToSort is already "sorted". */
    /* Make successively longer sorted runs of length 2, 4, 8, 16... until whole array is sorted*/
    for (int width = 1; width < numberOfItems; width = 2 * width) {
      /* array is full of runs of length width */
      for (int i = 0; i < numberOfItems; i = i + 2 * width) {
        /* merge two areas: itemsToSort[i:i+width-1] and itemsToSort[i+width:i+2*width-1] to workArray[] */
        /*  or copy itemsToSort[i:n-1] to workArray[] ( if(i+width >= n) ) */
        mergeAndSort(itemsToSort, workArray, i, Math.min(i + width, numberOfItems), Math.min(i + 2 * width, numberOfItems));
      }
      /* now workArray[] is full of runs of length 2*width */
      /* swap workArray[] to itemsToSort[] for next iteration */
      int[] help = itemsToSort;
      itemsToSort = workArray;
      workArray = help;
    }
    return itemsToSort;
  }

  private void mergeAndSort(int[] unsorted, int[] result, int startOfLeftRange, int startOfRightRange, int endOfRanges) {
    int leftIndex = startOfLeftRange;
    int rightIndex = startOfRightRange;
    /* while there are elements in the left or right lists */
    for (int i = startOfLeftRange; i < endOfRanges; i++) {
      /* if left list head exists and is <= existing right list head */
      if (leftIndex < startOfRightRange && (rightIndex >= endOfRanges || unsorted[leftIndex] <= unsorted[rightIndex])) {
        result[i] = unsorted[leftIndex];
        leftIndex = leftIndex + 1;
      } else {
        result[i] = unsorted[rightIndex];
        rightIndex = rightIndex + 1;
      }
    }
  }
}

