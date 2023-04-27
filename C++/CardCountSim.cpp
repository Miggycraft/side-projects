#include <iostream>
#include <vector>
#include <cstring>
#include <cmath>
#include <fstream>
using namespace std;

/**
	JUST REFERENCE:
	name = | A | K | Q | J |10 | 9 | 8 | 7 | 6 | 5 | 4 | 3 | 2 |
	indx = | 0 | 1 | 2 | 3 |4 | 5 | 6 | 7  | 8 | 9 |10 |11 |12 |
	Card Count: A-10 - 1, 9-7 + 0, 6-2 +1
	
	TODO:
	-Reset(resets everything)
	-add splitting option
	
	BUGS:
	-bug sa player then a,,, (kasi gina assume sa playerhand na 2 cacrds na ang sa player)
	-or a,aa sa player
	
	NOTES:
	-need to refractor code... it's a bit messy na smh :(
	-Y/N sa Basic Strategy (refer link sa best option) is considered as dont split
**/

// global variables
string WARNING_MESSAGE = "";
const string FILE_NAME = "sim_blackjack.cfg";

//FOR CONFIGURES
int SOLOMODE = -1;
int DECKS = -1;

class Deck{
	public:
		Deck(int deck = 6){
			deckSize = deck;
			cardSize = deck * 52;
		}
		
		void initialize(){
			for (int i = 0; i < deckSize; i++){ //for decks
				for (int j = 0; j < 13; j++){ // for cards
					cardTotal[j] += 4;
				}
			}
		}
		
		int getCardTotal(int index){
			return cardTotal[index];
		}
		
		void removeCardTotal(int index){
			if (cardTotal[index] > 0)
				cardTotal[index]--;
		}
		
		int getRemainingDecks(){
			return ceil((double)cardSize / 52);
		}
		
		int getCC_value(){
			if (CC_value == 0)
				return 0;
			return floor(CC_value / getRemainingDecks());
		}
		
		void addCC_value(int index){
			if (index >= 0 && index <= 4)
				CC_value--;
			else if (index >= 8 && index <= 12)
				CC_value++;
		}
		
		
	private:
		int cardSize;
		int CC_value = 0; // returns the current value for card count
		string cardsName[13] = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
		int cardTotal[13] = {0}; // card total means the total of a specific card, used in array, index can be seen in topmost comment
		int deckSize;
};

class DealerHand{
	public:
		DealerHand(){
			holdBool = false;
		}
		
		void hold(string carde){
			card = carde;
			holdBool = true;
		}
		
		void release(){ // releases dealer's card.
			card = "";
			holdBool = false;
			setTurn(true);
		}
		
		bool isHolding(){
			return holdBool;
		}
		
		bool getTurn(){
			return firstTurn;
		}
		
		void setTurn(bool b){
			firstTurn = b;
		}
				
		string getCard(){
			if (!isHolding())
				return "X";
			return card;
		}
		
	private:
		bool firstTurn = true;
		bool holdBool = true;
		string card = ""; 
};

class PlayerHand{
	public:
		PlayerHand(){
			cardSize = 0;
		}
		
		void release(){
			cardSize = 0;
			setTurn(true);
		}
		
		void hold(string card){
			cards[getCardSize()] = card;
			cardSize++;
		}
		
		void split(){
			setSplit(true);
			cardSize--;
			cards[1] = ""; // dinelete
		}
		
		int getTotalCardValue(){
			int cardVal = 0;
			for (int i = 0; i < getCardSize(); i++){
				cardVal += getCardValue(getCard(i));
			}
			
			if (hasA() && cardVal > 21){ // workaround for A value
				cardVal -= 10;
				if (cards[0] == cards[1]){ // they are pair
					cardVal -= 10;
				}
			}
			return cardVal;
		}
		
		// setters
		
		
		void setTurn(bool b){
			firstTurn = b;
		}
		
		void setSplit(bool b){
			hasSplit = b;
		}
		
		// getters
		
		bool hasA(){
			for (int i = 0; i < cardSize; i++){
				if (getCard(i) == "A")
					return true;
			}
			
			return false;
		}
		
		int getAIndex(){ // returns the FIRST A, value only
			int index = -1;
			for (int i = 0; i < getCardSize(); i++){
				if (getCard(i) == "A"){
					index = i;
					break;
				}
			}
			return index;
			
		}
		
