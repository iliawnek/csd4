import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TMT1 {

    public static void main(String[] args) {
        String filepath = args[0];
        int plainText = Integer.decode("0x5365");
        int keyCount = 65536;
        int chainLength = 256;
        int chainCount = 256;
        Random random = new Random();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
            for (int i = 0; i < chainCount; i++) {
                int start = random.nextInt(keyCount);
                int end = start;
                for (int j = 0; j < chainLength; j++) end = Coder.encrypt(end, plainText);
                writer.write(String.format("%d,%d\n", end, start));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
