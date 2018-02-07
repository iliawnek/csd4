import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CTO {

    public static void main(String[] args) {
        String filepath = args[0];
        try {
            // read cipher text from file
            ArrayList<String> cipherText = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line = null;
            while ((line = reader.readLine()) != null) cipherText.add(line);

            // brute force search
            double keyCount = Math.pow(2, 16);
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890., \n"; // assumed

            for (int key = 1; key < keyCount; key++) {
                String message = "";
                boolean skip = false;
                for (String block : cipherText) {
                    // decrypt block
                    int decrypted = Coder.decrypt(key, Integer.decode(block));

                    // first char
                    int c0 = decrypted / 256;
                    if (alphabet.indexOf(c0) < 0) {skip = true; break;}
                    message += (char) c0;

                    // second char
                    int c1 = decrypted % 256;
                    if (c1 != 0) {
                        if (alphabet.indexOf(c1) < 0) {skip = true; break;}
                        message += (char) c1;
                    }
                }
                // print only if key results in a message constructed using assumed alphabet
                if (!skip) {
                    System.out.printf("key = 0x%04X\n", key);
                    System.out.printf("message = %s\n", message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
