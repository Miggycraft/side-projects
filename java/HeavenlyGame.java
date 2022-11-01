import java.util.Scanner;
public class HeavenlyGame { // program is made in Jan 25, 2021 (1.5 hrs) modified jan 26, 2021 (1 hour)
	public static Scanner input = new Scanner(System.in);
	public static int Day, visited, Temp = 0;
	public static double LP = 0;
	public static int Puff, Lesser, Great = 0; // inventories
	public static int ManChoice, Items, ManHan = 0; // Man Encounters
	public static String ManCounter = "|===============================|\n" +
			"|(1) Help (2) Use Items (3) Flee|\n" +
            "|===============================|\n"	+
			"Type (1 - 3) to declare a choice, anything else forces you to flee\n";
	public static void main(String[] args) {
		tutorial();
	}
	public static void tutorial() {
		System.out.println("You have entered the limbo!");
		System.out.println("I am your archangel, Miggy, here to guide you through your paths");
		System.out.println("Make your good deads before the days ends");
		System.out.println("Before I let you go, have you subscribed to Miggy9 in your previous life?");
		System.out.println("(Y/N)");
		String choice = input.next();
		if(choice.equalsIgnoreCase("Y")) {
			System.out.println("Good, I will give you 50 LP for being subscribing to him");
			LP += 50;
		}
		else
			System.out.println("Shame, I would've given you 50 LP if you did");
		System.out.println("Off to the pit you go!");
		menu();
	}
	public static void menu() {
		System.out.println("\t\t\t|============THE PITS============|");
		System.out.println("\t\t\t|(1) - Check Current LP & Day    |");
		System.out.println("\t\t\t|(2) - Listen to the words of God|");
		System.out.println("\t\t\t| -  -  - (Once per day only)    |");
		System.out.println("\t\t\t|(3) - Encounter a stranger      |");
		System.out.println("\t\t\t|(4) - Beg for forgiveness       |");
		System.out.println("\t\t\t|(5) - Enter the Heavenly Shop   |");
		System.out.println("\t\t\t|(6) - Enter the gates of Heaven |");
		System.out.println("\t\t\t|(7) - Heaven's Wheel            |");
		System.out.println("\t\t\t|================================|");
		System.out.println("Type (1 - 7) to declare a choice, anything else shows this menu again");
		choicer();
	}
	public static void choicer() {
		if (Day == 7){ 
			System.out.println("The Day is already 7! You don't have enough LP to reach heaven");
			System.out.println("You have failed the game.");
			System.exit(1);
		}
		int choice = input.nextInt();
		switch(choice) {
		case 1: // Check Current LP & Day
			System.out.println("Currently, you have " + LP + " LP! (Langit Points) \n The current day is " + Day + "!"); choicer(); break;
		case 2: // Listen to the words of God
			switch(Day) {
				case 0: 
					if (visited != 1) {
						System.out.println("As god once said \" You need 400 Langit Points to enter heaven!! \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 1: 
					if (visited != 1) {
						System.out.println("As god once said \" Encountering a stranger could net you LP! \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 2: 
					if (visited != 1) {
						System.out.println("As god once said \" When Day 7 arrives, your days will end!! \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 3: 
					if (visited != 1) {
						System.out.println("As god once said \" You can use items in your inventory during an encounter! \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 4:
					if (visited != 1) {
						System.out.println("As god once said \" You are luckier when the day is odd \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 5: 
					if (visited != 1) {
						System.out.println("As god once said \" You will spend whole day when you beg for forgiveness!! \""); visited =1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Visit tomorrow if you want to learn more"); choicer(); break;
					}
				case 6: 
					if (visited != 1) {
						System.out.println("As god once said \" The end is near! You must climb today! \""); visited = 1; choicer(); break;
					}
					else {
						System.out.println("You have listened for today! Dont Visit tomorrow if you want to learn more"); choicer(); break;
					}
			}
		case 3: // Encounter a Stranger
			encounter(); break;
		case 4: // Beg for Forgiveness
			Temp = 0;
			Temp = (int)((Math.random() * 50));
			LP += Temp;
			System.out.println("You have begged for forgiveness! God gave you " + Temp + " LP!");
			visited = 0; Day +=1; menu(); break;
		case 5: // Enter the heavenly shop
			HeavenlyShop(); break;
		case 6: // Enter the gates of heaven
			if(LP >= 400) {
				System.out.println("You have ascended and finished the game, Thank you for playing!"); 
				System.exit(1); break;
			}
		else {
			System.out.println("You wasted your whole day to climb to the gates of heaven");
			System.out.println("Yet, you dont have enough LP, You went back");
			Day +=1; visited =0; menu(); break;
		}
		case 7: // heaven's wheel, days even = luckier, odd = normal
				System.out.println("Welcome to the heaven's wheel!");
		String Wheel = "    , - ~ ~ ~ - ,\r\n"
				+ "     ,      X = 1.5    ' ,\r\n"
				+ "   ,                      ,\r\n"
				+ "  ,                        ,\r\n"
				+ " ,                          ,\r\n"
				+ " , X = 2          X = 2.5   ,\r\n"
				+ " ,                          ,\r\n"
				+ "  ,                         ,\r\n"
				+ "   ,          X = 3        ,\r\n"
				+ "     ,                  , '\r\n"
				+ "       ' - , __ _ ,  '";  
		System.out.println(Wheel);
		HeavenWheel(); break;
		default: menu(); // random
		}
	}
	public static void encounter() {
	String Man = "      ////^\\\\\\\\\r\n"
			+ "      | ^   ^ |\r\n"
			+ "     @ (o) (o) @\r\n"
			+ "      |   <   |\r\n"
			+ "      |  ___  |\r\n"
			+ "       \\_____/\r\n"
			+ "     ____|  |____\r\n"
			+ "    /    \\__/    \\\r\n"
			+ "   /              \\\r\n"
			+ "  /\\_/|        |\\_/\\\r\n"
			+ " / /  |        |  \\ \\\r\n"
			+ "( <   |        |   > )\r\n"
			+ " \\ \\  |        |  / /\r\n"
			+ "  \\ \\ |________| / /";
		int EnemyEncounter = (int)(Math.random() * 6);
		switch(EnemyEncounter) {
		case 0: // - 25 man
			System.out.println(Man);
			ManHan = 0;
			ManEncounter(); break;
		case 1: // - 50 man
			System.out.println(Man);
			ManHan = 1;
			ManEncounter(); break;
		case 2: // - 100 man
			System.out.println(Man);
			ManHan = 2;
			ManEncounter(); break;
		case 3: // + 50 man
			System.out.println(Man);
			ManHan = 3;
			ManEncounter(); break;
		case 4: // + 100 man
			System.out.println(Man);
			ManHan = 4;
			ManEncounter(); break;
		case 5: // + 200 man
			System.out.println(Man);
			ManHan = 5;
			ManEncounter(); break;
		}
	}
	public static void ManEncounter() {	
		System.out.println(ManCounter);
		int choice = input.nextInt();
		switch (choice) {
		case 1: 
			switch (ManHan) {
			case 0: 
				System.out.println("You tried to help him but he is puff evil! -25 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP -= 25; menu(); break;
			case 1:
				System.out.println("You tried to help him but he is lesser evil! -50 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP -= 50; menu(); break;
			case 2:
				System.out.println("You tried to help him but he is great evil! -100 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP -= 100; menu(); break;
			case 3:
				System.out.println("You tried to help him and he is puff good! +50 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP += 50; menu(); break;
			case 4:
				System.out.println("You tried to help him and he is lesser good! +100 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP += 100; menu(); break;
			case 5:
				System.out.println("You tried to help him and he is great good! +200 LP");
				System.out.println("A day has passed!"); Day += 1; visited = 0;
				LP += 200; menu(); break;
			}
		case 2:
			String ItemMenu = "|==================================================|\n" +
					   "(1)Puff = " + Puff + " (2)Lesser = " + Lesser + " (3) Great = "  + Great + " (4) exit \n" +
					  "|=================================================|\n" +
					   "Type (1 - 3) to declare a choice, anything else forces you to exit\n";
			System.out.println(ItemMenu);
			int itemz = input.nextInt();
			switch (itemz) {
			case 1: //Puff 1/4
				if(Puff >= 1) {
					Puff -= 1;
					int Puffx = ((int)(Math.random() * 4));
					System.out.println((Puffx == 3) ? "it is " + ((ManHan >= 3) ? "Good" : "Bad") : "idk" );
				}
				else System.out.println("You dont have Puff Soul!"); ManEncounter(); break;
			case 2: //Lesser 1/2
				if(Lesser >= 1) {
					Lesser -= 1;
					int Lesserx = ((int)(Math.random() * 2));
					System.out.println((Lesserx == 1) ? "it is " + ((ManHan >= 3) ? "Good" : "Bad") : "idk" );
				}
				else System.out.println("You dont have Puff Soul!"); ManEncounter(); break;
			case 3: //Great 1
				if(Great >= 1) {
					Great -= 1;
					System.out.println("it is " + ((ManHan >= 3) ? "Good" : "Bad"));
				}
				else System.out.println("You dont have Puff Soul!"); ManEncounter(); break;
			default: ManEncounter(); break;
			}
			
		default: System.out.println("You have fled the area!"); menu(); break;
		}
	}
	public static void HeavenlyShop() {
		System.out.println("\t\t\t|========THE HEAVENLY SHOP=======|");
		System.out.println("\t\t\t|(1) 10 LP: Puff Soul            |");
		System.out.println("\t\t\t|(2) 25 LP: Lesser Soul          |");
		System.out.println("\t\t\t|(3) 50 LP: Great Soul           |");
		System.out.println("\t\t\t|(4) Talk to the shop keeper     |");
		System.out.println("\t\t\t|(5) Check Inventory             |");
		System.out.println("\t\t\t|(6) Leave                       |");
		System.out.println("\t\t\t|================================|");
		String ItemMenu = 
				"|==============ITEM MENU===============|\n" +
			    "Puff = " + Puff + " Lesser = " + Lesser + " Great = "  + Great + "\n" +
			    "|======================================|\n";
		int asked = 0;
		asked = input.nextInt();
		switch(asked) {
			case 1: // 10 lp puff soul 1/4
				if(LP >= 10) {
					System.out.println("You have purchased a Puff soul!");
					LP -= 10; Puff += 1; HeavenlyShop(); break;
				}
				else
					System.out.println("You don't have enough LP!"); HeavenlyShop(); break;
			case 2: // 25 lp lesser soul 1/2
				if(LP >= 25) {
					System.out.println("You have purchased a Lesser soul!");
					LP -= 25; Lesser += 1; HeavenlyShop(); break;
				}
				else
					System.out.println("You don't have enough LP!"); HeavenlyShop(); break;
			case 3: // 50 lp great soul 1
				if(LP >= 50) {
					System.out.println("You have purchased a Great soul!");
					LP -= 50; Great += 1; HeavenlyShop(); break;
				}
				else
					System.out.println("You don't have enough LP!"); HeavenlyShop(); break;
			case 4: // talk to the shop keeper
				System.out.println("The soul can be used to identify the person you encounter");
				System.out.println("Puff soul = 25% to identify the person you encounter");
				System.out.println("Lesser soul = 50% to identify the person you encounter");
				System.out.println("Great soul = 100% to identify the person you encounter");
				HeavenlyShop(); break;
			case 5: // check inventory
				System.out.println(ItemMenu); HeavenlyShop(); break;
			default: menu(); break;
			}
		
	}
	public static void HeavenWheel() { // heaven's wheel game
		System.out.println("=======================");
		System.out.println("What do you want to do?");
		System.out.println("(1) Play the Game      ");
		System.out.println("(2) Ask for the rules  ");
		System.out.println("(3) Leave              ");
		System.out.println("=======================");
		int choice = input.nextInt();
		switch(choice) {
		case 1: 
			System.out.println("How much LP would you bet? You currently have " + LP + " LP.");
			int Bet = input.nextInt();
			if(Bet > LP) {
				System.out.println("You don't have enough LP!"); HeavenWheel(); break;
			}
			else {
					System.out.println("What multiplier would you like to play?\r\n"
						+ "======================================\r\n"
						+ "(1) 1.5 (2) 2 (3) 2.5 (4) 3 (5) Leave\r\n"
						+ "======================================");
					int Multiplier = input.nextInt();
					switch(Multiplier) {
					case 1: // 1.5
						if (Day % 2 == 0)  { // even 1,5 = 1/3, 2 = 1/4, 2.5 = 1/5 3 = 1/6
							int lotto = (int)(Math.random() * 2);
							if(lotto == 0) {
								Bet *= 1.5;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP) + " LP"); HeavenWheel(); break;
							}
						}
						else { // odd, luckier
							int lotto = (int)(Math.random() * 2);
							if(lotto == 0) {
								Bet *= 1.5;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP ) + " LP"); HeavenWheel(); break;
							}
						}
					case 2: // 2
						if (Day % 2 == 0)  { // even 1,5 = 1/3, 2 = 1/4, 2.5 = 1/5 3 = 1/6
							int lotto = (int)(Math.random() * 3);
							if(lotto == 0) {
								Bet *= 2;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP) + " LP");
								HeavenWheel(); break;
							}
						}
						else { // odd, luckier
							int lotto = (int)(Math.random() * 2);
							if(lotto == 0) {
								Bet *= 2;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP) + " LP");
								HeavenWheel(); break;
							}
						}
					case 3: // 2.5
						if (Day % 2 == 0)  { // even 1,5 = 1/3, 2 = 1/4, 2.5 = 1/5 3 = 1/6
							int lotto = (int)(Math.random() * 4);
							if(lotto == 0) {
								Bet *= 2.5;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP) + " LP");
								HeavenWheel(); break;
							}
						}
						else { // odd, luckier
							int lotto = (int)(Math.random() * 3);
							if(lotto == 0) {
								Bet *= 2.5;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP) + " LP");
								HeavenWheel(); break;
							}
						}
					case 4: // 3
						if (Day % 2 == 0)  { // even 1,5 = 1/3, 2 = 1/4, 2.5 = 1/5 3 = 1/6
							int lotto = (int)(Math.random() * 5);
							if(lotto == 0) {
								Bet *= 3;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP -= Bet) + " LP");
								HeavenWheel(); break;
							}
						}
						else { // odd, luckier
							int lotto = (int)(Math.random() * 4);
							if(lotto == 0) {
								Bet *= 3;
								LP += Bet;
								System.out.println("You won! Your current LP is " + (LP));
								System.out.println("You spent the whole day enjoying your winnings, you left afterwards.");
								Day += 1; visited = 0; menu(); break;
							}
							else {
								LP -= Bet;
								System.out.println("You lost, Your current LP is " + (LP -= Bet) + " LP"); 
								HeavenWheel(); break;
							}
						}
					default: HeavenWheel(); break; // left
					}
				}
					
		case 2: 
			System.out.println("You have four options when you want to bet;\r\n"
				+ "X = \"number\" indicates what your multiplier is for that bet\r\n"
				+ "for example, 1.5 gives you 1.5x multiplier when you win,\r\n"
				+ "although the higher the multiplier rate is, the lesser your\r\n"
				+ "chance. 1.5 has 1/3 chance of winning, 2 has 1/4 chance of winning\r\n"
				+ "2.5 has 1/5 chance of winning and 3 has 1/6 chance of winning."); HeavenWheel(); break; 
		default: menu(); break;
		}
	}
}
