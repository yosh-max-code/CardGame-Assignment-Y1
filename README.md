# Patience Card Game

A Java-based implementation of the Patience card game, demonstrating object-oriented programming principles and game logic design.

## Overview

This project implements a console-based Patience card game where the objective is to consolidate multiple decks of cards into a single deck through strategic moves. Players must match cards based on suit and rank rules to successfully complete the game.

## Game Rules

**Objective**: Reduce multiple card decks down to a single deck by making valid moves.

**Valid Moves**:
- Cards can be matched based on **suit** (e.g., Hearts to Hearts)
- Cards can be matched based on **rank** (e.g., 7 to 7)
- Specific move rules depend on card positions and available matches

**Win Condition**: Successfully consolidate all cards into one deck.

## Features

- **Object-Oriented Design**: Implements core OOP principles
  - Encapsulation of card and deck properties
  - Inheritance for different card types or game states
  - Modular class structure for maintainability

- **Move Validation System**: Checks legality of moves based on suit and rank matching rules

- **Game State Management**: Tracks current deck configuration and valid move options

- **Error Handling**: Validates user input and prevents illegal moves

## Technical Implementation

### Core Classes

The project utilizes several key classes (specific implementation may vary):

- **Card**: Represents individual playing cards with suit and rank properties
- **Deck**: Manages collections of cards and deck operations
- **Game**: Controls game flow, move validation, and win conditions
- **Player**: Handles user input and move execution

### OOP Principles Demonstrated

- **Encapsulation**: Private fields with public getter/setter methods
- **Inheritance**: Class hierarchies for card types or game components
- **Abstraction**: Simplified interfaces for complex game operations
- **Polymorphism**: Method overriding for different card behaviors (if applicable)

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line terminal or Java IDE

### Compilation
```bash
javac src/*.java
```

### Execution
```bash
java -cp src Main
```

## How to Play

1. **Start Game**: Run the program to initialize the card decks
2. **View State**: The current deck configuration will be displayed
3. **Make Moves**: Enter commands to match cards based on suit or rank
4. **Win**: Successfully reduce all cards to a single deck

### Example Gameplay
```
Current Decks: 5
Deck 1: [7♥, K♠, 3♦]
Deck 2: [7♣, A♥, 9♠]
...

Enter move: match 1 2 by rank
Valid move! Cards matched.

Current Decks: 4
...
```

## Project Structure

```
patience-card-game/
├── README.md
├── src/
│   ├── Main.java          # Entry point
│   ├── Card.java          # Card representation
│   ├── Deck.java          # Deck management
│   ├── Game.java          # Game logic
│   └── (other classes)
└── docs/                  # Javadoc documentation (optional)
```

## Academic Context

This project was completed as part of the **Object-Oriented Programming** module at **Aberystwyth University** (2024). It demonstrates practical application of:

- Object-oriented design patterns
- Java programming fundamentals
- Algorithm implementation for game logic
- Software testing and debugging
- Code documentation practices

## Development Notes

### Challenges Addressed
- Implementing efficient move validation algorithms
- Managing game state across multiple decks
- Designing intuitive user input system
- Handling edge cases and invalid moves

### Key Learning Outcomes
- Practical experience with Java OOP concepts
- Understanding of game loop design patterns
- Experience with complex state management
- Application of software design principles

## Future Enhancements

Potential improvements could include:
- Graphical user interface (GUI) implementation
- AI opponent or hint system
- Multiple difficulty levels or rule variations
- Save/load game functionality
- Score tracking and statistics
- Undo/redo move capability

## Documentation

Full Javadoc documentation is available in the `docs/` folder (if included). To generate Javadocs:

```bash
javadoc -d docs src/*.java
```

## Author

**Yash Singh**  
Computer Science and Artificial Intelligence Student  
Aberystwyth University

**Contact**: yash.sujit.singh@outlook.com  
**GitHub**: [github.com/yosh-max-code](https://github.com/yosh-max-code)

## License

Academic project - completed as coursework at Aberystwyth University.

## Acknowledgments

Project specification and requirements provided by Aberystwyth University Computer Science department.
