package com.cardgame.patience.controller;

import com.cardgame.patience.model.*;
import com.cardgame.patience.persistence.ScoreEntry;
import com.cardgame.patience.persistence.ScoreManager;
import uk.ac.aber.dcs.cs12320.cards.gui.javafx.CardTable;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  this class to control the main game flow for my Patience card game
 * It implements the text-based interface (NFR1) and all functional requirements (FR1-FR11)
 * This class handles user input, game state, and connects all the model classes together, suitable for running in the command line / terminal
 *
 * @author YASH SINGH
 * @version 2.0
 */
public class MainCMD {
    private Deck deck;
    private ArrayList<Pile> piles;
    private Scanner scanner;
    private ScoreManager scoreManager;
    private String playerName;
    // Add cardTable reference for GUI support (NFR3)
    private CardTable cardTable;

    /**
     * Constructor that initializes the game and gets the player's name
     */
    public MainCMD() {
        deck = new Deck();
        piles = new ArrayList<>(); // Initialize the list of piles
        scanner = new Scanner(System.in);
        scoreManager = new ScoreManager(); // Initialize ScoreManager

        // Get the player's name for score tracking (NFR2)
        System.out.print("Please enter your name: ");
        playerName = scanner.nextLine();
        System.out.println("Welcome, " + playerName + "!");
    }

    /**
     * Sets the CardTable instance for GUI support
     * @param cardTable The CardTable instance to use for GUI
     */
    public void setCardTable(CardTable cardTable) {
        this.cardTable = cardTable;
        // Initial update of the card display
        updateCardTableDisplay();
    }

    /**
     * Updates the graphical display of cards
     * This implements NFR3 (graphical interface)
     */
    public void updateCardTableDisplay() {
        if (cardTable != null) {
            // Convert our cards to the format expected by CardTable
            ArrayList<String> displayCards = new ArrayList<>();

            // For each pile, add the top card to the display list
            for (Pile pile : piles) {
                if (!pile.isEmpty()) {
                    Card topCard = pile.getTopCard();
                    // Format: "2H.gif" for 2 of Hearts
                    displayCards.add(topCard.getRank() + topCard.getSuit() + ".gif");
                }
            }


            cardTable.cardDisplay(displayCards);
        }
    }

    /**
     * this method is used to start the game and display the main menu in a loop
     * It implements NFR1 (command-line menu) and processes user choices
     */
    public void start() {
        // I used a do-while loop in other methods, but chose a while(true) with break here
        // for better flow control when quitting the game
        while (true) {
            displayMenu();
            String choice = getUserChoice();

            // Process the user's choice
            if (choice.equals("1")) {
                showPack();  // FR1
            } else if (choice.equals("2")) {
                shuffleCards();  // FR2
            } else if (choice.equals("3")) {
                dealCard();  // FR3
            } else if (choice.equals("4")) {
                moveLastPileOntoPrevious();  // FR4
            } else if (choice.equals("5")) {
                moveLastPileOverTwo();  // FR5
            } else if (choice.equals("6")) {
                amalgamateCards();  // FR6
            } else if (choice.equals("7")) {
                showPiles();  // FR7
            } else if (choice.equals("8")) {
                makeMove();  // FR8
            } else if (choice.equals("9")) {
                makeManyMoves();  // FR9
            } else if (choice.equals("10")) {
                showTopResults();  // FR10
            } else if (choice.equals("Q") || choice.equals("q")) {
                endGame();  // FR11
                break;  // Exit the loop and end the program
            } else {
                System.out.println("That feature is not implemented yet.");
            }

            System.out.println();  // Add a blank line for better readability
        }

        scanner.close();  // Close the scanner when the game ends
    }