		bool getTurn(){
			return firstTurn;
		}
		
		bool getHasSplit(){
			if (splitIndex != 0 && splitIndex != 1){
				WARNING_MESSAGE += "ERROR AT SPLIT INDEX, LIKELY MORE THAN 2!\n";
			}
			return hasSplit;
		}
		
		bool canSplit(){
			if (getCardSize() > 2 || getCardSize() == 0)
				return false;
			if (getHasSplit())
				return false;
			return (getCard(0) == getCard(1));
		}
		
		int getCardValue(string card){
			if (card == "A"){
				return 11;
			}
			if (card == "K" || card == "J" || card == "Q" || card == "10")
				return 10;
				
			try{
				return stoi(card);
			}
			catch(exception ex){ // i doubt this would happen, haahaha... right?
				WARNING_MESSAGE += "ERROR AT GETTING CARD VALUE\n"; 
				return -1;
			}
		}
		
		int getCardSize(){
			return cardSize;
		}
		
		string getCard(int index){
			return cards[index];
		}
		
		int splitIndex = 0; //only 0 or 1 (left or right hand)
		
	private:
		int cardSize;
		bool hasSplit = false;
		bool firstTurn = true;
		string cards[10] = {};
};

void loadConfig(bool forceNew);
void printCard(Deck d);
void draw(string message, Deck d, DealerHand dh, PlayerHand ph);
bool isValid(string input, int *index, int turnIndex);
string upperCase(string s);
string bestOption(string dealerCard, PlayerHand ph);
bool legalMove(int turnIndex, bool playerEnds, PlayerHand ph, DealerHand dh, string card, string strLen);

