import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TMT2 {

    public static void main(String[] args) {
        int cipherText = Integer.decode("0x5b1b");

        // read table
        String tableFilepath = args[1];
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
        if (table.get(cipherText) != null) {

        }
        // check if cipher text is in middle of chain
        else {

        }
    }
}
