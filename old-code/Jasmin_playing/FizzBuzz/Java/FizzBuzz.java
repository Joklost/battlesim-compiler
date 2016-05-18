public class FizzBuzz {
    public static void main(String args[]) {
        int i;
        for(i = 1; i < 25+1; i++) {
        	if (i % 3 == 0 && i % 5 != 0)
            	System.out.println("fizz");
            else if (i % 3 != 0 && i % 5 == 0)
            	System.out.println("buzz");
            else if (i % 3 == 0 && i % 5 == 0)
            	System.out.println("fizzbuzz");
            else
            	System.out.println(i);
        }
    }
}