int main(){
	loadConfig(false);
	Deck deck(DECKS);
	deck.initialize();
	DealerHand dh;
	PlayerHand ph;
	PlayerHand split_ph;
	
	bool indexUp = false;
	bool dealerEnds = false;
	bool playerEnds = false;
	bool playerTurn = false;
	bool playerDoubleDown = false;
	bool miscEnds = false;
	int turnIndex = 0; // 0 = dealer turn, 1 = player, 2 = misc (other players), 4 = option player will amke
	string message[4] = {"Enter Dealer\'s Upcard:", "Enter Player\'s Hand:", "Enter remaining cards:", "Option Made:"};
	
	string input;
	string tempString;
	int index = -1; // used for getting the index of a card
	int *p_index = &index;
	
	while(true){
		indexUp = false;
		WARNING_MESSAGE = "";
		if (dealerEnds && playerEnds && miscEnds){
			dealerEnds = false;
			playerEnds = false;
			playerDoubleDown = false;
			miscEnds = false;
			ph.release();
			dh.release();
			turnIndex = 0;
		}
		if (ph.getTotalCardValue() > 21) //bust cumm
			playerEnds = true;
		if (turnIndex < 0 || turnIndex > 3) // back to dealer
			turnIndex = 0;		
		if (dealerEnds && turnIndex == 0) // dealer done
			turnIndex = 1;	
		if (playerEnds && (turnIndex == 1 || turnIndex == 3)) // player done
			turnIndex = 2;
		if (playerDoubleDown && turnIndex == 1 && !playerEnds)
			playerEnds = true;
		if (turnIndex == 1)
			playerTurn = true;
		else
			playerTurn = false;
			
		if (SOLOMODE == 1 && turnIndex == 2 && !playerEnds){ // for solo players (1v1 against dealer)
			turnIndex = 3;
		}
		if (turnIndex == 3)
			draw((message[turnIndex] + bestOption(dh.getCard(),ph)), deck, dh, ph);
		else
			draw(message[turnIndex], deck, dh, ph); // create the output format (should be only for creating output format)
		
		cout << ">";
		cin >> input;
		input = upperCase(input);
		if (turnIndex == 3){
			if (input == "HIT"){
				turnIndex++;
			}
			else if (input == "STAND"){
				turnIndex++;
				playerEnds = true;
			}
			else if (input.at(0) == 'D' && ph.getCardSize() < 3){
				turnIndex++;
				playerDoubleDown = true;
			}
			else if (input == "SPLIT" && ph.canSplit() && !ph.getHasSplit()){
				turnIndex++;
				ph.split();
				split_ph.hold(ph.getCard(0));
			}
		}
		else if (ph.getHasSplit()){ // AHH SHIT
			// new code for splitting cards, don't ask why. i'm sorry.
			for (int i = 0; i < input.length(); i++){
				if (input[i] != ',') // used for delimiter, so it won't add "," to the temp string.
					tempString += input[i];
				
				if (input[i] == ',' || i == input.length()-1){ // delimiter, reads the next string and resets the temp string as well
					if (isValid(tempString, p_index, turnIndex) && legalMove(turnIndex, playerEnds, ph, dh, tempString, input)){
						if (tempString.at(0) != 'N'){ // N = null aka skips the turn
							deck.removeCardTotal(index);
							deck.addCC_value(index);
						}
						
						if (turnIndex == 0){ // dealer's turn
							dealerEnds = true;
							dh.hold(tempString);
						}
						
						if (playerTurn){
							ph.hold(tempString);
						}
						
						if (turnIndex == 0 && dh.getTurn()){
							dh.setTurn(false);
						}
						
						if (tempString == "X")
							miscEnds = true;
						
						if (!indexUp) // dapat last to
							indexUp = true;
					}
					tempString = "";
				}
			}
			if (indexUp){ // index up means na satisfied ang isValid 
				if (ph.getTurn() && turnIndex == 1) // this is bad.. but i don't want to make anoter variable
					ph.setTurn(false);	
				turnIndex++;
			}
		}
		else{
			for (int i = 0; i < input.length(); i++){
				if (input[i] != ',') // used for delimiter, so it won't add "," to the temp string.
					tempString += input[i];
				
				if (input[i] == ',' || i == input.length()-1){ // delimiter, reads the next string and resets the temp string as well
					if (isValid(tempString, p_index, turnIndex) && legalMove(turnIndex, playerEnds, ph, dh, tempString, input)){
						if (tempString.at(0) != 'N'){ // N = null aka skips the turn
							deck.removeCardTotal(index);
							deck.addCC_value(index);
						}
						
						if (turnIndex == 0){ // dealer's turn
							dealerEnds = true;
							dh.hold(tempString);
						}
						
						if (playerTurn){
							ph.hold(tempString);
						}
						
						if (turnIndex == 0 && dh.getTurn()){
							dh.setTurn(false);
						}
						
						if (tempString == "X")
							miscEnds = true;
						
						if (!indexUp) // dapat last to
							indexUp = true;
					}
					tempString = "";
				}
			}
			if (indexUp){ // index up means na satisfied ang isValid 
				if (ph.getTurn() && turnIndex == 1) // this is bad.. but i don't want to make anoter variable
					ph.setTurn(false);	
				turnIndex++;
			}
		}
			
	}
	return 0;
}

void loadConfig(bool forceNew){
	int count = 0;
	int tempNum;
	ifstream inFile(FILE_NAME);
	if (inFile.good() && !forceNew){ // load the previous one
		if (inFile){
			try{
				string line;
				while(getline(inFile, line, '\n')){
					if (count == 3){ // decks
						tempNum = stoi(line.substr(line.find(":") + 1));
						if (tempNum <= 0 || tempNum > 100)
							throw out_of_range("nyo~");
						else
							DECKS = tempNum;
					}
					if (count == 4){ // SoloMode
						tempNum = stoi(line.substr(line.find(":") + 1));
						if (tempNum > 1 || tempNum < 0){
							throw out_of_range("nyo~");
						}
						else
							SOLOMODE = tempNum;	
					}
					count++;
				}	
			}
			catch(exception x){
				loadConfig(true); // resets
			}
		}
		else{
			cout << "No file found... possibly corrupt?" << endl; // doubt this will be called.
		}
	}
	else{ // make a new config file
		ofstream newFile(FILE_NAME);
		newFile << "-CONFIG FILE-\n";
		newFile << "Written by Miggy btw\n\n";
		newFile << "Decks:6\n";
		newFile << "soloMode:0\n";
		newFile << "\n\n-NOTES-\n";
		newFile << "Decks should be between 1 - 100!\n";
		newFile << "soloMode should be only 0 (false) or 1 (true)\n";
		newFile.close();
		DECKS = 6;
		SOLOMODE = 0;
	}
	
	inFile.close();
}

