import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by USER on 02/12/2016 as part of Minesweeper.
 */
class StatusPanel extends JPanel {

    private Dimension prefButtonSize = new Dimension(100,40);
    static boolean onGoing = true;

    StatusPanel() {
        this.setName("Status");
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(MainFrame.WIDTH,50));
        this.setBackground(Color.DARK_GRAY);
        // Buttons
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> MainFrame.newGame());
        newGameButton.setPreferredSize(prefButtonSize);
        this.add(newGameButton);

        JButton AIGameButton = new JButton("AI Game");
        AIGameButton.addActionListener(e -> MainFrame.newAIGame());
        AIGameButton.setPreferredSize(prefButtonSize);
        this.add(AIGameButton);
    }
}
