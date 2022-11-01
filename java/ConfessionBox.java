import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * How to use Confession Box (to confess for your sins duh!)
 * each one of the sinners must have their own private Prayer Letter
 * prayer letter must only contain lowercases a - z and spacebar,
 * no special letters are allowed inside the Prayer Letter
 * 
 * For Confession: (third arguement must be -c)
 * a sinner will use the directory to put where they placed their confession
 * (ex /Files/ihavesinned.txt) or (mymistakes.txt) are possible answers for it.
 * the box will convert your words into uneligable letters unless your the prayers
 * only you have are on it, otherwise its impossible to be read out
 * 
 * For Praying: (third arguement must be -p)
 * a sinner will use the directy to put where they have placed their confession_n.dat file
 * (ex /Files/Confession_5.dat) or (Confession_5.dat) are possible answers for it.
 * the box will convert the uneligable letters into your written words using the prayers 
 * you provided ot it, otherwise the decode is nonsense
 */

public class ConfessionBox {
	static String prayerLetter; // should be a directory
	static String directory;
	static Scanner input = new Scanner(System.in);
	static String indexes = "-";
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Must be used with {PrayerLetter} {Directory} -p/c!");
			System.exit(1);
		}
		
		prayerLetter = args[0];
		directory = args[1];
		
		if (!new File(prayerLetter).exists()) {
			System.out.println("You have no prayer letter!");
			System.exit(1);
		}

		char choice = args[2].charAt(1);
		
		try {
		if (choice == 'p')
			pray();
		else if (choice == 'c')
			confess();
		}
		catch (FileNotFoundException ex) {
			System.out.println("Confession not found!");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void pray() throws FileNotFoundException{
		Scanner r = new Scanner(new File(directory));
		ArrayList<Integer> Indexes = getIndexArray(r.next());
		String prayerString = getPrayerString(new File(prayerLetter));
		if (prayerString.length() == 0) {
			System.out.println("Your prayers are empty!");
			System.exit(1);
		}
		int letterIndex = 0;
		int prayerIndex = 0;
		int spaceTime = 0;
		int letterNum = 0;
		String output = "";
		
		if (!new File(directory).exists()) {
			System.out.println("Confession not found!");
			System.exit(1);
		}
		while (r.hasNext()) {
			String word = r.next();
			
			if (spaceTime >= 25) {
				spaceTime = 0;
				output = output + "\n";
			}
			
			for (int i = 0; i < word.length(); i++) {
				if (prayerIndex >= prayerString.length())
					prayerIndex = 0;
				letterIndex++;
				letterNum = letterToNum(word.charAt(i));
				if (Indexes.size() != 0) {
					if (letterIndex == Indexes.get(0)) {
						Indexes.remove(0);
						letterNum += 25;
					}
				}
				letterNum -= (letterToNum(prayerString.charAt(prayerIndex)));
				
				output += numToLetter(letterNum, letterIndex);
				letterNum = 0;
				spaceTime++;
				prayerIndex++;
			}
			
			output += " ";
		}
		
		System.out.println("Your prayer has been restored.\n");
		System.out.println(output);
		
		r.close();
	}
	
	public static ArrayList<Integer> getIndexArray(String s) {
		ArrayList<Integer> indexes = new ArrayList<>();
		// yawaa aning code diri oy piste im so sorry 
		int k = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '-')
				continue;
			k = i;
			while (true) {
				if (s.charAt(k) == '-')
					break;
				k++;
			}
			indexes.add(Integer.parseInt(s.substring(i,k)));
			i = k;
		}
		
		return indexes;
	}
	
	public static void confess() throws IOException {
		int n = 1;
		while (true) {
			if (new File("Confession_" + n + ".dat").exists())
				n++;
			else
				break;
		}
		
		Scanner r = new Scanner(new File(directory));
		String prayerString = getPrayerString(new File(prayerLetter));
		
		if (prayerString.length() == 0) {
			System.out.println("Your prayers are empty!");
			System.exit(1);
		}
		
		int letterIndex = 0;
		int letterNum = 0;
		int prayerIndex = 0;
		int spaceTime = 0;
		
		String output = "";
		while (r.hasNext()) {
			String word = r.next();
			
			if (spaceTime >= 25) {
				spaceTime = 0;
				output = output + "\n";
			}
			
			for (int i = 0; i < word.length(); i++) {
				if (prayerIndex >= prayerString.length())
					prayerIndex = 0;
				
				letterIndex++;
				letterNum = letterToNum(word.charAt(i));
				letterNum += (letterToNum(prayerString.charAt(prayerIndex)));
				
				output += numToLetter(letterNum, letterIndex);
				letterNum = 0;
				spaceTime++;
				prayerIndex++;
			}
			
			output += " ";
		}
		

		FileWriter confess = new FileWriter(new File("Confession_"+n+".dat"));
		confess.write(indexes + "\n" + output);
		confess.close();
		r.close();
		System.out.println("Your confession has been stored.");
	}
	
	public static int letterCount(File dir) throws FileNotFoundException {
		int n = 0;
		// counts all the total letters
		
		Scanner r = new Scanner(dir);
		
		while (r.hasNext()) {
			n += r.next().length();
		}
		
		r.close();
		
		return n;
	}

	public static String getPrayerString(File dir) throws FileNotFoundException{
		String output = "";
		Scanner r = new Scanner(dir);
		while (r.hasNext()) {
			output += r.next();
		}
		
		r.close();
		return output;
	}
	
	public static int letterToNum(char a) {
		if (a <= 'a' && a >= 'z') {
			System.out.println("Your letters are all wrong! Repent!");
			System.exit(1);
		}
		
		return a - 'a';
	}
	
	public static char numToLetter(int i, int index) {
		if (i > 25) {
			i = i % 25;
			indexes += index + "-";
		}
		
		return (char) ('a' + i);
	}
}
