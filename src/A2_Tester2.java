import java.util.Arrays;

class CreateDeck1 implements Runnable {
    public void run() {
        Deck deck = new Deck(4, 3);

        if (deck.numOfCards != 14) {
            throw new AssertionError("Deck is empty when it should have 14 cards");
        }
        System.out.println("Test passed.");
    }
}

class CreateDeck2 implements Runnable {
    public void run() {

        Deck deck1 = new Deck();
        Deck.PlayingCard card1 = deck1.new PlayingCard("clubs", 1); // AC
        Deck.PlayingCard card2 = deck1.new PlayingCard("clubs", 2); // 2C
        Deck.PlayingCard card3 = deck1.new PlayingCard("diamonds", 1); // AD
        Deck.PlayingCard card4 = deck1.new PlayingCard("diamonds", 2); // 2D
        Deck.Joker rJoker = deck1.new Joker("red"); // RJ
        Deck.Joker bJoker = deck1.new Joker("black"); // BJ
        deck1.addCard(card1);
        deck1.addCard(card2);
        deck1.addCard(card3);
        deck1.addCard(card4);
        deck1.addCard(rJoker);
        deck1.addCard(bJoker);       // AC 2C AD 2D RJ BJ

        Deck deck2 = new Deck(2, 2); // AC 2C AD 2D RJ BJ

        if (deck1.head.getValue() != deck2.head.getValue()) {
            throw new AssertionError("Head of the deck is incorrect");
        }

        for (int i = 1; i < deck1.numOfCards + 1; i++) {
            if (deck1.head.getValue() != deck2.head.getValue()) {
                throw new AssertionError("The deck is not correctly created."
                        + "The card at position " + i + " (and/or after) is incorrect.");
            }
            deck1.head = deck1.head.next;
            deck2.head = deck2.head.next;

        }
        System.out.println("Test passed.");
    }
}

class CreateDeck3 implements Runnable {
    @Override
    public void run() {
        // checking if exception is thrown by the constructor
        try {
            Deck deck1 = new Deck(14, 1);
        } catch (IllegalArgumentException raiseException) {
            System.out.println("Test passed.");
        }

        try {
            Deck deck1 = new Deck(2, 6);
        } catch (IllegalArgumentException raiseException) {
            System.out.println("Test passed.");
        }
    }
}

class DeepCopyDeck1 implements Runnable{
    public void run(){
        // creating and copying an empty deck
        Deck deck = new Deck();
        Deck copy = new Deck(deck);

        if (!(deck.head == null && copy.head == null)) {
            throw new AssertionError("An empty deck is not being initialized with default values."
                    + "The head of the deck and copy deck should be null.");
        }

        if (!(deck.numOfCards == 0 && copy.numOfCards == 0)) {
            throw new AssertionError("An empty deck is not being initialized with default values."
                    + "The number of cards in the deck and copy deck should be 0.");
        }
        System.out.println("Test passed.");
    }
}

class TripleCut1 implements Runnable {
    @Override
    public void run() {
        // For the edge case of tripleCut() when there are no cards before the 1st card
        // firstCard is the head of the deck

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);   // AC 2C AH 2H

        deck.tripleCut(c1, c3); // Expected outcome: 2H AC 2C AH

        // test whether all the cards are in the right order
        boolean head = deck.head == c4;
        boolean tail = deck.head.prev == c3;
        boolean c4Ref = c4.next == c1 && c4.prev == c3;
        boolean c3Next = c3.next == c4;
        boolean c1Prev = c1.prev == c4;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c4Ref && c3Next && c1Prev)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("Test passed.");

    }
}

class TripleCut2 implements Runnable {
    @Override
    public void run() {
        // For the edge case of tripleCut() when there are no cards after the 2nd card
        // secondCard is the tail of the deck

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4); // AC 2C AH 2H

        deck.tripleCut(c2, c4); // Expected Outcome: 2C AH 2H AC

        boolean head = deck.head == c2;
        boolean tail = deck.head.prev == c1;
        boolean c2Prev = c2.prev == c1;
        boolean c1Ref = c1.prev == c4 && c1.next == c2;
        boolean c4Next = c4.next == c1;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c2Prev && c1Ref && c4Next)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("Test passed.");
    }
}


