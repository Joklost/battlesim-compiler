import java.util.*;

public class TestCalculator {

	public static void main(String[] args) {
		Scanner kbd = new Scanner(System.in);
		Calculator calc = new Calculator();
		while(true) {
			System.out.print("-> ");
			Float x = kbd.nextFloat();
			Float y = calc.c2f(x);
			System.out.println("" + x + "C = " + y + "F");
			y = calc.f2c(x);
			System.out.println("" + x + "F = " + y + "C");
			System.out.print("quit?(y/n) ");
			String response = kbd.next();
			if (response.equals("y")) break;
		}
		System.out.println("bye");
	}



}
