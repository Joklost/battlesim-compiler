import java.util.*;

public class TestAccount {

	public static void main(String[] args) {
		Scanner kbd = new Scanner(System.in);
		Account savings = new Account();
		while(true) {
			System.out.print("-> ");
			Double amt = kbd.nextDouble();

			savings.deposit(amt);
			System.out.println("balance = $" + savings.getBalance());

			// quit?
			System.out.print("quit?(y/n) ");
			String response = kbd.next();
			if (response.equals("y")) break;
		}
		System.out.println("bye");
	}



}