class TripleCut3 implements Runnable {
    @Override
    public void run() {
        // regular case of tripleCut()

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H
        Deck.Card c5 = deck.new PlayingCard(Deck.suitsInOrder[3], 1); //AS
        Deck.Card c6 = deck.new PlayingCard(Deck.suitsInOrder[3], 2); //2S

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);
        deck.addCard(c5);
        deck.addCard(c6);       // Deck: AC 2C AH 2H AS 2S

        deck.tripleCut(c3, c5);  // Expected outcome: 2S AH 2H AS AC 2C

        boolean head = deck.head == c6;
        boolean tail = deck.head.prev == c2;
        boolean c1Ref = c1.prev == c5 && c1.next == c2;
        boolean c2Ref = c2.prev == c1 && c2.next == c6;
        boolean c3Red = c3.prev == c6 && c3.next == c4;
        boolean c4ref = c4.prev == c3 && c4.next == c5;
        boolean c5Ref = c5.prev == c4 && c5.next == c1;
        boolean c6Ref = c6.prev == c2 && c6.next == c3;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c1Ref && c2Ref && c3Red && c4ref && c5Ref && c6Ref)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("Test passed.");
    }
}


class TripleCut4 implements Runnable {
    @Override
    public void run() {
        // For the edge case of tripleCut() there are three cards and firstCard
        // and secondCard are the same

        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);

        // Before:
        // head = c1, c2, c3
        // AC, 2C, AH
        deck.tripleCut(c2, c2);
        // Expected deck arrangement:
        // head = c3, c2, c1
        // AH, 2C, AC

        boolean head = deck.head == c3;
        boolean tail = deck.head.prev == c1;
        boolean c1Ref = c1.prev == c2 && c1.next == c3;
        boolean c2Ref = c2.prev == c3 && c2.next == c1;
        boolean c3Ref = c3.prev == c1 && c3.next == c2;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c1Ref && c2Ref && c3Ref)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("Test passed.");
    }
}


class CountCut1 implements Runnable{
    @Override
    public void run() {
        // regular case

        Deck deck = new Deck();

        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[2], 1); //AH
        Deck.Card c4 = deck.new PlayingCard(Deck.suitsInOrder[2], 2); //2H

        deck.addCard(c1);
        deck.addCard(c3);
        deck.addCard(c4);
        deck.addCard(c2);      // Deck : AC AH 2H 2C

        deck.countCut();       // Cut 2 numbers from the top (taking value of 2C)
        // Expected Deck Outcome : 2H AC AH 2C

        boolean head = deck.head == c4;
        boolean tail = deck.head.prev == c2;
        boolean c4Ref = c4.prev == c2 && c4.next == c1;
        boolean c1Ref = c1.prev == c4 && c1.next == c3;
        boolean c3ref = c3.prev == c1 && c3.next == c2;
        boolean c2Ref = c2.prev == c3 && c2.next == c4;

        if (!(head && tail)) {
            throw new AssertionError("The head/tail is incorrect");
        } else if (!(c4Ref && c1Ref && c3ref && c2Ref)) {
            throw new AssertionError("The pointers of the cards are incorrect");
        }
        System.out.println("Test passed.");
    }
}

class CountCut2 implements Runnable {
    @Override
    public void run() {
        // when the value of the last card is a multiplier of the number of cards in the deck

        Deck deck = new Deck();
        Deck.PlayingCard c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.PlayingCard c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.PlayingCard c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C
        Deck.PlayingCard c4 = deck.new PlayingCard(Deck.suitsInOrder[0], 4); //4C

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);
        deck.addCard(c4);      // Deck : AC 2C 3C 4C

        deck.countCut();       // Do nothing because 4 is a multiplier of 4

        if(!(deck.head == c1) && deck.head.prev == c4) {
            throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
        }
        System.out.println("Test passed.");
    }
}

