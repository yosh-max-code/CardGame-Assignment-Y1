package com.cardgame.patience.model;

import java.util.ArrayList;

/**
 * I created this abstract class to implement the "is-a" relationship for inheritance
 * Both Deck and Pile are types of card containers, so the common functionality is here
 * This demonstrates my understanding of inheritance in OOP for the assignment.
 *
 * @author YASH SINGH
 * @version 1.0
 */
public abstract class CardContainer {
    // I protected this field so subclasses can access it directly
    protected ArrayList<Card> cards;

    /**
     * Constructor that initializes an empty container
     * All card containers start empty and then have cards added to them
     */
    public CardContainer() {
        this.cards = new ArrayList<>();
    }

    /**
     * I created this method to add a card to the container
     * Used by both Deck and Pile classes
     *
     * @param card The card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * I created this method to check if the container is empty
     * This is useful for error checking in the game logic
     *
     * @return true if the container has no cards
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * I created this method to get the number of cards in the container
     * Used for various game logic and display purposes
     *
     * @return The number of cards
     */
    public int size() {
        return cards.size();
    }

    /**
     * I created this method to access all cards in this container
     * This is needed when moving cards between piles
     *
     * @return An ArrayList containing all cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}