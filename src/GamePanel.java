import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by USER on 02/12/2016.
 */
class GamePanel extends JPanel {

    private static int NUMBER_OF_MINES = 10;
    static GridSquare[][] GAME_GRID;

    GamePanel() {
        this.setName("Game");
        this.setLayout(new GridLayout(10, 10));
        this.setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.HEIGHT - 50));
        this.setBackground(Color.RED);
        // Adds button grid
        GAME_GRID = createGameGrid(10, 10);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.add(GAME_GRID[i][j]);
            }
        }
        // Adds check for victory on mouseclick
    }

    private GridSquare[][] createGameGrid(int width, int height) {
        GridSquare[][] gameGrid = new GridSquare[width][height];
        // Initialises GridSquare objects
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                gameGrid[i][j] = new GridSquare();
            }
        }
        // Adds mines
        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_MINES; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            gameGrid[y][x].setMine(true);
            gameGrid = updateAdjacentMines(gameGrid, y, x);
        }

        return gameGrid;
    }

    private GridSquare[][] updateAdjacentMines(GridSquare[][] gameGrid, int y, int x) {
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if(i >= 0 && i < gameGrid[0].length && j >= 0 && j < gameGrid.length) {
                    gameGrid[i][j].setAdjacentMineCount(gameGrid[i][j].getAdjacentMineCount()+1);
                    gameGrid[i][j].updateIcon();
                }
            }
        }
        return gameGrid;
    }

    static boolean checkForVictory() {
        int clickedCounter = 0;
        int gridCounter = 0;

        for (GridSquare[] row : GAME_GRID) {
            for (int j = 0; j < GAME_GRID[0].length; j++) {
                if (row[j].isClicked()) {
                    clickedCounter++;
                }
                gridCounter++;
            }
        }

        return clickedCounter == (gridCounter - NUMBER_OF_MINES);
    }

    static boolean withinYIndex(int y) {
        return !(y >= GAME_GRID.length || y < 0);
    }

    static boolean withinXIndex(int x) {
        return !(x >= GAME_GRID[0].length || x < 0);
    }
}