class CountCut3 implements Runnable{
    @Override
    public void run() {
        // when the number for cut is 1

        Deck deck = new Deck();

        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C

        deck.addCard(c1);
        deck.addCard(c2);

        deck.countCut();       // Should put AC above 3C (technically not changing the deck)

        if(!(deck.head == c1) && deck.head.prev == c2) {
            throw new AssertionError("The method countCut() is modifying the deck when it shouldn't");
        }
        System.out.println("Test passed.");
    }
}

class LocateJoker1 implements Runnable {
    @Override
    public void run() {
        // edge case where deck is empty, must return null

        Deck deck = new Deck();
        deck.locateJoker("red");

        if (deck.locateJoker("red") != null) {
            throw new AssertionError("The method locateJoker() is not returning null when the deck is empty");
        }
        System.out.println("Test passed.");
    }
}

class LocateJoker2 implements Runnable {
    @Override
    public void run() {
        // edge case where there is no joker in the deck, must return null
        Deck deck = new Deck();
        Deck.Card c1 = deck.new PlayingCard(Deck.suitsInOrder[0], 1); //AC
        Deck.Card c2 = deck.new PlayingCard(Deck.suitsInOrder[0], 2); //2C
        Deck.Card c3 = deck.new PlayingCard(Deck.suitsInOrder[0], 3); //3C

        deck.addCard(c1);
        deck.addCard(c2);
        deck.addCard(c3);

        deck.locateJoker("red");

        if (deck.locateJoker("red") != null) {
            throw new AssertionError("The method locateJoker() is not returning null when the there is no joker");
        }
        System.out.println("Test passed.");
    }
}

class GenerateNextKeystreamValue1 implements Runnable {
    @Override
    public void run() {
        // example case from the last page of pdf

        Deck deck = new Deck(5, 2);
        // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

        int seed = 10;
        Deck.gen.setSeed(seed);
        deck.shuffle();     // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

        int value = deck.generateNextKeystreamValue();


        if (value != 4) {
            throw new AssertionError("The method generateNextKeystreamValue() " +
                    "is not returning the correct value. Expected value is 4 but got " + value);
        }
        System.out.println("Test passed.");
    }
}

class GenerateNextKeystreamValue2 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(1, 1); // AC RJ BJ

        int seed = 31;
        Deck.gen.setSeed(seed);
        deck.shuffle();         // RJ BJ AC

        int value = deck.generateNextKeystreamValue();

        if (value != 1) {
            throw new AssertionError("The method generateNextKeystreamValue() is " +
                    "not returning the correct value. Expected value is 1 but got " + value);
        } else if (deck.head != deck.locateJoker("red")) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("Test passed.");
    }
}

class GenerateNextKeystreamValue3 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(2, 2); // AC 2C AD 2D RJ BJ

        int seed = 26;
        Deck.gen.setSeed(seed);
        deck.shuffle();        // BJ 2C 2D AD RJ AC

        int value = deck.generateNextKeystreamValue();

        if (value != 2) {
            throw new AssertionError("The method generateNextKeystreamValue() is " +
                    "not returning the correct value. Expected value is 2 but got " + value);
        } else if (deck.head.getValue() != 14) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("Test passed.");
    }
}

class GenerateNextKeystreamValue4 implements Runnable {
    public void run() {
        Deck deck = new Deck();
        Deck.PlayingCard card1 = deck.new PlayingCard("clubs", 1); // AC
        Deck.PlayingCard card2 = deck.new PlayingCard("clubs", 2); // 2C
        Deck.PlayingCard card3 = deck.new PlayingCard("clubs", 3); // 3C
        Deck.Joker rJoker = deck.new Joker("red"); // RJ
        Deck.PlayingCard card4 = deck.new PlayingCard("diamonds", 1); // AD
        Deck.PlayingCard card5 = deck.new PlayingCard("diamonds", 2); // 2D
        Deck.Joker bJoker = deck.new Joker("black"); // BJ
        Deck.PlayingCard card6 = deck.new PlayingCard("diamonds", 3); // 3D

        deck.addCard(card1);
        deck.addCard(card2);
        deck.addCard(card3);
        deck.addCard(rJoker);
        deck.addCard(card4);
        deck.addCard(card5);
        deck.addCard(bJoker);
        deck.addCard(card6);        // AC 2C 3C RJ AD 2D BJ 3D

        int value = deck.generateNextKeystreamValue();

        if (value != 16) {
            throw new AssertionError("The method generateNextKeystreamValue() is " +
                    "not returning the correct value. Expected value is 16 but got " + value);
        } else if (deck.head.getValue() != 16) {
            throw new AssertionError("Incorrect head after generateNextKeystreamValue()");
        }
        System.out.println("Test passed.");
    }
}

