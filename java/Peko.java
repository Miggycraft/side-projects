public class Peko {
    private int currentIndex = 0;
    private int arrayLength = 8; // all arrays starts with a length of 8
    private int pekoArr[];
    
    Peko(){
        pekoArr = new int[arrayLength];
    }
        
    void add(){
        if (pekoArr[currentIndex] == 39) // number overflows
            pekoArr[currentIndex] = 0;
        else
            pekoArr[currentIndex]++;
    }
    
    void sub() {
        if (pekoArr[currentIndex] == 0) // number overflows
            pekoArr[currentIndex] = 39;
        else
            pekoArr[currentIndex]--;
    }
    
    void shiftL() { // shifts to the left
        if (currentIndex == 0) //array overflow
            currentIndex = arrayLength - 1;
        else
            currentIndex--;
    }
    
    void shiftR() { // shifts to the right
        if (currentIndex == (arrayLength - 1)) // array overflow
            currentIndex = 0;
        else
            currentIndex++;
    }
    
    void halfArr() { // halves the new array
        if (arrayLength != 2) { // will only half the arrays length is 4 or higher
            arrayLength /= 2;
            
            if (currentIndex >= arrayLength)
                currentIndex = arrayLength - 1;
        }
    }
    
    void doubleArr() { //doubles the new array
        int doubleLength = arrayLength * 2;
        int[] temp = new int[doubleLength];
        for (int i = 0; i < arrayLength; i++) { // copies the value
            temp[i] = pekoArr[i];
        }
        
        pekoArr = temp;
        arrayLength *= 2;
        
    }
    
    void printArrTest() {
        for (int i = 0; i < arrayLength; i++) {
            System.out.print("[" + pekoArr[i] + "] ");
        }
    }
    
    void printArr() {
        int num = pekoArr[currentIndex];
        if (num >= 0 && num <= 25) { // prints a letter
            System.out.print((char)('a' + num));
        }
        else if (num >= 26 && num <= 35) { // prints a number
            System.out.print(num - 26);
        }
        else // for special characters
            switch(num) {
            case 36:
                System.out.print("!");
                break;
            case 37:
                System.out.print("?");
                break;
            case 38:
                System.out.println(); // new line
                break;
            default:
                System.out.print(" ");
        }
    }
}