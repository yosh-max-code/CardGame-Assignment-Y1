package com.cardgame.patience.model;

/**
 * I created this class to represent a potential move in my Patience card game.
 * It stores information about which piles are involved in a move.
 * This helps me track and execute different moves, especially in the auto-play features.
 *
 * @author YASH SINGH
 * @version 1.0
 */
public class Move {
    // The index of the pile to move from
    public int fromPile;

    // The index of the pile to move to
    public int toPile;

    /**
     * Constructor to create a new move between two piles
     *
     * @param fromPile The index of the source pile
     * @param toPile The index of the destination pile
     */
    public Move(int fromPile, int toPile) {
        this.fromPile = fromPile;
        this.toPile = toPile;
    }
}