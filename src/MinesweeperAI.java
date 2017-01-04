import java.util.ArrayList;
import java.util.Random;

/**
 * Created by USER on 11/12/2016 as part of Minesweeper.
 */
public class MinesweeperAI {

    GridSquare[][] gameGrid;
    int[][] gameGridRisk;
    GridSquare currentSquare;

    MinesweeperAI(GridSquare[][] gameGrid) {
        this.gameGrid = gameGrid;
        // picks random starting point
        Random r = new Random();
        int i = r.nextInt(gameGrid.length), j = r.nextInt(gameGrid[0].length);
        currentSquare = gameGrid[i][j];
    }

    void play() {
        for (int k = 0; k < 100; k++) {
            if (StatusPanel.onGoing) {
                currentSquare.artificialClick();
                assessBoard();
                chooseNextMove();
                printRiskGrid();
            }
        }
    }

    private void assessBoard() {
        gameGridRisk = new int[gameGrid.length][gameGrid[0].length];
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                gameGridRisk[i][j] = 0;
                scoreUsingAdjacent(i, j);
                markKnownMines();
            }
        }
        markKnownMines();
    }

    // TODO add check to see if adjacent minecount has been flagged in adjacent squares
    // thus setting a square to 0 risk, so that the rest around it can be clicked.
    private void scoreUsingAdjacent(int i, int j) {
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (GamePanel.withinXIndex(l) && GamePanel.withinYIndex(k)) {
                    // Confirms AI should be able to see square, assumes risk otherwise
                    if (!gameGrid[k][l].isClicked()) {
                        gameGridRisk[i][j]++;
                    } else {
                        if (gameGrid[k][l].adjacentMineCount == 0) {
                            gameGridRisk[i][j] = 0;
                            return;
                        } else {
                            gameGridRisk[i][j]++;
                        }
                    }
                }
            }
        }
    }

    //TODO make grid iterator cause these for loops are disgusting
    private void markKnownMines() {
        // find 1s
        // check if there is only 1 available space
        // if so, flag
        // find 2s
        // ...
        int adjacentCount = 1;
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (!gameGrid[i][j].isClicked()) {
                    break;
                }
                if (gameGrid[i][j].adjacentMineCount == adjacentCount) {
                    ArrayList<int[]> spaces = findFreeSpaces(i, j);
                    if (spaces.size() == adjacentCount) {
                        flagSpaces(spaces);
                    }
                }
            }
        }
    }

    private void flagSpaces(ArrayList<int[]> spaces) {
        for (int i = 0; i < spaces.size(); i++) {
            int[] index = spaces.get(i);
            gameGrid[index[0]][index[1]].artificialFlag();
        }
    }

    private ArrayList<int[]> findFreeSpaces(int i, int j) {
        ArrayList<int[]> spaces = new ArrayList<>();
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (GamePanel.withinXIndex(l) && GamePanel.withinYIndex(k)) {
                    if (!gameGrid[k][l].isClicked()) {
                        spaces.add(new int[]{k, l});
                    }
                }
            }
        }
        return spaces;
    }


    private void chooseNextMove() {
        int minRisk = 0;
        boolean moveFound = false;
        while (!moveFound) {
            for (int i = 0; i < gameGridRisk.length; i++) {
                for (int j = 0; j < gameGridRisk[0].length; j++) {
                    if (gameGridRisk[i][j] == minRisk && !gameGrid[i][j].isClicked() && !gameGrid[i][j].isFlagged()) {
                        currentSquare = gameGrid[i][j];
                        return;
                    }
                }
            }
            minRisk++;
        }
    }

    private void printRiskGrid() {
        System.out.println("");
        for (int i = 0; i < gameGridRisk.length; i++) {
            System.out.println("");
            for (int j = 0; j < gameGridRisk[0].length; j++) {
                if (gameGrid[i][j].isClicked()) {
                    System.out.print("# ");
                } else {
                    System.out.print(gameGridRisk[i][j] + " ");
                }
            }
        }
    }
}
