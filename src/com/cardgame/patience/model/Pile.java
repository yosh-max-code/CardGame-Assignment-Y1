package com.cardgame.patience.model;

/**
 * I created this class to represent a pile of cards in my Patience game.
 * It extends the CardContainer abstract class to inherit common functionality.
 * In this game, only the top card of each pile is visible and matters for gameplay.
 *
 * @author YASH SINGH
 * @version 1.0
 */
public class Pile extends CardContainer {

    /**
     * Constructor to create a new empty pile
     * Cards will be added to this pile during gameplay
     */
    public Pile() {
        super(); // Call the parent constructor to initialize the cards ArrayList
    }

    /**
     * access the top card of the pile
     * This is important for checking if piles can be combined
     *
     * @return The top card, or null if pile is empty
     */
    public Card getTopCard() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }

    /**
     * this method used to display the pile on the console
     * In Patience, only the top card is visible, so that's all we show
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Empty pile");
        } else {
            System.out.println(getTopCard());
        }
    }
}