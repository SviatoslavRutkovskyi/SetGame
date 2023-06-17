package FrontendSwing;

import Model.Card;
import Model.Set;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class StatPanel extends JPanel{
    private ArrayList<Card> myBoard;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    public StatPanel() {
        super();
        Set set = new Set(9);
        myBoard = set.getBoard();
        setBackground(Color.RED);
       // setPreferredSize(new Dimension(WIDTH, HEIGHT));
        init();
    }
    private void init() {
    }
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


    }
}


