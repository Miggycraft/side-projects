import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PekoReader {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Peko!");

        File file = new File("peko.peko");
        if (file.exists()) { // idk throw method is kinda stupid bevcause of this
            System.out.println("Peko!");
            read(file);
        }
    }

    public static void read(File dir) throws FileNotFoundException {
        Peko peko = new Peko();
        Scanner input = new Scanner(dir);
        while(input.hasNext()) {
            String pekoString = input.next();
            switch(pekoString) {
            case "PEko": peko.sub(); break;
            case "peKO": peko.add(); break;
            case "Peko": peko.shiftL(); break;
            case "pekO": peko.shiftR(); break;
            case "peko": peko.halfArr(); break;
            case "PEKO": peko.doubleArr(); break;
            case "PeKo": peko.printArr(); break;
            case "pEkO": peko.printArrTest(); break;
            default:
            }
        }

        input.close();
    }
}