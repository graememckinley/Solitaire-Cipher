
//package assignment2;

public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher(Deck key) {
        this.key = new Deck(key); // deep copy of the deck
    }

    /*
     * TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {
        /**** ADD CODE HERE ****/

        int[] keystream = new int[size];

        for (int i = 0; i < size; i++) {
            keystream[i] = this.key.generateNextKeystreamValue();
        }

        return keystream;
    }

    /*
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
        /**** ADD CODE HERE ****/

        String msgConv = "";
        String msgEncoded = "";

        // Remove all spaces and punctuation
        for (int i = 0; i < msg.length(); i++) {
            if ((msg.charAt(i) >= 65 && msg.charAt(i) <= 90) || (msg.charAt(i) >= 97 && msg.charAt(i) <= 122)) {
                msgConv += msg.charAt(i);
            }
        }

        // Make all letters uppercase
        msgConv = msgConv.toUpperCase();


        // Generate keystream
        int[] keystream = getKeystream(msgConv.length());


        // Shift each letter based on keystream
        for (int j = 0; j < msgConv.length(); j++) {
            char encoded;
            encoded = (char) ((msgConv.charAt(j) + keystream[j] - 'A') % 26 + 'A');

            msgEncoded += (char) encoded;
        }

        return msgEncoded;
    }

    /*
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
        /**** ADD CODE HERE ****/

        String msgDecoded = "";

        // Generate keystream
        int[] keystream = getKeystream(msg.length());


        // Shift each letter based on keystream
        for (int j = 0; j < msg.length(); j++) {
            char decoded;
            decoded = (char) ((msg.charAt(j) - keystream[j] - 'Z') % 26 + 'Z');

            msgDecoded += (char) decoded;
        }

        return msgDecoded;
    }
}
