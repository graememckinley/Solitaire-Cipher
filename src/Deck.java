
//package assignment2;
import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /*
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {
        /**** ADD CODE HERE ****/

        // Edge case handling
        if ((numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) || (numOfSuits < 1 || numOfSuits > suitsInOrder.length)) {
            throw new IllegalArgumentException("Invalid input");
        }

        // Stores joker options
        String[] jokerColours = {"red", "black"};

		// Creates a deck based on number of cards and suits
        for (int i = 0; i < numOfSuits; i++) {
			for (int rank = 1; rank < numOfCardsPerSuit + 1; rank++) {

				Card card = new PlayingCard(suitsInOrder[i], rank);
                addCard(card);
			}
		}

		// Adds the jokers to the deck
        for (int j = 0; j < 2; j++) {
			Card card = new Joker(jokerColours[j]);
			addCard(card);
		}
    }

    /*
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {
        /**** ADD CODE HERE ****/

        // Sets currentCard to the head of the passed deck
        Card currentCard = d.head;

        // Creates new deck from deck d
        for (int i = 0; i < d.numOfCards; i++) {
            this.addCard(currentCard.getCopy());
            currentCard = currentCard.next;
        }
    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {
    }

    /*
     * TODO: Adds the specified card at the bottom of the deck. This
     * method runs in $O(1)$.
     */
    public void addCard(Card c) {
        /**** ADD CODE HERE ****/

        if (this.numOfCards == 0) {
            this.head = c;      // points the head towards the first card
            c.next = this.head;     // points first card towards head (next circular)
            c.prev = this.head;     // points first card towards head (previous circular)

        } else {
            if (this.numOfCards == 1) {
                this.head.next = c;     // points the head towards the next card
            }
            c.next = this.head;     // points the added card to the head (circular)
            c.prev = this.head.prev;    // points added card to second last card
            this.head.prev.next = c;    // points second last card to added card
            this.head.prev = c;     // points head to added card (circular)
        }

        this.numOfCards += 1;
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf.
     * This method runs in O(n) and uses O(n) space, where n is the total
     * number of cards in the deck.
     */
    public void shuffle() {
        /**** ADD CODE HERE ****/

        Card[] deckArray = new Card[this.numOfCards];
        Card currentCard = this.head;
        Card temp;

        // Copies cards to array
        for (int k = 0; k < numOfCards; k++) {
            deckArray[k] = currentCard;
            currentCard = currentCard.next;
        }

        // Swaps cards in the array
        for (int i = this.numOfCards - 1; i > 0; i--) {
            int j = gen.nextInt(i + 1);

            temp = deckArray[i];
            deckArray[i] = deckArray[j];
            deckArray[j] = temp;
        }

        // Resets the deck
        this.numOfCards = 0;

        // Rebuilds the shuffled deck
        for (Card card : deckArray) {
            this.addCard(card);
        }
    }

    /*
     * TODO: Returns a reference to the joker with the specified color in
     * the deck. This method runs in O(n), where n is the total number of
     * cards in the deck.
     */
    public Joker locateJoker(String color) {    // Check edge cases
        /**** ADD CODE HERE ****/

        Card currentCard = this.head;

        // Iterates through the deck until a "color" Joker is found
        for (int i = 0; i < this.numOfCards; i++) {
            if (currentCard instanceof Joker) {
                if (((Joker) currentCard).getColor().equals(color)) {
                    return (Joker) currentCard;
                }
            }
            currentCard = currentCard.next;
        }

        // Return null if no Jokers in the deck
        return null;
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */
    public void moveCard(Card c, int p) {   // Check that head is updated correctly
        /**** ADD CODE HERE ****/

        // Remove card from list
        c.prev.next = c.next;   // Points previous card to next card
        c.next.prev = c.prev;    // Points next card to previous card

        Card currentCard = c;

        // Iterates to the correct spot in the deck
        for (int i = 0; i < p; i++) {
            currentCard = currentCard.next;
        }

        // Inserts the card into the list
        c.next = currentCard.next;      // Points card to the next card
        c.prev = currentCard;       // Points card to the previous card
        currentCard.next.prev = c;     // Points the next card to card
        currentCard.next = c;       // Points the previous card to card
    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You
     * can assume that the input cards belong to the deck and the first one is
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
        /**** ADD CODE HERE ****/

        Card head1 = this.head;
        Card tail1 = firstCard.prev;

        Card head3 = secondCard.next;
        Card tail3 = this.head.prev;

        // If the first section is empty
        if (firstCard.equals(this.head)) {

            // Join sections (next)
            tail3.next = firstCard;

            // Join sections (prev)
            head3.prev = secondCard;

            // Set new head
            this.head = head3;

        } else if (secondCard.equals(this.head.prev)) {

            // Join sections (next)
            tail3.next = head1;
            tail1.next = firstCard;

            // Set new head
            this.head = firstCard;

        } else {

            // Join sections (next)
            tail3.next = firstCard;
            secondCard.next = head1;
            tail1.next = head3;

            // Join sections (prev)
            head1.prev = secondCard;
            firstCard.prev = tail3;
            head3.prev = tail1;

            // Set new head
            this.head = head3;
        }
    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the
     * bottom card is equal to a multiple of the number of cards in the deck,
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {
        /**** ADD CODE HERE ****/

        int numberOfCardsRemoved = this.head.prev.getValue();
        Card currentCard = this.head;

        // Iterate to find the last card in the block to be moved
        for (int i = 0; i < numberOfCardsRemoved - 1; i++) {
            currentCard = currentCard.next;
        }

        Card lastCardToRemove = currentCard;
        Card tail = this.head.prev;


        this.head.prev = tail.prev;     // Points the former head at the second last card
        tail.prev.next = this.head;     // Points the second last card at the former head

        this.head = lastCardToRemove.next;      // Sets the new head
        this.head.prev = tail;      // Points the new head at the tail

        tail.next = head;       // Points the tail at the new head

        lastCardToRemove.next = tail;       // Points the shifted section at the tail
        tail.prev = lastCardToRemove;       // Points the tail at the shifted section

    }


    /*
     * TODO: Returns the card that can be found by looking at the value of the
     * card on the top of the deck, and counting down that many cards. If the
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
        /**** ADD CODE HERE ****/

        int headValue = this.head.getValue();
        Card currentCard = this.head;

        // Iterate to the correct card
        for (int i = 0; i < headValue; i++) {
            currentCard = currentCard.next;
        }

        // Check whether the card is a Joker or not
        if (currentCard instanceof Joker) {
            return null;
        } else {
            return currentCard;
        }
    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {
        /**** ADD CODE HERE ****/

        while (true) {
            // Step 1: Locate red joker and move it one card below
            Card redJoker = locateJoker("red");
            this.moveCard(redJoker, 1);


            // Step 2: Locate black joker and move it two cards below
            Card blackJoker = locateJoker("black");
            this.moveCard(blackJoker, 2);


            // Step 3: Perform triple cut
            Card firstJoker = null;
            Card secondJoker = null;
            Card currentCard = this.head;
            boolean firstJokerFound = false;
            boolean jokersNotFound = true;

            // Iterate through the deck to find the jokers
            // This assumes there are Jokers (might have to change)
            while (jokersNotFound) {
                if (currentCard instanceof Joker) {
                    if (!firstJokerFound) {
                        firstJoker = currentCard;
                        firstJokerFound = true;
                    } else {
                        secondJoker = currentCard;
                        jokersNotFound = false;
                    }
                }
                currentCard = currentCard.next;
            }

            this.tripleCut(firstJoker, secondJoker);


            // Step 4: Perform count cut
            this.countCut();


            // Step 5: Get the value
            if (this.lookUpCard() != null) {
                return this.lookUpCard().getValue();
            }
        }
    }


    public abstract class Card {
        public Card next;
        public Card prev;

        public abstract Card getCopy();

        public abstract int getValue();

    }

    public class PlayingCard extends Card {
        public String suit;
        public int rank;

        public PlayingCard(String s, int r) {
            this.suit = s.toLowerCase();
            this.rank = r;
        }

        public String toString() {
            String info = "";
            if (this.rank == 1) {
                //info += "Ace";
                info += "A";
            } else if (this.rank > 10) {
                String[] cards = {"Jack", "Queen", "King"};
                //info += cards[this.rank - 11];
                info += cards[this.rank - 11].charAt(0);
            } else {
                info += this.rank;
            }
            //info += " of " + this.suit;
            info = (info + this.suit.charAt(0)).toUpperCase();
            return info;
        }

        public PlayingCard getCopy() {
            return new PlayingCard(this.suit, this.rank);
        }

        public int getValue() {
            int i;
            for (i = 0; i < suitsInOrder.length; i++) {
                if (this.suit.equals(suitsInOrder[i]))
                    break;
            }

            return this.rank + 13 * i;
        }

    }

    public class Joker extends Card {
        public String redOrBlack;

        public Joker(String c) {
            if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
                throw new IllegalArgumentException("Jokers can only be red or black");

            this.redOrBlack = c.toLowerCase();
        }

        public String toString() {
            //return this.redOrBlack + " Joker";
            return (this.redOrBlack.charAt(0) + "J").toUpperCase();
        }

        public Joker getCopy() {
            return new Joker(this.redOrBlack);
        }

        public int getValue() {
            return numOfCards - 1;
        }

        public String getColor() {
            return this.redOrBlack;
        }
    }

}
