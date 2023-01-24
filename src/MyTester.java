import java.util.Arrays;

public class MyTester {
    public static Deck.Card[] deckToArray(Deck deck) {
        Deck.Card[] deckArray = new Deck.Card[deck.numOfCards];
        Deck.Card currentCard = deck.head;
        Deck.Card temp;

        // Copies cards to array
        for (int k = 0; k < deck.numOfCards; k++) {
            deckArray[k] = currentCard;
            currentCard = currentCard.next;
        }

        return deckArray;
    }

    public static void printDeck(Deck deck) {
        System.out.println(Arrays.toString(deckToArray(deck)));
    }

    public static void printDeckReferences(Deck deck) {
        Deck.Card currentCard = deck.head;

        for (int i = 0; i < deck.numOfCards; i++) {
            System.out.println(currentCard.prev + " <----- " + currentCard + " -----> " + currentCard.next);
            currentCard = currentCard.next;
        }
    }

    public static Deck keystreamExampleDeck() {

        // Create the deck
        Deck deck = new Deck();

        // Clubs
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 4); //4C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 7); //7C
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[0], 10); //10C
        Deck.Card c5 = deck.new PlayingCard(Deck.suitsInOrder[0], 13); //KC

        // Diamonds
        Deck.Card c6 = deck.new PlayingCard(Deck.suitsInOrder[1], 3); //3D
        Deck.Card c7 = deck.new PlayingCard(Deck.suitsInOrder[1], 6); //6D
        Deck.Card c8 = deck.new PlayingCard(Deck.suitsInOrder[1], 9); //9D
        Deck.Card c9 = deck.new PlayingCard(Deck.suitsInOrder[1], 12); //QD

        // Black joker
        Deck.Card c10 = deck.new Joker("black"); //BJ

        // More clubs
        Deck.Card c11 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C
        Deck.Card c12 = deck.new PlayingCard(Deck.suitsInOrder[0], 6); //6C
        Deck.Card c13 = deck.new PlayingCard(Deck.suitsInOrder[0], 9); //9C
        Deck.Card c14 = deck.new PlayingCard(Deck.suitsInOrder[0], 12); //QC

        // More diamonds
        Deck.Card c15 = deck.new PlayingCard(Deck.suitsInOrder[1], 2); //2D
        Deck.Card c16 = deck.new PlayingCard(Deck.suitsInOrder[1], 5); //5D
        Deck.Card c17 = deck.new PlayingCard(Deck.suitsInOrder[1], 8); //8D
        Deck.Card c18 = deck.new PlayingCard(Deck.suitsInOrder[1], 11); //JD

        // Red joker
        Deck.Card c19 = deck.new Joker("red"); //RJ

        // Even more clubs
        Deck.Card c20 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c21 = deck.new PlayingCard(Deck.suitsInOrder[0], 5); //5C
        Deck.Card c22 = deck.new PlayingCard(Deck.suitsInOrder[0], 8); //8C
        Deck.Card c23 = deck.new PlayingCard(Deck.suitsInOrder[0], 11); //JC

        // Even more diamonds
        Deck.Card c24 = deck.new PlayingCard(Deck.suitsInOrder[1], 1); //AD
        Deck.Card c25 = deck.new PlayingCard(Deck.suitsInOrder[1], 4); //4D
        Deck.Card c26 = deck.new PlayingCard(Deck.suitsInOrder[1], 7); //7D
        Deck.Card c27 = deck.new PlayingCard(Deck.suitsInOrder[1], 10); //10D
        Deck.Card c28 = deck.new PlayingCard(Deck.suitsInOrder[1], 13); //KD

        // Add cards to deck
        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);
        deck.addCard(c5);
        deck.addCard(c6);
        deck.addCard(c7);
        deck.addCard(c8);
        deck.addCard(c9);
        deck.addCard(c10);
        deck.addCard(c11);
        deck.addCard(c12);
        deck.addCard(c13);
        deck.addCard(c14);
        deck.addCard(c15);
        deck.addCard(c16);
        deck.addCard(c17);
        deck.addCard(c18);
        deck.addCard(c19);
        deck.addCard(c20);
        deck.addCard(c21);
        deck.addCard(c22);
        deck.addCard(c23);
        deck.addCard(c24);
        deck.addCard(c25);
        deck.addCard(c26);
        deck.addCard(c27);
        deck.addCard(c28);

        return deck;
    }

    public static void keystreamExample() {

        // Create the deck
        Deck deck = keystreamExampleDeck();
        printDeck(deck);

        System.out.println(deck.generateNextKeystreamValue());
    }

    public static void smallExample() {
        Deck deck = new Deck(5, 2);
        printDeck(deck);

        Deck.gen.setSeed(10);

        SolitaireCipher cipher = new SolitaireCipher(deck);

        cipher.encode("Is that you, Bob?");
    }

    public static void studentExample() {
        /*
        A little cryptic message for you to decode using a full deck of cards shuffled with seed 1234:
        GBEHNIGSWUKOEEY
        CBLNWLRGE
        KEVBPRBZ
        CBLTHIL
         */

        Deck deck = new Deck(13, 4);

        Deck.gen.setSeed(1234);
        deck.shuffle();

        SolitaireCipher cipher1 = new SolitaireCipher(deck);
        SolitaireCipher cipher2 = new SolitaireCipher(deck);
        SolitaireCipher cipher3 = new SolitaireCipher(deck);
        SolitaireCipher cipher4 = new SolitaireCipher(deck);

        System.out.println(cipher1.decode("GBEHNIGSWUKOEEY"));
        System.out.println(cipher2.decode("CBLNWLRGE"));
        System.out.println(cipher3.decode("KEVBPRBZ"));
        System.out.println(cipher4.decode("CBLTHIL"));
    }

    public static void main(String[] args) {
        Deck deck = new Deck(13,2);

        SolitaireCipher cipherToEncode = new SolitaireCipher(deck);
        SolitaireCipher cipherToDecode = new SolitaireCipher(deck);

        deck.shuffle();

        String message = "This is for COMP 250";

        String encoded = cipherToEncode.encode(message);
        System.out.println(encoded);

        String decoded = cipherToDecode.decode(encoded);
        System.out.println(decoded);
    }
}
