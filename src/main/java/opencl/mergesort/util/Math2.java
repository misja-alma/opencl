package opencl.mergesort.util;

public class Math2 {
  public static int log2(int number) {
    int result = 0;
    while(number > 1) {
      number >>>= 1;
      result ++;
    }
    return result;
  }

  public static int power2(int number) {
    int result = 1;
    for(int i=0; i<number; i++) {
      result += result;
    }
    return result;
  }
}
