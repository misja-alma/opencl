package opencl.square.classic;

import opencl.square.Squarer;

public class ClassicSquarer implements Squarer {
  public void square(float[] in) {
    for(int i=0; i<in.length; i++) {
      in[i] = in[i] * in[i] * in[i] * in[i];
    }
  }
}
