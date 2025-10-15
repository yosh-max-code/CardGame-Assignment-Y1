package com.cardgame.patience.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * The reason i made this class is to handle all score related functionality in my Patience game
 * It manages saving scores to a file, loading previous scores, and retrieving the top scores
 * This satisfies the NFR2 requirement for score persistence using text files

 * @author YASH SINGH
 * @version 3.0
 */

/**
 * This was made a static finalso file name is the same everytime its created
 */
public class ScoreManager {
    private static final String SCORES_FILE = "patience_scores.txt";
    private ArrayList<ScoreEntry> scores;

    /**
     * Constructor that initializes the scores list and loads existing scores
     * The scores get loaded immediately so there available as soon as the game starts
     */
    public ScoreManager() {
        scores = new ArrayList<>();
        loadScores();
    }

    /**
     * I created this method to load scores from the text file into memory
     * If the file doesn't exist yet or there's an error it gets handled
     */
    private void loadScores() {
        try {
            // Create a file object for our scores file
            File file = new File(SCORES_FILE);

            // If the file doesn't exist yet, we don't need to load anything
            if (!file.exists()) {
                System.out.println("No previous scores file found. A new one will be created when scores are saved.");
                return;
            }

            // Create a reader to read the file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to separate the fields
                String[] parts = line.split(",");

                // Make sure the line has the expected format
                if (parts.length >= 2) {
                    String playerName = parts[0];
                    int pileCount = Integer.parseInt(parts[1]);

                    // Create a ScoreEntry and add it to our list
                    ScoreEntry entry = new ScoreEntry(playerName, pileCount);
                    scores.add(entry);
                }
            }

            reader.close();
        /*
        I chose these catch blocks to handle problems accessing and reading the file , input,ouput
        and formatting for text that cant get parsed imto a number
         */
        } catch (IOException e) {
            System.out.println("Error reading scores file: " + e.getMessage());
        } catch (NumberFormatException e) {

            System.out.println("Error parsing score: " + e.getMessage());
        }
    }

    /**
     * I use this method to save a new score to both memory and the file
     *
     * @param playerName The name of the player
     * @param pileCount The number of piles remaining (lower is better)
     */
    public void saveScore(String playerName, int pileCount) {
        // Create a new score entry
        ScoreEntry entry = new ScoreEntry(playerName, pileCount);

        // Add it to our list of scores
        scores.add(entry);

        try {
            // Create a writer to write to the file
            // I use FileWriter with 'true' to add (append ) to a file rather than erasing old scores
            BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE, true));

            // Write the new score entry as a line in the file
            writer.write(playerName + "," + pileCount);
            writer.newLine();

            System.out.println("Score saved successfully!");

        } catch (IOException e) {
            // I handle file writing errors
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    /**
     * this method was used to sort top scoring (sorted by pile count)
     * This is used for FR10 to display the top 10 results
     *
     * @param n The number of top scores to return
     * @return ArrayList of the top N ScoreEntry objects
     */
    public ArrayList<ScoreEntry> getTopScores(int n) {
        // Create a copy of the scores list so we don't modify the original
        ArrayList<ScoreEntry> sortedScores = new ArrayList<>(scores);

        // Sort the scores by pile count (ascending - lower is better)
        Collections.sort(sortedScores, new Comparator<ScoreEntry>() {
            public int compare(ScoreEntry s1, ScoreEntry s2) {
                return Integer.compare(s1.getPileCount(), s2.getPileCount());
            }
        });

        // Create a list for the top scores
        ArrayList<ScoreEntry> topScores = new ArrayList<>();

        // Add up to n scores to the result list
        for (int i = 0; i < Math.min(n, sortedScores.size()); i++) {
            topScores.add(sortedScores.get(i));
        }

        return topScores;
    }
}