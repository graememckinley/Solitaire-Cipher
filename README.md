# Solitaire-Cipher

This project was completed in fulfillment of COMP 250 - Intro to Computer Science at McGill University.

For grading purposes skeleton versions of `Deck.java` and `SolitaireCipher.java` were provided to ensure that file, class, and method names were the same for all students.
Methods I implemented contain the /**** ADD CODE HERE ****/ comment and are explained below.
`Deck.java` and `SolitaireCipher.java` were the only files submitted for final evaluation, all other files were used for testing.

## Overview

This project is a java implentation of the solitaire cryptographic algorithm originally designed by Bruce Schneier and used in the novel *Cryptonomicon* by Neal Stephenson.
An explanation of the algorithm can be found [here](https://en.wikipedia.org/wiki/Solitaire_(cipher)).

### Card Value
Each card is associated with a value which depends on its rank and suit.
Cards in order from Ace to King have value 1 to 13 respectively. 
This value increases by a multiple of 13 depending on the suit of the card with the suits being ranked: clubs (lowest), followed by diamonds, hearts, and spades (highest). 
The jokers both have the same value and this value is equal to the total number of cards in the deck minus one.
If the deck has a total of 54 cards (the 52 playing cards plus the two jokers), then the jokers have value 53.

### Deck
The deck is implemented as a circular doubly linked list with the cards as nodes.
This means that the first card (the one on the top of the deck) is linked to the last card (the one at the bottom of the deck) and vice versa.

## Method Breakdown

For testing and grading purposes all members of the classes are public.
In reality most of these methods and all of the fields should have been kept private.

### `Deck.Deck(int numOfCardsPerSuit, int numOfSuits)`
Creates a deck with cards from Ace to numOfCardsPerSuit for the first numOfSuits in the class field suitsInOrder. 
The cards are ordered first by suit, and then by rank. In addition to these cards, a red joker and a black joker are added to the bottom of the deck in this order.
The constructor raises an IllegalArgumentException if the first input is not a number between 1 and 13 (both included) or the second input is not a number between 1 and the size of the class field suitsInOrder.

### `Deck.Deck(Deck d)`
Creates a deck by making a copy of the input deck.

### `Deck.addCard(Card c)`
Adds the input card to the bottom of the deck. This method runs in O(1).

### `Deck.shuffle()`
Shuffles the deck based on the Fisher-Yates shuffle algorithm. An explanation of the algorithm can be found [here](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle).
The algorithm runs in O(n) time and space where n is the number of cards in the deck.

### `Deck.locateJoker(String color)`
Returns a reference to the joker in the deck with the specified color. This method runs in O(n).

### `Deck.moveCard(Card c, int p)`
Moves the card c by p positions down the deck. This methods assumes the deck is not empty and the input card belongs to the deck. This mehtod runs in O(p).

### `Deck.tripleCut(Card firstCard, Card secondCard)`
Performs a triple cut on the deck using the two input cards as explained [here](https://en.wikipedia.org/wiki/Solitaire_(cipher)#Keystream_algorithm).
This method assums the input cards belong to the deck and the first one is nearest to the top of the deck. This method runs in O(1).

### `Deck.countCut()`
Performs a count cut on the deck as explained [here](https://en.wikipedia.org/wiki/Solitaire_(cipher)#Keystream_algorithm).
The number used for the cut is the value of the bottom card modulo the total number of cards in the deck.
This method runs in O(n).

### `Deck.lookUpCard()`
Returns a reference to the card that can be found by looking at the value of the card on the top of the deck and counting down that many cards.
If the card found is a joker the method returns null.
This method runs in O(n).

### `Deck.generateNextKeystreamValue()`
Uses the solitaire cryptographic algorithm to generate one value for the keystream using this deck.
This method runs in O(n).

### `SolitaireCiper.getKeystream(int size)`
Generates a keystream of the given size.

### `SolitaireCipher.encode(String msg)`
Encodes the input message by generating a keystream of the correct size and using it to encode the message as described [here](https://en.wikipedia.org/wiki/Solitaire_(cipher)#Encryption_and_decryption).

### `SolitaireCipher.decode(String msg)`
Decodes the input message by generating a keystream of the correct size and using it to decode the message as described [here](https://en.wikipedia.org/wiki/Solitaire_(cipher)#Encryption_and_decryption).

## Run Example
The following is an example block of code for encoding and decoding a message.

```
public static void main(String[] args) {
    Deck deck = new Deck(13, 4);

    SolitaireCipher cipherToEncode = new SolitaireCipher(deck);
    SolitaireCipher cipherToDecode = new SolitaireCipher(deck);

    deck.shuffle();

    String message = "Welcome to GitHub";

    String encoded = cipherToEncode.encode(message);
    System.out.println(encoded);

    String decoded = cipherToDecode.decode(encoded);
    System.out.println(decoded);
}
```

Run:
```
ABVAWLDKPZEIHFF
WELCOMETOGITHUB
```