string bestOption(string dealerCard, PlayerHand ph){
	// calcuation is based on this https://www.blackjackapprenticeship.com/wp-content/uploads/2018/08/BJA_Basic_Strategy.jpg
	// ngl the boring statements (i.e. not splittable or aces) is messy, refactoring should be good to make it more readable
	char c;
	int num;
	int dealerCardValue;
	int playerCardValue = ph.getTotalCardValue();
	bool dealerFoundValue = false;
	
	if (ph.canSplit()){
		dealerCardValue = ph.getCardValue(dealerCard); // lol...
		string pair = ph.getCard(0);
		if (pair == "A" || pair == "8")
			return "SPLIT";
		if (pair == "9"){
			if (dealerCardValue >= 2 && dealerCardValue <= 6)
				return "SPLIT";
			else if (dealerCardValue == 8 || dealerCardValue == 9)
				return "SPLIT";
		}
		if (pair == "7" && (dealerCardValue >= 2 && dealerCardValue <= 7))
			return "SPLIT";
		if (pair == "6" && (dealerCardValue >= 3 && dealerCardValue <= 6))
			return "SPLIT";
		if ((pair == "3") || (pair == "2") && (dealerCardValue >= 4 && dealerCardValue <= 7)) // same strat
			return "SPLIT";
	}
	
	if (ph.hasA() && ph.getCardSize() <= 2 && !ph.getHasSplit()){ // for soft totals lang, and A are not paired.
		dealerCardValue = ph.getCardValue(dealerCard); // holy shit i used playerhand function to get value of dealercard..
		int notA = (ph.getAIndex() == 0) ? 1 : 0; // if A index is nasa 0, yung not A ko is 1, else 0.
		char cAce = ph.getCard(notA).at(0);
		switch(cAce){
			case 'K':
			case 'Q':
			case 'J':
				return "STAND";		
		}
		try{
			num = stoi(ph.getCard(notA));
		}catch(exception x){
			WARNING_MESSAGE += "ERROR AT CONVERTING PLAYER CARD TO NUMBER\n"; // this is likely not going to happen UNTA
		}
		switch(num){
			case 10:
			case 9:
				return "STAND";
			case 8:
				if (dealerCardValue == 6)
					return "DOUBLE/HIT";
				else
					return "STAND";
			case 7:
				if (dealerCardValue <= 6)
					return "DOUBLE/HIT";
				else if (dealerCardValue >= 9)
					return "HIT";
				else
					return "STAND";
			case 6:
				if (dealerCardValue == 1 || dealerCardValue >= 7)
					return "HIT";
				else
					return "DOUBLE/HIT";
			case 5:
			case 4:
				if (dealerCardValue == 1 || dealerCardValue == 2 || dealerCardValue >= 7)
					return "HIT";
				else
					return "DOUBLE/HIT";
			case 3:
			case 2:
				if (dealerCardValue <= 4 || dealerCardValue >= 7)
					return "HIT";
				else
					return "DOUBLE/HIT";	
		}
	}
	
	c = dealerCard.at(0);
	if (c == 'A'){
		dealerFoundValue = true;
		dealerCardValue = 11;
	}
	else if (c == 'J' || c == 'K' || c == 'Q'){
		dealerFoundValue = true;
		dealerCardValue = 10;
	}
	
	if (!dealerFoundValue){
		try{
			num = stoi(dealerCard);
		}catch(exception x){
			WARNING_MESSAGE += "ERROR AT CONVERTING DEALER CARD TO NUMBER\n"; // this is likely not going to happen UNTA
		}
		dealerCardValue = num;
	}
	
	switch(dealerCardValue){
		case 11: // ace
			// 11 and 10 has same cases
		case 10: // faces or 10
			if (playerCardValue >= 17)
				return "STAND";
			else if (playerCardValue == 11)
				return "DOUBLE/HIT";
			return "HIT";
		case 9:
		case 8:
		case 7:
			if (playerCardValue >= 17)
				return "STAND";
			else if (playerCardValue == 11  || playerCardValue == 10)
				return "DOUBLE/HIT";
			return "HIT";
		case 6:
		case 5:
		case 4:
			if (playerCardValue >= 12)
				return "STAND";
			else if (playerCardValue == 11  || playerCardValue == 10 || playerCardValue == 9)
				return "DOUBLE/HIT";
			return "HIT";
		case 3:
			if (playerCardValue >= 13)
				return "STAND";
			else if (playerCardValue == 11  || playerCardValue == 10 || playerCardValue == 9)
				return "DOUBLE/HIT";
			return "HIT";
		case 2:
			if (playerCardValue >= 13)
				return "STAND";
			else if (playerCardValue == 11  || playerCardValue == 10)
				return "DOUBLE/HIT";
			return "HIT";
	}
	return "NOT FOUND {BUG}";
}

