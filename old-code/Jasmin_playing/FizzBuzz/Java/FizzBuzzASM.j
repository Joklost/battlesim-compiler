Compiled from "FizzBuzz.java"
public class FizzBuzz {
  public FizzBuzz();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_1
       1: istore_1
       2: iload_1
       3: bipush        26
       5: if_icmpge     90
       8: iload_1
       9: iconst_3
      10: irem
      11: ifne          31
      14: iload_1
      15: iconst_5
      16: irem
      17: ifeq          31
      20: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      23: ldc           #3                  // String fizz
      25: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      28: goto          84
      31: iload_1
      32: iconst_3
      33: irem
      34: ifeq          54
      37: iload_1
      38: iconst_5
      39: irem
      40: ifne          54
      43: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      46: ldc           #5                  // String buzz
      48: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      51: goto          84
      54: iload_1
      55: iconst_3
      56: irem
      57: ifne          77
      60: iload_1
      61: iconst_5
      62: irem
      63: ifne          77
      66: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      69: ldc           #6                  // String fizzbuzz
      71: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      74: goto          84
      77: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      80: iload_1
      81: invokevirtual #7                  // Method java/io/PrintStream.println:(I)V
      84: iinc          1, 1
      87: goto          2
      90: return
}