    /**
     * The method is used to display the menu options to the player
     * This implements part of NFR1 (command-line menu)
     */
    private void displayMenu() {
        System.out.println(" ========== PATIENCE CARD GAME ========== ");
        System.out.println("1 - Print the pack out");
        System.out.println("2 - Shuffle");
        System.out.println("3 - Deal a card");
        System.out.println("4 - Make a move, move last pile onto previous one");
        System.out.println("5 - Make a move, move last pile back over two piles");
        System.out.println("6 - Amalgamate piles in the middle (by giving their numbers)");
        System.out.println("7 - Print the displayed cards on the command line");
        System.out.println("8 - Play for me once");
        System.out.println("9 - Play for me many times");
        System.out.println("10 - Display top 10 results");
        System.out.println("Q - Quit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Used this helper method to get user input from the console
     *
     * @return The user's input as a String
     */
    private String getUserChoice() {
        return scanner.nextLine();
    }

    /**
     * this helper method to create a new deck with all 52 cards
     * This avoids code duplication in several menu options
     */
    private void createNewDeck() {
        // I create arrays to store all possible suits and ranks
        String[] suits = {"H", "C", "D", "S"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // create each card in loop
        // The outer loop goes through each suit
        for (int i = 0; i < suits.length; i++) {
            String currentSuit = suits[i];

            // The inner loop goes through each rank
            for (int j = 0; j < ranks.length; j++) {
                String currentRank = ranks[j];

                // For each combination of suit and rank, I create a new card
                Card newCard = new Card(currentSuit, currentRank);
                deck.addCard(newCard);
            }
        }
        System.out.println(" a new deck of 52 cards.");
    }

    /**
     * this method is used is to implement FR1: Show the full pack of cards
     */
    private void showPack() {
        // First I check if the deck is empty
        if (deck.size() == 0) {
            System.out.println("The deck is empty. I'll create a new one.");
            // I call my helper method to create a new deck
            createNewDeck();
        }

        // Now I display all cards in the deck
        System.out.println("Current deck:");
        deck.showPack();
    }

    /**
     * this method is used used to implement FR2: Shuffle the cards
     */
    private void shuffleCards() {
        // First I check if the deck is empty
        if (deck.size() == 0) {
            System.out.println("The deck is empty. I'll create a new one before shuffling.");
            // I call my helper method to create a new deck
            createNewDeck();
        }

        // Now I call the shuffle method from the Deck class
        deck.shuffle();
        System.out.println("The deck has been shuffled.");
    }

    /**
     * this method is used to implement FR3: Deal a card
     */
    private void dealCard() {
        // First I check if the deck is empty
        if (deck.size() == 0) {
            System.out.println("The deck is empty. Cannot deal a card.");
            return; // Exit the method early since we can't deal a card
        }

        // Take the top card from the deck
        Card card = deck.dealCard();

        // Create a new pile with this card
        Pile newPile = new Pile();
        newPile.addCard(card);

        // Add the pile to our list of piles
        piles.add(newPile);

        // Display the new state of the game
        System.out.println("Dealt: " + card);
        showPiles();


    }

    /**
     * this method is used to implement FR4: Move last pile onto previous one
     */
    private void moveLastPileOntoPrevious() {
        // Make sure we have at least 2 piles
        if (piles.size() < 2) {
            System.out.println("Need at least 2 piles to make this move.");
            return;
        }

        // Get the last two piles
        Pile lastPile = piles.get(piles.size() - 1);
        Pile previousPile = piles.get(piles.size() - 2);

        // Check if top cards match by suit or rank
        Card lastTopCard = lastPile.getTopCard();
        Card prevTopCard = previousPile.getTopCard();

        if (lastTopCard.getSuit().equals(prevTopCard.getSuit()) ||
                lastTopCard.getRank().equals(prevTopCard.getRank())) {

            // Move all cards from last pile to previous pile
            for (Card card : lastPile.getCards()) {
                previousPile.addCard(card);
            }

            // Remove the last pile
            piles.remove(piles.size() - 1);
            System.out.println("Move successful!");
        } else {
            System.out.println("Move not allowed - cards don't match.");
        }

        // Show current game state
        showPiles();

        updateCardTableDisplay();
    }

    /**
     * this method is used is to implement FR5: Move last pile back over two piles
     */
    private void moveLastPileOverTwo() {
        // Make sure we have at least 3 piles
        if (piles.size() < 3) {
            System.out.println("Need at least 3 piles to make this move.");
            return;
        }
        // Get the piles with a card in between them
        Pile lastPile = piles.get(piles.size() - 1);
        Pile overTwoPile = piles.get(piles.size() - 3);

        // Check if top cards match by suit or rank
        Card lastTopCard = lastPile.getTopCard();
        Card prevTopCard = overTwoPile.getTopCard();

        if (lastTopCard.getSuit().equals(prevTopCard.getSuit()) ||
                lastTopCard.getRank().equals(prevTopCard.getRank())) {

            // Move all cards from last pile to overTwo pile
            for (Card card : lastPile.getCards()) {
                overTwoPile.addCard(card);
            }

            // Remove the last pile
            piles.remove(piles.size() - 1);
            System.out.println("Move successful!");
        } else {
            System.out.println("Move not allowed - cards don't match.");
        }


        showPiles();


        updateCardTableDisplay();
    }

    /**
     * this method is used to implement FR6: Amalgamate piles in the middle
     */
    private void amalgamateCards() {
        // Make sure I have enough piles
        if (piles.size() < 4) {
            System.out.println("Need at least 4 piles to amalgamate piles in the middle.");
            return;
        }

        try {
            // Get source and target pile numbers
            System.out.print("Enter the number of the pile to move: ");
            int sourceIndex = Integer.parseInt(scanner.nextLine()) - 1;

            System.out.print("Enter the number of the pile to move to: ");
            int targetIndex = Integer.parseInt(scanner.nextLine()) - 1;

            // Validate the input - I added robust error checking here
            if (sourceIndex < 0 || sourceIndex >= piles.size() ||
                    targetIndex < 0 || targetIndex >= piles.size() ||
                    sourceIndex == targetIndex) {
                System.out.println("Invalid pile numbers.");
                return;
            }

            // Check if piles are adjacent OR have exactly two piles between them
            if ((Math.abs(targetIndex - sourceIndex) == 1) ||
                    (Math.abs(targetIndex - sourceIndex) == 3)) {
                // Get the source and target piles
                Pile sourcePile = piles.get(sourceIndex);
                Pile targetPile = piles.get(targetIndex);

                // Check if top cards match by suit or rank
                Card sourceTopCard = sourcePile.getTopCard();
                Card targetTopCard = targetPile.getTopCard();

                if (sourceTopCard.getSuit().equals(targetTopCard.getSuit()) ||
                        sourceTopCard.getRank().equals(targetTopCard.getRank())) {

                    // Move all cards from source pile to target pile
                    for (Card card : sourcePile.getCards()) {
                        targetPile.addCard(card);
                    }

                    // Remove the source pile
                    piles.remove(sourceIndex);
                    System.out.println("Move successful!");
                } else {
                    System.out.println("Move not allowed - cards don't match.");
                }
            } else {
                System.out.println("Invalid move: Piles must be adjacent or have exactly two piles between them.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid pile numbers (integers only).");
        }


        showPiles();


        updateCardTableDisplay();
    }

    /**
     * this method is used to implement FR7: Show all cards in play
     * This helps us see the current state of the game
     */
    private void showPiles() {
        if (piles.isEmpty()) {
            System.out.println("No cards in play yet.");
            return;
        }

        System.out.println("Current piles (" + piles.size() + " total):");
        for (int i = 0; i < piles.size(); i++) {
            Pile pile = piles.get(i);
            System.out.print("Pile " + (i + 1) + ": ");
            if (pile.isEmpty()) {
                System.out.println("Empty");
            } else {
                System.out.println(pile.getTopCard());
            }
        }

    }

    /**
     * this method is used to implement FR8: Play for me once
     * Makes one automatic move, prioritizing two-pile jumps over adjacent moves
     *
     * @return true if a move was made, false if no moves are possible
     */
    private boolean makeMove() {
        // First, check if we have enough piles to make any moves
        if (piles.size() < 2) {
            System.out.println("Not enough piles to make a move.");
            return false;
        }

        // STEP 1: Try to find a two-pile jump first (highest priority)

        // Check if we have enough piles for a jump move (need at least 4 piles)
        if (piles.size() >= 4) {
            // Look through all possible pairs of piles with two piles between them
            for (int i = 0; i < piles.size() - 3; i++) {
                // Get the top cards for comparison
                Card leftCard = piles.get(i).getTopCard();
                Card rightCard = piles.get(i + 3).getTopCard();

                // Check if cards match by suit or rank
                if (leftCard.getSuit().equals(rightCard.getSuit()) ||
                        leftCard.getRank().equals(rightCard.getRank())) {

                    // We found a valid jump move! Let's do it
                    System.out.println("Auto-move: Moving pile " + (i + 4) +
                            " onto pile " + (i + 1));

                    // Add all cards from right pile to left pile
                    Pile rightPile = piles.get(i + 3);
                    Pile leftPile = piles.get(i);

                    for (Card card : rightPile.getCards()) {
                        leftPile.addCard(card);
                    }

                    // Remove the right pile since we moved all its cards
                    piles.remove(i + 3);

                    // Show the result, update display and return success
                    showPiles();
                    updateCardTableDisplay();
                    return true;
                }
            }
        }

        // STEP 2: Check for moving the last pile back two spaces
        if (piles.size() >= 3) {
            int lastPosition = piles.size() - 1;
            int twoBackPosition = lastPosition - 2;

            Card lastCard = piles.get(lastPosition).getTopCard();
            Card twoBackCard = piles.get(twoBackPosition).getTopCard();

            if (lastCard.getSuit().equals(twoBackCard.getSuit()) ||
                    lastCard.getRank().equals(twoBackCard.getRank())) {

                // Found a valid move for last pile jumping back two spaces
                System.out.println("Auto-move: Moving last pile back over two piles");

                // Move all cards from last pile to the pile two spaces back
                Pile lastPile = piles.get(lastPosition);
                Pile targetPile = piles.get(twoBackPosition);

                for (Card card : lastPile.getCards()) {
                    targetPile.addCard(card);
                }

                // Remove the last pile
                piles.remove(lastPosition);


                showPiles();
                updateCardTableDisplay();
                return true;
            }
        }

        // STEP 3: If no jump moves found, try adjacent moves

        // Look through all adjacent pairs of piles
        for (int i = 0; i < piles.size() - 1; i++) {
            // Get the top cards for comparison
            Card leftCard = piles.get(i).getTopCard();
            Card rightCard = piles.get(i + 1).getTopCard();

            // Check if cards match by suit or rank
            if (leftCard.getSuit().equals(rightCard.getSuit()) ||
                    leftCard.getRank().equals(rightCard.getRank())) {

                // Found a valid adjacent move
                System.out.println("Auto-move: Moving pile " + (i + 2) +
                        " onto pile " + (i + 1));

                // Add all cards from right pile to left pile
                Pile rightPile = piles.get(i + 1);
                Pile leftPile = piles.get(i);

                for (Card card : rightPile.getCards()) {
                    leftPile.addCard(card);
                }

                // Remove the right pile
                piles.remove(i + 1);


                showPiles();
                updateCardTableDisplay();
                return true;
            }
        }

        // If we get here, no moves were possible
        System.out.println("No automatic moves are possible.");
        return false;
    }

    /**
     * this method is used to implement FR9: Play for me many times
     * It calls makeMove() multiple times based on user input
     */
    private void makeManyMoves() {
        // STEP 1: Ask how many times to play
        System.out.print("How many times would you like me to play for you? ");
        // I added error handling here with try-catch for parsing the integer
        int numTimes;
        try {
            numTimes = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        // STEP 2: Counter for moves actually made
        int movesMade = 0;

        // STEP 3: Try to make the requested number of moves
        for (int i = 0; i < numTimes; i++) {
            // Try to make a single move
            boolean moveMade = makeMove();

            // If we made a move, count it
            if (moveMade) {
                movesMade++;
            } else {
                // If no move was possible, stop trying
                break;
            }
        }

        // STEP 4: Tell the user how many moves we made
        System.out.println("Made " + movesMade + " moves automatically.");
    }

    /**
     * this method is used to implement FR10: Show top ten results
     * this method is used retrieves and displays the top 10 scores from the ScoreManager
     */
    private void showTopResults() {
        // Get the top 10 scores from the ScoreManager
        ArrayList<ScoreEntry> topScores = scoreManager.getTopScores(10);

        // Display a header
        System.out.println("\n========== TOP 10 RESULTS ==========");

        // Check if there are any scores to show
        if (topScores.isEmpty()) {
            System.out.println("No scores have been recorded yet.");
            return;
        }

        // Display the scores in order
        for (int i = 0; i < topScores.size(); i++) {
            ScoreEntry entry = topScores.get(i);
            System.out.println((i + 1) + ". " + entry.getPlayerName() + ": " +
                    entry.getPileCount() + " piles");
        }

    }

    /**
     * this method is used to implement FR11: Quit the game
     * this method is used saves the score and performs cleanup before exiting
     */
    private void endGame() {
        if (!piles.isEmpty()) {
            if (piles.size() > 1) {
                System.out.println("Game ended with " + piles.size() + " piles remaining.");
            } else if (piles.size() == 1) {
                System.out.println("You WON! Game ended with 1 pile remaining.");
            }

            // Save the score (piles.size() is the score - fewer piles is better)
            scoreManager.saveScore(playerName, piles.size());

            // Show the top scores
            showTopResults();
        }

        System.out.println("Thanks for playing!");

        if (cardTable != null) {
            cardTable.allDone(); // Tell CardTable we're done
            updateCardTableDisplay(); // Update once more to remove the face-down card
        }
    }

    /**
     * Main method to start the game
     * this for easy testing from the command line
     */
    public static void main(String[] args) {
        MainCMD game = new MainCMD();
        game.start();
    }
}