import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TMT2 {

    public static void main(String[] args) {
        int cipherText = Integer.decode("0x5b1b");
        int plainText = Integer.decode("0x5365");
        int chainLength = 1024;

        // read table
        String tableFilepath = args[0];
        HashMap<Integer, Integer> table = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableFilepath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(",");
                int end = Integer.parseInt(splitLine[0]);
                int start = Integer.parseInt(splitLine[1]);
                table.put(end, start);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check if cipher text is end of a chain
        if (table.containsKey(cipherText)) {
            int key = table.get(cipherText);
            for (int i = 0; i < chainLength - 1; i++) {
                key = Coder.encrypt(key, plainText);
            }
            System.out.printf("0x%04X\n", key);
        }
        // check if cipher text is in middle of chain
        else {
            int endBlockOffset = 0;
            int endBlock = cipherText;
            boolean keyFound = false;
            for (int i = 0; i < chainLength; i++) {
                endBlock = Coder.encrypt(endBlock, plainText);
                // key exists if endBlock = end of any chains
                if (table.containsKey(endBlock)) {
                    endBlockOffset = i + 1;
                    keyFound = true;
                    break;
                }
            }
            if (keyFound) {
                int key = table.get(endBlock);
                for (int i = 0; i < chainLength - endBlockOffset - 1; i++) {
                    key = Coder.encrypt(key, plainText);
                }
                System.out.printf("0x%04X\n", key);
            }
        }
    }
}
