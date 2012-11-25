package opencl.hash.aparapi;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import opencl.hash.Hasher;

// TODO the range is too big! Shouldn't be bigger than nr of gpu's
// TODO find out why increasing the hash iterations 'hangs' the program, also increasing the size of the input array,
//      and, also doing work in subsets, as suggested above!
public class AparapiHasher implements Hasher {

  public int[] hash(final int[] in) {
    CalcKernel kernel = new CalcKernel(in);
    Range range = Range.create(in.length);
    kernel.execute(range);
    return kernel.getResult();
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
      out[id] = longCalculation(in[id]);
    }

    private int longCalculation(int h) {
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
}
