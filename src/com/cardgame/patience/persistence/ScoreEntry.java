package com.cardgame.patience.persistence;

/**
 * this class is to represent an individual score entry in my Patience game
 * Each entry contains a player's name and their final score (the number of piles remaining)
 * The goal of the game is to have as few piles as possible, so lower scores are better
 *
 * @author YASH SINGH
 * @version 1.0
 */
public class ScoreEntry {
    private String playerName;
    private int pileCount;

    /**
     * Constructor to create a new score entry
     *
     * @param playerName The name of the player
     * @param pileCount The number of piles remaining (lower is better)
     */
    public ScoreEntry(String playerName, int pileCount) {
        this.playerName = playerName;
        this.pileCount = pileCount;
    }

    /**
     * I need this method to access the player's name when displaying scores
     *
     * @return The player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * I need this method to access the pile count when sorting and displaying scores
     *
     * @return The pile count (lower is better)
     */
    public int getPileCount() {
        return pileCount;
    }

    /**
     * I use this method to create a formatted string representation of the score entry
     * This is helpful when displaying scores in the command line interface
     *
     * @return A formatted string showing the player name and score
     */
    @Override
    public String toString() {
        return playerName + ": " + pileCount + " piles";
    }
}