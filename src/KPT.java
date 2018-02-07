public class KPT {

    public static void main(String[] args) {
        int cipherText = Integer.decode("0xb73f");
        int plainText = Integer.decode("0x4120");
        double keyCount = Math.pow(2, 16);

        for (int key = 1; key < keyCount; key++) {
            int decrypted = Coder.decrypt(key, cipherText);
            if (decrypted == plainText) System.out.printf("0x%04X", key);
        }
    }
}
