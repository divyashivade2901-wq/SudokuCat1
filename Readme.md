# 🐱 SudokuCat

**SudokuCat** is a fun and interactive puzzle game developed in **Java using Swing GUI**. The game is inspired by cat-based logic puzzles, where the player has to find hidden cats while following a set of logical rules.

The game combines **logic, observation, and strategy** with a cute cat-themed interface.

## 🎮 Game Rules

The objective is to find all the hidden cats on the board.

The player must follow these rules:

* 🐱 There must be **one cat in each row**.
* 🐱 There must be **one cat in each column**.
* 🎨 There must be **one cat in each colored region**.
* 🚫 Cats **cannot touch each other**, even diagonally.
* ❤️ The player gets **3 lives**.
* ❌ Selecting the same wrong cell twice marks it with a red **X**.
* 🏆 Find all the cats to complete the puzzle.

## ✨ Features

* 🐱 Cute cat-themed puzzle game
* 🎯 Multiple difficulty levels
* 🎨 Irregular colored regions
* 🧩 Logic-based puzzle solving
* ❤️ Three lives system
* ❌ Wrong-cell detection
* ⏱️ Game timer
* 📊 Move counter
* 🏆 Winning screen
* 💀 Game-over screen
* 🔄 Retry / Play Again option
* 🎲 Randomized puzzle layouts
* 🖥️ Java Swing graphical user interface

## 🎚️ Difficulty Levels

SudokuCat provides different difficulty levels to make the game more challenging as the player progresses.

| Level     | Board Size | Difficulty   |
| --------- | ---------- | ------------ |
| 🟢 Easy   | 4 × 4      | Beginner     |
| 🟡 Medium | 6 × 6      | Intermediate |
| 🔴 Hard   | 8 × 8      | Advanced     |

The number of regions and puzzle complexity increases with the difficulty level while maintaining logical and solvable puzzles.

## 🛠️ Technologies Used

* **Java**
* **Java Swing**
* **AWT**
* **Object-Oriented Programming (OOP)**
* **Randomized Puzzle Generation**
* **Event Handling**

## 💻 Requirements

To run SudokuCat, you need:

* Java JDK 17 or later
* A Java-compatible IDE or code editor
* Windows, macOS, or Linux

The project can be run using:

* VS Code
* IntelliJ IDEA
* Eclipse
* Command Prompt / Terminal

## 🚀 How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/SudokuCat.git
```

### 2. Open the Project

Open the downloaded project folder in your preferred Java IDE or VS Code.

### 3. Compile the Program

If the main file is `SudokuCat.java`, open the terminal inside the project folder and run:

```bash
javac SudokuCat.java
```

### 4. Run the Game

```bash
java SudokuCat
```

Alternatively, you can run the program directly using the **Run** button in VS Code or your IDE.

## 🎮 How to Play

1. Start the game.
2. Select a difficulty level.
3. Study the colored regions and board.
4. Click on a cell where you think a cat is hidden.
5. Use the row, column, region, and diagonal rules to make logical decisions.
6. Avoid selecting incorrect cells.
7. Find all the hidden cats before losing all three lives.
8. Complete the puzzle to reach the **Win Screen**.

## 🧠 Game Logic

The puzzle is based on constraint-solving rules.

For every cat placed on the board:

```text
One cat per row
One cat per column
One cat per colored region
No two cats can touch, even diagonally
```

These rules allow the player to logically determine the possible locations of hidden cats.

## 📂 Project Structure

```text
SudokuCat/
│
├── SudokuCat.java
├── README.md
└── ...
```

Additional files such as images or resources may be included depending on the project version.

## 🏆 Objective

The main objective of SudokuCat is to complete the puzzle by finding all hidden cats while following all the game rules and using the minimum number of incorrect attempts.

The game is designed to make logical puzzle solving more enjoyable through a **cat-themed graphical interface**.

## 👩‍💻 Project

**Project Name:** SudokuCat
**Language:** Java
**GUI:** Java Swing
**Project Type:** Puzzle Game

Developed as a **Java academic project**.

---

## 📌 Future Improvements

Possible future improvements include:

* 🔊 Sound effects and background music
* 🐾 More cat animations
* 🌟 More difficulty levels
* 🏅 High-score system
* 💾 Save and resume game
* 📱 Improved responsive interface
* 🎨 More themes and customization options

---

## 📄 License

This project was created for **educational and academic purposes**.
