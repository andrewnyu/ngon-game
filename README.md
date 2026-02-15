# ngon-game

A word game built in Java Swing. Click on letter tiles (drawn as N-sided polygons) to spell words from the dictionary. Each tile has a score based on its number of sides; find valid words before you run out of mistakes.

## Screenshot

The game shows a grid of colored polygons (NGons), each displaying a letter. Build words by clicking tiles, then submit. Your score is the sum of the tile scores for each valid word.

## Requirements

- **Java** (JDK 8 or later; uses `javac` and `java`)

## How to Run

From the project root:

```bash
javac *.java
java Starter
```

The window opens at 1024×768 with the title **WordMap**.

## How to Play

1. **Build a word** — Click letter tiles (the NGons) in order. The current word and its score appear at the top.
2. **Submit** — Click **Submit** to check your word. If it’s in the dictionary, you get the word’s score and the word is removed from the pool.
3. **Clear** — Remove the last letter from your current word (limited number of uses).
4. **Clear All** — Clear the entire current word (uses your clear budget).
5. **Refresh** — Shuffle all tiles (limited number of uses).
6. **End Game** — End the game and see your stats.

**Limits (default):**

- **Mistakes:** 5 wrong submissions → game over  
- **Refreshes:** 10 board refreshes  
- **Clears:** 60 single-letter clears  

## Project Structure

| File         | Role |
|-------------|------|
| `Starter.java`  | Entry point; launches the game window. |
| `MyFrame.java`  | Main `JFrame`; holds the canvas and control panel. |
| `MainFrame.java`| Game canvas: grid of NGons, word list, score, and rendering. |
| `NGon.java`     | N-sided regular polygon with a letter and score (score = number of sides). |
| `Module.java`   | Control panel (buttons) and mouse handling for tile clicks. |

## Dictionary

The game uses a fixed word list (e.g. *danaerys*, *jon*, *snow*, *ghost*, *arya*, *dragon*, *throne*, etc.). Valid words are only from this list. Letter tiles are generated with a frequency distribution derived from these words.

## Author

Andrew Yu  

Repository: [github.com/andrewnyu/ngon-game](https://github.com/andrewnyu/ngon-game)
