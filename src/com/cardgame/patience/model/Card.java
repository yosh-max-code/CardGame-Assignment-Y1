package com.cardgame.patience.model;

/**
 * This class represents a single playing card in my Patience card game
 * Each card has a suit (H, C, D, S) and a rank (2-10, J, K, Q, A)
 * I've kept this class simple with just the essential properties and methods needed for the game
 *
 * @author YASH SINGH
 * @version 1.0
 */
public class Card {
    private String suit; // H (Hearts), C (Clubs), D (Diamonds), S (Spades)
    private String rank; // 2-10, J (Jack), Q (Queen), K (King), A (Ace)

    /**
     * Constructor to create a new card with specific suit and rank
     *
     * @param suit The card's suit (H, C, D, S)
     * @param rank The card's rank (2-10, J, Q, K, A)
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * I need this method to access the suit for card comparison rules
     *
     * @return The card's suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * I need this method to access the rank for card comparison rules
     *
     * @return The card's rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * I use this method to create a string representation of the card
     * This is useful for displaying cards in the text interface
     *
     * @return String representation of the card (e.g., "2H" for 2 of Hearts)
     */
    public String toString() {
        return rank + suit;
    }
}