// =========================== SOLITAIRE CIPHER ================================

class GetKeystream1 implements Runnable{
    @Override
    public void run() {
        // example case from the last page of pdf

        Deck deck = new Deck(5, 2);
        // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

        int seed = 10;
        Deck.gen.setSeed(seed);
        deck.shuffle();     // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

        SolitaireCipher cipher = new SolitaireCipher(deck);
        int[] keystream = cipher.getKeystream(12);

        int[] expected = {4, 4, 15, 3, 3, 2, 1, 14, 16, 17, 17, 14};

        if (!Arrays.equals(keystream, expected)) {
            throw new AssertionError("The method getKeystream() is not returning the correct keystream");
        }

        System.out.println("Test passed.");
    }
}
class GetKeystream2 implements Runnable {
    @Override
    public void run()    {
        Deck deck1 = new Deck(12,2);

        int seed = 429;
        Deck.gen.setSeed(seed);
        deck1.shuffle();

        SolitaireCipher cipher1 = new SolitaireCipher(deck1);
        int [] keystream1 = cipher1.getKeystream(5);

        SolitaireCipher cipher2 = new SolitaireCipher(deck1);
        int[] keystream2 = cipher2.getKeystream(5);

        if (!Arrays.equals(keystream1, keystream2)) {
            throw new AssertionError("The keystream values of the 2 ciphers are not the same. " +
                    "\nExpected: [8, 10, 21, 16, 21] for both. \nGot " + Arrays.toString(keystream1) +
                    " for keystream1 and " + Arrays.toString(keystream2) + " for keystream2");
        }
        System.out.println("Test passed.");
    }
}

class EncodingAndDecodingTest1 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(5, 3);
        String message = "Heya! Love", decodedMessage = "HEYALOVE";

        SolitaireCipher cipher = new SolitaireCipher(deck);
        String encodedMessage = (cipher.encode(message));

        cipher = new SolitaireCipher(deck);
        String decodeAttempt = cipher.decode(encodedMessage);

        if (!decodeAttempt.equals(decodedMessage)) {
            throw new AssertionError("Error encoding/ decoding. \n Expected encoded message: LVQCZRNF. I received " + encodedMessage + "\n" +
                    "Expected decode output: HEYALOVE. I received: " + decodeAttempt);
        }
        System.out.println("Test passed.");
    }
}


class EncodingAndDecodingTest2 implements Runnable
{
    @Override
    public void run()
    {
        Deck deck = new Deck(1,1);
        String message = "Heya! L%$@!%:ove(!#%$", decodedMessage="HEYALOVE";

        SolitaireCipher cipher = new SolitaireCipher(deck);
        String encodedMessage = (cipher.encode(message));

        cipher = new SolitaireCipher(deck);
        String decodeAttempt = cipher.decode(encodedMessage);

        if(!decodeAttempt.equals(decodedMessage)) {
            throw new AssertionError("Error encoding/ decoding. \n Expected encoded message: IFZBMPWF. I received "+encodedMessage+"\n" +
                    "Expected decode output: HEYALOVE. I received: "+ decodeAttempt);
        }
        System.out.println("Test passed.");
    }
}

