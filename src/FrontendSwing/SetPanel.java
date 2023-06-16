package FrontendSwing;

import Model.Card;
import Model.Set;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SetPanel extends JPanel {
    
    private ArrayList<Card> myBoard;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    public SetPanel() {
        super();
        Set set = new Set(9);
        myBoard = set.getBoard();
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        init();
    }
    private void init() {
        ArrayList<CardPanel> cardPanels = new ArrayList<>();
        for (int i = 0; i < myBoard.size(); i++) {
            CardPanel newCard = new CardPanel();
            newCard.setMyCard(myBoard.get(i));
            add(newCard);
            cardPanels.add(newCard);

        }
    }
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


    }
}
