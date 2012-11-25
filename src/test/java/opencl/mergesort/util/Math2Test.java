package opencl.mergesort.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class Math2Test {
  @Test
  public void testLog2() throws Exception {
    assertEquals(0, Math2.log2(1));
    assertEquals(1, Math2.log2(2));
    assertEquals(2, Math2.log2(4));
    assertEquals(1, Math2.log2(3));
  }

  @Test
  public void testPower2() throws Exception {
    assertEquals(1, Math2.power2(0));
    assertEquals(2, Math2.power2(1));
    assertEquals(4, Math2.power2(2));
  }
}
