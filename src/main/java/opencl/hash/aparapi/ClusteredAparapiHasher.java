package opencl.hash.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import opencl.hash.Hasher;

// TODO a 100 times smaller range leads to a twice as long calculation ... why?
public class ClusteredAparapiHasher implements Hasher {

  public int[] hash(final int[] in) {
    CalcKernel kernel = new CalcKernel(in);
    Range range = Range.create(in.length / 10);
    kernel.execute(range);
    return kernel.getResult();
  }
}

class CalcKernel extends Kernel {
  final int[] out;
  final int[] in;

  public CalcKernel(int[] in) {
    this.in = in;
    out = new int[in.length];
  }

  public void run() {
    int id = getGlobalId();
    int start = id * 10;
    int end = start + 10;
    for(int i=start; i<end; i++) {
      out[i] = longCalculation(in[i]);
    }
  }

  private static int longCalculation(int h) {
    for (int i=0; i<10000; i++) {    // increasing i to 20000 will make the program last forever ..
      h ^= (h >>> 20) ^ (h >>> 12);
      h = h ^ (h >>> 7) ^ (h >>> 4);
    }
    return h;
  }

  public int[] getResult() {
    return out;
  }
}
