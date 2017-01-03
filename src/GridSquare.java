import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by USER on 02/12/2016 as part of Minesweeper.
 */
class GridSquare extends JButton {
    private final String flagIcon = "F";
    private final String mineIcon = "@";

    boolean mine = false, clicked = false, flagged = false;
    int adjacentMineCount;

    GridSquare() {
        this.setVisible(true);
        this.setMargin(new Insets(0,0,0,0));
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    setClicked(true);
                    updateIcon();
                    if (isMine()) {
                        JOptionPane.showMessageDialog(MainFrame.MAIN_FRAME, "BOOM!");
                        StatusPanel.onGoing = false;
                    } else {
                        if(GamePanel.checkForVictory()) {
                            JOptionPane.showMessageDialog(MainFrame.MAIN_FRAME, "You win!");
                            StatusPanel.onGoing = false;
                        }
                    }
                } else if(SwingUtilities.isRightMouseButton(e)){
                    setFlagged(!isFlagged());
                    updateIcon();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    void artificialClick() {
        MouseEvent me = new MouseEvent(this, MouseEvent.MOUSE_PRESSED,10,InputEvent.BUTTON1_MASK, 100,100,1,false);
        for(MouseListener ml : this.getMouseListeners()) {
            ml.mousePressed(me);
        }
    }

    void artificialFlag() {
        MouseEvent me = new MouseEvent(this, MouseEvent.MOUSE_PRESSED,10,InputEvent.BUTTON3_MASK, 100,100,1,false);
        for(MouseListener ml : this.getMouseListeners()) {
            ml.mousePressed(me);
        }
    }

    void updateIcon() {
        if(isClicked()) {
            if(isMine()) {
                setText(mineIcon);
            } else {
                setText(Integer.toString(adjacentMineCount));
            }
        } else {
            if(isFlagged()) {
                setText(flagIcon);
            } else {
                setText(" ");
            }
        }
    }

    boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    boolean isFlagged() {
        return flagged;
    }

    private void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    private boolean isMine() {
        return mine;
    }

    void setMine(boolean mine) {
        this.mine = mine;
    }

    int getAdjacentMineCount() {
        return adjacentMineCount;
    }

    void setAdjacentMineCount(int adjacentMineCount) {
        this.adjacentMineCount = adjacentMineCount;
    }
}
