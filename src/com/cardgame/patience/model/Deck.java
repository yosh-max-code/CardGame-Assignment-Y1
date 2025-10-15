package com.cardgame.patience.model;

import java.util.Collections;

/**
 * Used to represent a deck of cards in my Patience game
 * It extends the CardContainer abstract class to inherit common functionality
 * This class handles shuffling and dealing cards for the game
 *
 * @author YASH SINGH
 * @version 1.0
 */
public class Deck extends CardContainer {

    /**
     * Constructor - creates an empty deck
     * I'll populate it with 52 cards in the MAIN file
     */
    public Deck() {
        super(); // Call the parent constructor to initialize the cards ArrayList
    }

    /**
     * I created this method to implement FR2: Shuffle the cards
     * It randomizes the order of cards in the deck using Collections.shuffle
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * implemented FR3: Deal a card
     * It removes and returns the top card from the deck
     *
     * @return The top card, or null if deck is empty
     */
    public Card dealCard() {
        if (isEmpty()) {
            return null;  // Return null if no cards left
        }
        return cards.remove(0);  // Remove and return the top card
    }

    /**
     * implemented FR1: Show the full pack
     * It displays all cards in their current order to the console
     */
    public void showPack() {
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            System.out.print(currentCard + " ");
        }
        System.out.println();
    }
}