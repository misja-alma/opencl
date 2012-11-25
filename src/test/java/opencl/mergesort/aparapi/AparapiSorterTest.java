package opencl.mergesort.aparapi;

import opencl.mergesort.Sorter;
import opencl.mergesort.SorterTest;

public class AparapiSorterTest extends SorterTest {
  @Override
  protected Sorter getSorterImplementation() {
    return new AparapiSorter2();
  }
}
