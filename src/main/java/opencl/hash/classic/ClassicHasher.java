package opencl.hash.classic;

import opencl.hash.Hasher;

// TODO run in 4 (2?) threads
public class ClassicHasher implements Hasher {
  public int[] hash(int[] in) {
    int[] result = new int[in.length];
    for(int i=0; i<in.length; i++) {
      result[i] = doHash(in[i]);
    }
    return result;
  }


  private int doHash(int h) {
    for (int i=0; i<10000; i++) {
      h ^= (h >>> 20) ^ (h >>> 12);
      h = h ^ (h >>> 7) ^ (h >>> 4);
    }
    return h;
  }
}
