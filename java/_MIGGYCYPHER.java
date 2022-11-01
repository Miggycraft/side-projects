import java.util.Random;
import java.util.Scanner;

public class _MIGGYCIPHER {
public static Scanner input = new Scanner(System.in);
public static int subIndex = 0;
public static int memory = 0;
public static int currValue = 0;
public static String output = "";

	public static void main(String[] args) {
		System.out.println("Enter a String of Text: ");
		String text = input.nextLine();
		text = text.replaceAll("\\s+",""); //clears whitespace
		
		if (multipleOfFive(text)) {
			for (int i = 0; i < text.length() / 5; i++) {
				output += reader(text) + "";
				subIndex++;
			}
			
			System.out.println(output);
		}
		
		else {
			System.out.println("Invalid String!");
			main(args);
		}
	}
	
	public static String reader(String text) {
		currValue = 0;
		int index = subIndex * 5;

		currValue += might(text.charAt(index++));
		currValue += itWill(text.charAt(index++));
		
		currValue += memory;
		gonna(text.charAt(index++));
		
		index++;
		currValue += yeets(text.charAt(index--));
		currValue += goingTo(text.charAt(index));

		return toText(currValue);
	}
	
	public static int textSwap(char x) {
		if (x >= 'a' && x <= 'z') {
			return (x -'a') + 0;
		}
		else if (x >= 'A' && x <= 'Z') {
			return (x - 'A') + 26;
		}
		
		else {
			return x + 4;
		}
	}
	
	public static int might(char x) {
		Random random = new Random();
		boolean accepts = random.nextBoolean();
		
		if (x == 'M' || x == 'm') {
			return 0;
		}
		
		if (accepts)
			return textSwap(x);
		else
			return 0;
	}

	public static int itWill(char x) {
		if (x == 'I' || x == 'i') {
			return 0;
		}
		
		return textSwap(x);
	}
	
	public static void gonna(char x) {
		if (x == 'G' || x == 'g')
			x = 0;
		
		memory = textSwap(x);
	}
	
	public static int goingTo(char x) {
		int y = textSwap(x);
		
		if (x == 'G' || x == 'g') {
			return 0;
		}
		
		if (currValue + y > 37)
			return 0;
		else
			return y;
	}
	
	public static int yeets (char x) {
		
		if (x == 'Y' || x == 'y') {
			return 0;
		}
		
		return -(textSwap(x));
	}

	public static String toText(int x) {
		if (x > 36 || x < 0)
			x = overload(x);
		
		if (x >= 0 && x <= 9)
			return x + "";
		else if (x == 36)
			return " ";
		else
			return (char)((x - 10) + 'a') + "";
	}
	
	public static boolean multipleOfFive(String text) {
		if (text.length() % 5 != 0)
			return false;
		else
			return true;
	}

	public static int overload(int x) {
		if (x < 0) {
			while (x < 0) {
				x += 37;
			}
		}
		
		if (x > 36) {
			while (x > 36) {
				x -= 37;
			}
		}
		return x;
	}
}