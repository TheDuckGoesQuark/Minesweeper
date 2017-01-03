import javax.swing.*;
import java.awt.*;

/**
 * Created by USER on 02/12/2016.
 */
public class MainFrame extends JFrame {

    static final int WIDTH = 300;
    static final int HEIGHT = 400;
    private static GamePanel GAME_PANEL;
    static MainFrame MAIN_FRAME;
    private static Dimension DIMENSIONS = new Dimension(WIDTH,HEIGHT);

    public static void createMainFrame() {
        MAIN_FRAME = new MainFrame();
        MAIN_FRAME.setName("Minesweeper");
        MAIN_FRAME.setSize(DIMENSIONS);
        MAIN_FRAME.setResizable(false);
        MAIN_FRAME.setLocationRelativeTo(null);
        MAIN_FRAME.setTitle("Minesweeper");
        // Adding panels and window operations
        MAIN_FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addStatusPanel();
        addGamePanel();
        MAIN_FRAME.pack();
        MAIN_FRAME.setVisible(true);
    }

    public static void addStatusPanel() {
        StatusPanel STATUS_PANEL = new StatusPanel();
        MAIN_FRAME.add(STATUS_PANEL, BorderLayout.NORTH);
        STATUS_PANEL.setBounds(0,0,WIDTH,100);
    }

    public static void addGamePanel() {
        GAME_PANEL = new GamePanel();
        MAIN_FRAME.add(GAME_PANEL, BorderLayout.SOUTH);
        GAME_PANEL.setBounds(0,100,WIDTH,HEIGHT-100);
    }

    public static void newGame() {
        StatusPanel.onGoing = true;
        MAIN_FRAME.remove(GAME_PANEL);
        addGamePanel();
        MAIN_FRAME.revalidate();
        MAIN_FRAME.repaint();
    }

    public static void newAIGame() {
        newGame();
        MinesweeperAI bot = new MinesweeperAI(GamePanel.GAME_GRID);
        bot.play();
    }
}
