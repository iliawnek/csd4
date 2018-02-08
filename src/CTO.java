import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CTO {

    static HashMap<Character, ArrayList<Character>> digrams;

    public static void main(String[] args) {
        String cipherTextFilepath = args[0];
        String digramsFilepath = args[1];

        // read cipher text from file
        ArrayList<String> cipherText = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cipherTextFilepath));
            String line;
            while ((line = reader.readLine()) != null) cipherText.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // read non-occurring English digrams from file
        digrams = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(digramsFilepath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                char firstLetter = splitLine[0].charAt(0);
                ArrayList<Character> secondLetters = new ArrayList<>();
                for (int i = 1; i < splitLine.length; i++) {
                    secondLetters.add(splitLine[i].charAt(0));
                }
                digrams.put(firstLetter, secondLetters);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // brute force search
        int keyCount = 65536;

        for (int key = 0; key < keyCount; key++) {
            String message = "";
            boolean skip = false;
            int blocksToCheck = 10;
            for (int i = 0; i < cipherText.size(); i++) {

                // decrypt block
                String block = cipherText.get(i);
                int decrypted = Coder.decrypt(key, Integer.decode(block));

                // first char
                int c0 = decrypted / 256;
                if (!CTO.inAlphabet(c0) && i < blocksToCheck) {
                    skip = true;
                    break;
                }
                message += (char) c0;

                // second char
                int c1 = decrypted % 256;
                if (c1 != 0) {
                    if (!CTO.inAlphabet(c1) && i < blocksToCheck) {
                        skip = true;
                        break;
                    }
                    message += (char) c1;
                }
            }
            if (!skip && !hasNonOccurringDigrams(message, blocksToCheck * 2)) {
                System.out.printf("key = 0x%04X\n", key);
                System.out.printf("message = %s\n", message);
                System.out.println();
            }
        }
    }

    static boolean isLetter(int c) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // assumed alphabet
        return letters.indexOf(c) >= 0;
    }

    static boolean isSymbol(int c) {
        String symbols = "., \n"; // assumed alphabet
        return symbols.indexOf(c) >= 0;
    }

    static boolean inAlphabet(int c) {
        return CTO.isLetter(c) || CTO.isSymbol(c);
    }

    static boolean hasNonOccurringDigrams(String s, int charsToCheck) {
        for (int i = 0; i < charsToCheck; i++) {
            int c0 = Character.toLowerCase(s.charAt(i));
            int c1 = Character.toLowerCase(s.charAt(i + 1));
            if (CTO.isLetter(c0) && CTO.isLetter(c1)) {
                if (digrams.get((char) c0).contains((char) c1)) return true;
            }
        }
        return false;
    }
}
