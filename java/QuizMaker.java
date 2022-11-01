import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class QuizMaker {
	final static String DIRECTORY = ""; //dinelete ko since my own directory ang meron dito (11/1/22)
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException {
		String quizName;
		int chose = -1;
		System.out.println(
				  "Welcome to Create Your Own Quiz!\r\n"
				+ "(1) - Create a Quiz\r\n"
				+ "(2) - Take a Quiz");
		while(chose != 1 || chose != 2) {
			System.out.print("Input your choice: ");
			chose = input.nextInt();
			if (chose == 1) {
				System.out.print("Enter your quiz name: ");
				input.nextLine();
				quizName = input.nextLine();
				quizMaker(quizName);
				main(args);
			}
			else if (chose == 2) {
				quizReader();
				main(args);
			}
			else
				System.out.println("Invalid Input!");
		}
	}
	
	public static void quizMaker(String quizName) throws IOException {
		File quizText = new File(DIRECTORY + "\\" + quizName + ".txt");
		quizText.createNewFile();
		PrintWriter printer = new PrintWriter(quizText);
		int totalQuestions;
		String question;
		String aChoice;
		String bChoice;
		String cChoice;
		String dChoice;
		char answer;
		String choiceGiven;
		
		System.out.print("How many Questions would you like to create: ");
		while(true) {
			String questions = input.nextLine();
			if (digitsOnly(questions)) {
				totalQuestions = Integer.parseInt(questions);
				break;
			}
			else
				System.out.println("Digits only!");
		}
		
		
		for (int i = 0; i < totalQuestions; i++) {
			System.out.print("Enter your (" + (i+1) + ") question: ");
			question = input.nextLine();
			
			System.out.print("Enter the first choice (a): ");
			aChoice = input.nextLine();
			
			System.out.print("Enter the first choice (b): ");
			bChoice = input.nextLine();
			
			System.out.print("Enter the first choice (c): ");
			cChoice = input.nextLine();
			
			System.out.print("Enter the first choice (d): ");
			dChoice = input.nextLine();
			
			System.out.print("Enter the letter of the correct answer (a,b,c,d): ");
			while(true) {
				choiceGiven = input.nextLine();
				if (ad(choiceGiven)) {
					answer = choiceGiven.toLowerCase().charAt(0);
					break;
				}
				else
					System.out.println("Invalid Input!");
			}
			
			printer.println((i+1) + ".) " + question);
			printer.println("a.) " + aChoice);
			printer.println("b.) " + bChoice);
			printer.println("c.) " + cChoice);
			printer.println("d.) " + dChoice);
			printer.println("Answer: " + answer);
		}
		printer.close();
	}
	
	public static void quizReader() throws FileNotFoundException {
		boolean chosen = true;
		int numPick = 0;
		File file = new File(DIRECTORY);
		String[] arrayFile = file.list();
		System.out.println("Choose a quiz:");
		
		for (int i = 0; i < arrayFile.length; i++) {
			System.out.println((i+1) + ".) " + arrayFile[i]);
		}
		
		System.out.print("Choose the number of the quiz you want to take: ");
		while(chosen) {
			numPick = input.nextInt();
			if (numPick >= 1 && numPick <= arrayFile.length)
				chosen = false;
			else
				System.out.println("Invalid input!");
		}
		
		textQuizReader(new File(DIRECTORY + "\\" + arrayFile[numPick-1]));
	}
	
	public static boolean digitsOnly(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) >= '0' && text.charAt(i) <= '9')
				continue;
			else
				return false;
		}
		return true;
	}

	public static boolean ad(String text) {
		text = text.toLowerCase();
		if (text.charAt(0) >= 'a' && text.charAt(0) <= 'd')
			return true;
		else
			return false;
	}

	public static void textQuizReader(File quizName) throws FileNotFoundException {
		int totalCorrect = 0;
		int totalQuestion = 0;
		String listAnswer;
		char answer;
		String choiceGiven;
		Scanner textReader = new Scanner(quizName);
		while(textReader.hasNext()) {
			for (int i = 0; i < 5; i++) {
				if (i == 0) {
					totalQuestion++;
					System.out.print("Question ");
				}
				System.out.println(textReader.nextLine());
			}
			
			while(true) {
				choiceGiven = input.next();
				if (ad(choiceGiven)) {
					answer = choiceGiven.toLowerCase().charAt(0);
					break;
				}
				else
					System.out.println("Invalid Input!");
			}
			
			listAnswer = textReader.nextLine();
			
			if (answer == listAnswer.charAt(8)) {
				totalCorrect++;
			}
		}
		
		System.out.println("You got " + totalCorrect + "/" + totalQuestion + "!");
	}
}
