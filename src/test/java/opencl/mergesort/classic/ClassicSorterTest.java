package opencl.mergesort.classic;

import opencl.mergesort.Sorter;
import opencl.mergesort.SorterTest;

public class ClassicSorterTest extends SorterTest {
  @Override
  protected Sorter getSorterImplementation() {
    return new ClassicSorter();
  }
}
