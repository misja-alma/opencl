package opencl.hash;

// TODO A 'embarassingly parallel' problem
public interface Hasher {
  int[] hash(int[] source);
}
