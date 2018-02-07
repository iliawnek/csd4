public class KPT {

    public static void main(String[] args) {
        int cipherText = Integer.decode("0xb73f");
        int plainText = Integer.decode("0x4120");
        int keyCount = 65536;

        for (int key = 0; key < keyCount; key++) {
            int decrypted = Coder.decrypt(key, cipherText);
            if (decrypted == plainText) System.out.printf("0x%04X", key);
        }
    }
}