class SolitaireCipher1 implements Runnable {
    @Override
    public void run() {
        Deck deck = new Deck(5, 2);
        // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ

        int seed = 123;
        Deck.gen.setSeed(seed);
        deck.shuffle();  // AD RJ 2C 4C 3D AC 5C BJ 4D 2D 5D 3C

        String message = "You are amazing!", message2 = "YOUAREAMAZING";

        SolitaireCipher cipher = new SolitaireCipher(deck);
        int[] keystream = cipher.getKeystream(message2.length());
        int[] expected = {2, 4, 15, 16, 15, 4, 4, 16, 4, 3, 4, 1, 5};

        SolitaireCipher cipher1 = new SolitaireCipher(deck);
        String encodedMessage = cipher1.encode(message);
        // The expected keystream should be the same keystream used by the cipher1 for encoding

        SolitaireCipher cipher2 = new SolitaireCipher(deck);
        String decodedMessage = cipher2.decode(encodedMessage);
        // The expected keystream should be the same keystream used by the cipher2 for decoding

        if (!Arrays.equals(keystream, expected)) {
            throw new AssertionError("The method getKeystream() is not returning the correct keystream");
        }

        if (!encodedMessage.equals("ASJQGIECECMOL")) {
            throw new AssertionError("The encoded message is not correct");
        }
        if (!decodedMessage.equals(message2)) {
            throw new AssertionError("The decoded message is not correct");
        }
        System.out.println("Test passed.");
    }
}

class SolitaireCipher2 implements Runnable {
    @Override
    public void run()    {
        Deck deck = new Deck(2,1);

        int seed = 1234;
        Deck.gen.setSeed(seed);
        deck.shuffle();

        String message = "mari[o]  a(n)d><{lu~/ig}i", message2 = "MARIOANDLUIGI";

        SolitaireCipher cipher = new SolitaireCipher(deck);
        int[] keystream = cipher.getKeystream(message2.length());
        int[] expected = {1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1};

        SolitaireCipher cipher1 = new SolitaireCipher(deck);
        SolitaireCipher cipher2 = new SolitaireCipher(deck);

        String encodedMessage = cipher1.encode(message);
        String decodedMessage = cipher2.decode(encodedMessage);

        if (!Arrays.equals(keystream, expected)) {
            throw new AssertionError("The method getKeystream() is not returning the correct keystream");
        }
        if(!(encodedMessage.equals("NBTKPBPFMVKIJ"))) {
            throw new AssertionError("The encoded message is not correct");
        }
        if (!decodedMessage.equals(message2)) {
            throw new AssertionError("The decoded message is not correct");
        }
        System.out.println("Test passed.");
    }
}


public class A2_Tester2 {
    // To skip running some tests, just comment them out below.
    static String[] tests = {
            "CreateDeck1",
            "CreateDeck2",
            "CreateDeck3",
            "DeepCopyDeck1",
            "TripleCut1",
            "TripleCut2",
            "TripleCut3",
            "TripleCut4",
            "CountCut1",
            "CountCut2",
            "CountCut3",
            "LocateJoker1",
            "LocateJoker2",
            "GenerateNextKeystreamValue1",
            "GenerateNextKeystreamValue2",
            "GenerateNextKeystreamValue3",
            "GenerateNextKeystreamValue4",
            "GetKeystream1",
            "GetKeystream2",
            "EncodingAndDecodingTest1",
            "EncodingAndDecodingTest2",
            "SolitaireCipher1",
            "SolitaireCipher2",
    };

    public static void main(String[] args) {
        int numPassed = 0;
        for(String className: tests)    {
            System.out.printf("%n======= %s =======%n", className);
            System.out.flush();
            try {
                Runnable testCase = (Runnable) Class.forName(className).getDeclaredConstructor().newInstance();
                testCase.run();
                numPassed++;
            } catch (AssertionError e) {
                System.out.println(e);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        System.out.printf("%n%n%d of %d tests passed.%n", numPassed, tests.length);
        System.out.println("");

        if (numPassed == tests.length) {
            System.out.println("A little cryptic message for you to decode using a full deck of cards shuffled with seed 1234:");
            System.out.println("GBEHNIGSWUKOEEY");
            System.out.println("CBLNWLRGE");
            System.out.println("KEVBPRBZ");
            System.out.println("CBLTHIL");
            System.out.println("Note: They are all separate messages. Decode them using different cipher objects.");
            System.out.println("========================================");
        }
    }
}
