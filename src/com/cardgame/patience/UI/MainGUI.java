/**
 *
 * @author YASH SINGH
 * @version 3.0
 */

import java.util.ArrayList;
import java.util.Scanner;

import com.cardgame.patience.model.*;
import com.cardgame.patience.persistence.ScoreEntry;
import com.cardgame.patience.persistence.ScoreManager;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.aber.dcs.cs12320.cards.gui.javafx.CardTable;

/**
 * I created this class to implement the graphical interface requirement (NFR3).
 *  * It follows a similar structure to MainCMD but integrates with JavaFX
 *  * This main file is strictly for gui whereas the other CMD GAME java file works with a menu for command line
 */
public class MainGUI extends Application {

    private CardTable cardTable;
    private Deck deck;
    private ArrayList<Pile> piles;
    private Scanner scanner;
    private ScoreManager scoreManager;
    private String playerName;

    @Override
    public void start(Stage stage) {
        cardTable = new CardTable(stage);

        // Initialize the card display right away with the card back
        ArrayList<String> initialCards = new ArrayList<>();
        // Add the card back to show immediately
        cardTable.cardDisplay(initialCards);

        // The interaction with this game is from a command line
        // menu. We need to create a separate non-GUI thread
        // to run this in. DO NOT REMOVE THIS.
        Runnable commandLineTask = () -> {
            // Initialize the game
            initGame();

            // Start the command line interface
            startCommandLine();
        };
        Thread commandLineThread = new Thread(commandLineTask);
        // This is how we start the thread.
        // This causes the run method to execute.
        commandLineThread.start();
    }

    /**
     * Initialize the game components
     */
    private void initGame() {
        deck = new Deck();
        piles = new ArrayList<>();
        scanner = new Scanner(System.in);
        scoreManager = new ScoreManager();

        // Get the player's name
        System.out.print("Please enter your name: ");
        playerName = scanner.nextLine();
        System.out.println("Welcome, " + playerName + "!");

    }

    /**
     * This method is used to populate the deck with all 52 cards
     */
    private void createNewDeck() {
        // Create a new deck with all 52 cards
        String[] suits = {"H", "C", "D", "S"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (int i = 0; i < suits.length; i++) {
            String currentSuit = suits[i];
            for (int j = 0; j < ranks.length; j++) {
                String currentRank = ranks[j];
                deck.addCard(new Card(currentSuit, currentRank));
            }
        }
        System.out.println("Created a new deck of 52 cards.");
    }

    /**
     * This method is used to handle the main game loop
     * It displays the menu and processes user commands
     */
    private void startCommandLine() {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                showPack();
            } else if (choice.equals("2")) {
                shuffleCards();
            } else if (choice.equals("3")) {
                dealCard();
            } else if (choice.equals("4")) {
                moveLastPileOntoPrevious();
            } else if (choice.equals("5")) {
                moveLastPileOverTwo();
            } else if (choice.equals("6")) {
                amalgamateCards();
            } else if (choice.equals("7")) {
                showPiles();
            } else if (choice.equals("8")) {
                makeMove();
            } else if (choice.equals("9")) {
                makeManyMoves();
            } else if (choice.equals("10")) {
                showTopResults();
            } else if (choice.equals("Q") || choice.equals("q")) {
                endGame();
                running = false;
            } else {
                System.out.println("That feature is not implemented yet.");
            }

            System.out.println();
        }
    }

    /**
     * This method is used to display the game menu options
     * like in the Main CMD class
     */
    private void displayMenu() {
        System.out.println("========== PATIENCE CARD GAME ==========");
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
     * This method is used to implement FR1: Show the full pack
     */
    private void showPack() {
        if (deck.size() == 0) {
            System.out.println("The deck is empty. Creating a new one.");
            createNewDeck();
        }

        System.out.println("Current deck:");
        deck.showPack();
    }

    /**
     * This method is used to implement FR2: Shuffle the cards
     */
    private void shuffleCards() {
        if (deck.size() == 0) {
            System.out.println("The deck is empty. Creating a new one before shuffling.");
            createNewDeck();
        }

        deck.shuffle();
        System.out.println("The deck has been shuffled.");
    }

    /**
     * This method is used to implement FR3: Deal a card
     * It also updates the GUI to show the new card
     */
    private void dealCard() {
        if (deck.size() == 0) {
            System.out.println("The deck is empty. Cannot deal a card.");
            return;
        }

        // Deal a card from the deck
        Card card = deck.dealCard();

        // Create a new pile with this card
        Pile newPile = new Pile();
        newPile.addCard(card);
        piles.add(newPile);

        System.out.println("Dealt: " + card);
        showPiles();

        // Update the GUI
        updateCardTableDisplay();
    }

    /**
     * This method is used to implement FR4: Move last pile onto previous one
     */
    private void moveLastPileOntoPrevious() {
        if (piles.size() < 2) {
            System.out.println("Need at least 2 piles to make this move.");
            return;
        }

        Pile lastPile = piles.get(piles.size() - 1);
        Pile previousPile = piles.get(piles.size() - 2);

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

        showPiles();
        updateCardTableDisplay();
    }

    /**
     * This method is used to implement FR5: Move last pile back over two piles
     */
    private void moveLastPileOverTwo() {
        if (piles.size() < 3) {
            System.out.println("Need at least 3 piles to make this move.");
            return;
        }

        Pile lastPile = piles.get(piles.size() - 1);
        Pile overTwoPile = piles.get(piles.size() - 3);

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
     * This method is used to implement FR6: Amalgamate piles in the middle
     */
    private void amalgamateCards() {
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

            // Validate the input
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
     * This method is used to implement FR7: Show all cards in play
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
     * This method is used to implement FR8: Play for me once
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
     * This method is used to implement FR9: Play for me many times
     * It calls makeMove() multiple times based on user input
     */
    private void makeManyMoves() {
        // STEP 1: Ask how many times to play
        System.out.print("How many times would you like me to play for you? ");
        // Added error handling here with try-catch for parsing the integer
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
     * This method is used to implement FR10: Show top ten results
     * This method retrieves and displays the top 10 scores from the ScoreManager
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
     * This method is used to implement FR11: Quit the game
     * This method saves the score and performs cleanup before exiting
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
     * This method is used to update the card display in the GUI
     * It converts the model data to the format needed by CardTable
     */
    private void updateCardTableDisplay() {
        if (cardTable == null) return;

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

    // //////////////////////////////////////////////////////////////
    public static void main(String args[]) {
        Application.launch(args);
    }
}