bool isValid(string message, int *index, int turnIndex){ //checks if the given string is valid, if true, returns index value as well.
	char c;
	int num;
	if (!message.length() > 0){ // if passed an empty value (aka , lang)
		*index = -1;
		return false;
	}
	if (message.length() == 1){ // checks if the length is 1 (if more than 1, then it must be num "10" or rubbish)
		c = message.at(0); // checks if the first value is equal to A,K,Q or J, else it assumes it is an number
		switch(c){
			case 'A':
				*index = 0;
				return true;
			case 'K':
				*index = 1;
				return true;
			case 'Q':
				*index = 2;
				return true;
			case 'J':
				*index = 3;
				return true;
		}
		if (turnIndex == 2){
			switch(c){
				case 'X':
				case 'N':
					*index = -1;
					return true;
			}
		}
	}
	
	try{
		num = stoi(message); // if it isn't a number, it will throw and changes the num to -1
	}
	catch(exception x){
		num = -1;
	}
	switch(num){
		case 10:
			*index = 4;
			return true;
		case 9:
			*index = 5;
			return true;
		case 8:
			*index = 6;
			return true;
		case 7:
			*index = 7;
			return true;
		case 6:
			*index = 8;
			return true;
		case 5:
			*index = 9;
			return true;
		case 4:
			*index = 10;
			return true;
		case 3:
			*index = 11;
			return true;
		case 2:
			*index = 12;
			return true;
		default:
			*index = -1;
			return false;
	}
}

bool legalMove(int turnIndex, bool playerEnds, PlayerHand ph, DealerHand dh, string card, string strLen){
	switch(turnIndex){
		case 0: // dealer
			return (dh.getTurn() && !dh.isHolding());
		case 1: // player
			if (ph.getTurn() && ph.getCardSize() < 2 && strLen.length() >= 3)
				return true;
			else if (!ph.getTurn() && ph.getCardSize() >= 2 && strLen.length() <= 2)
				return true;
			else if (ph.getHasSplit())
				return true;
			return false;
		case 2: // misc
			if (card.at(0) == 'X' && !playerEnds)
				return false;
			else{
				return true;
			}
		default:
			WARNING_MESSAGE += "INVALID LEGAL MOVE INDEX";
			return false;
	}
}

void draw(string message, Deck d, DealerHand dh, PlayerHand ph){ // draws every action taken.
	system("cls");
	printf("------------------------------------------------------------------\n");
	printf("|                       REMAINING CARDS                          |\n");
	printf("------------------------------------------------------------------\n");
	printf("| A  | K  | Q  | J  | 10 | 9  | 8  | 7  | 6  | 5  | 4  | 3  | 2  |\n");
	printCard(d);
	printf("------------------------------------------------------------------\n");
	printf("| Card Count = %-2d               | Splittable = %-5s             |\n",d.getCC_value(), ph.canSplit() ? "TRUE" : "FALSE"); // change true value to check if the player's hand can abe split
	printf("------------------------------------------------------------------\n");
	if (WARNING_MESSAGE != ""){
		cout << WARNING_MESSAGE << endl;
		printf("------------------------------------------------------------------\n");
	}
	if (ph.getHasSplit()){
		if (ph.splitIndex == 0){
			message += "[LEFT HAND]";
		}
		else if (ph.splitIndex == 1){
			message += "[RIGHT HAND]";
		}
	}
	cout << "Dealer's Upcard: " << dh.getCard() << endl;
	cout << message << endl;
}

void printCard(Deck d){
	for (int i = 0; i < 12; i++){
		printf("| %-2d ",d.getCardTotal(i));
	}
	printf("| %-2d |\n",d.getCardTotal(12)); // for the final card and closure, as well as new line
}

string upperCase(string s){
	for (int i = 0; i < s.length(); i++){
		s[i] = toupper(s[i]);
	}
	
	return s;
}