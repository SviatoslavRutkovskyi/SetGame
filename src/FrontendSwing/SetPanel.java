package FrontendSwing;

import static Properties.SetProp.*;
import Model.Card;
import Model.Set;
import Properties.SetProp;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SetPanel extends JPanel implements PropertyChangeListener {
    
    private ArrayList<Card> myBoard;
    private final ArrayList<Integer> myCardSet = new ArrayList<>();
    private final Set set;
    private final ArrayList<CardPanel> cardPanels;

    private final PropertyChangeSupport myPcs;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    public SetPanel() {
        super();
        set = new Set(9);
        myBoard = set.getBoard();
        cardPanels = new ArrayList<>();
        myPcs = new PropertyChangeSupport(this);
        setBackground(Color.GRAY);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridLayout(0, 3, 10, 10));
        init();
    }
    private void init() {
        for (int i = 0; i < myBoard.size(); i++) {
            CardPanel newCard = new CardPanel(i);
            newCard.setMyCard(myBoard.get(i));
            newCard.addPropertyChangeListener(this);
            myPcs.addPropertyChangeListener(newCard);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SetProp prop = SetProp.valueOf(evt.getPropertyName());
//        System.out.println("Property Change called, selected: " + evt.getOldValue() + " id: " + evt.getNewValue());
        if (prop == SetProp.CARD_SELECT) {
            if (evt.getOldValue().equals(true)) {
                myCardSet.add((int) evt.getNewValue());
                if (myCardSet.size() == 3) {
                    if (set.callSet(myCardSet.get(0), myCardSet.get(1), myCardSet.get(2))) {
                        System.out.println(myCardSet);
                        myCardSet.clear();
                        myBoard = set.getBoard();
                        for (int i = 0; i < myBoard.size(); i++) {
                            cardPanels.get(i).setMyCard(myBoard.get(i));
                        }
                        myPcs.firePropertyChange(UPDATE_BOARD.toString(), null, null);
                    }
                }
            } else {
//                System.out.println("Removing a card; Before: " + myCardSet);
                myCardSet.remove(Integer.valueOf((int) evt.getNewValue()));
//                System.out.println("after: " + myCardSet);
            }
        } else if (prop == ADD_CARDS) {
            set.addCards(3);
            myPcs.firePropertyChange(UPDATE_BOARD.toString(), null, null);
        }
    }

}
