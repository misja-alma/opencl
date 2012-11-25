package opencl.square.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import opencl.square.Squarer;

public class AparapiSquarer implements Squarer {
  public void square(final float[] in) {
    Kernel kernel = new Kernel() {
      public void run() {
        int id = getGlobalId();
        in[id] = in[id] * in[id] * in[id] * in[id];
      }
    };
    Range range = Range.create(in.length);
    kernel.execute(range);
  }
}

