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

## Mathematics of NGon generation

Each letter tile is a **regular N-gon** (polygon with \(n\) equal sides and angles). The number of sides \(n\) is chosen at random in \([4, 16]\) per tile and also equals the **tile’s score**. The shape is determined by a target **area** (`size`) and the **center** \((x, y)\).

### Side length from area

For a regular \(n\)-gon with area `size`, the **side length** \(s\) is:

\[
s = \sqrt{\frac{2 \cdot \mathrm{size} \cdot \tan(180°/n)}{n}}
\]

This comes from the area formula for a regular polygon: area = \(\frac{1}{2} n \cdot s \cdot a\) with apothem \(a\), and the relation between \(s\) and \(a\) below.

### Apothem

The **apothem** \(a\) is the distance from the center to the midpoint of any side:

\[
a = \frac{s}{2 \tan(180°/n)}
\]

The game uses this for **click detection**: a click at \((p_x, p_y)\) is inside the NGon if \(|p_x - x| \le a\) and \(|p_y - y| \le a\) (bounding-box style test).

### Vertex positions

Vertices are built by walking around the polygon. The first vertex is at the **top** of the polygon: \((x,\, y - a)\). Each subsequent vertex is reached by stepping **side length** \(s\) in the direction of the \(i\)-th exterior angle:

- Start: \((x - s/2,\; y - a)\)
- For \(i = 0, 1, \ldots, n\!-\!1\): add \(\bigl(s \cos(360° \cdot i / n),\; s \sin(360° \cdot i / n)\bigr)\) to get the next vertex.

So the polygon is drawn with one vertex at the top and edges of length \(s\) turning by \(360°/n\) at each vertex.

### Color

Color is chosen from \(n\): for \(n \le 16\), RGB is \(\bigl(\lfloor 210 \cdot n/16 \rfloor,\; 45,\; 210 - \lfloor 210 \cdot n/16 \rfloor\bigr)\) (red–blue gradient); for \(n > 16\) the tile is drawn in blue.

---

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
