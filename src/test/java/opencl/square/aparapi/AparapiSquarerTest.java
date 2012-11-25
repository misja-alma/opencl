package opencl.square.aparapi;

import opencl.square.classic.ClassicSquarer;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class AparapiSquarerTest {
  private AparapiSquarer squarer = new AparapiSquarer();

  @Test
  public void testSquare() throws Exception {
    float[] in = {1.0f, 1.5f, 2.0f};

    squarer.square(in);

    assertEquals(1.0f, in[0], 0.001);
    assertEquals(2.25f * 2.25f, in[1], 0.001);
    assertEquals(16.0f, in[2], 0.001);
  }
}
