package opencl.mergesort;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class SorterTest {
  protected abstract Sorter getSorterImplementation();

  @Test
  public void testSort_oneElement() throws Exception {
    int[] list = new int[]{42};

    int[] result = getSorterImplementation().sort(list);

    assertEquals(42, result[0]);
  }

  @Test
  public void testSort_twoElements() throws Exception {
    int[] list = new int[]{42, 41};

    int[] result = getSorterImplementation().sort(list);

    assertEquals(41, result[0]);
    assertEquals(42, result[1]);

    list = new int[]{41, 42};

    result = getSorterImplementation().sort(list);

    assertEquals(41, result[0]);
    assertEquals(42, result[1]);
  }

  @Test
  public void testSort_threeElements() throws Exception {
    int[] list = new int[]{42, 41, 3};

    int[] result = getSorterImplementation().sort(list);

    assertEquals(3, result[0]);
    assertEquals(41, result[1]);
    assertEquals(42, result[2]);
  }

  @Test
  public void testSort_manyElements() throws Exception {
    int[] list = new int[]{1, 2, 3, 6, 5, 9, 11, 7};

    int[] result = getSorterImplementation().sort(list);

    assertEquals(1, result[0]);
    assertEquals(2, result[1]);
    assertEquals(3, result[2]);
    assertEquals(5, result[3]);
    assertEquals(6, result[4]);
    assertEquals(7, result[5]);
    assertEquals(9, result[6]);
    assertEquals(11, result[7]);
  }
}

