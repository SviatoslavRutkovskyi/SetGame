package FrontendSwing;

import static Properties.SetProp.ADD_CARDS;
import static Properties.SetProp.UPDATE_BOARD;

import Model.Card;
import Model.Set;
import Properties.SetProp;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SetGUI extends JFrame implements PropertyChangeListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    private final ArrayList<CardPanel> cardPanels = new ArrayList<>();
    private ArrayList<Card> myBoard;
    private final ArrayList<Integer> myCardSet = new ArrayList<>();
    private final Set set;
    private final JPanel setPanel;
    private final PropertyChangeSupport myPcs;
    public SetGUI() {
        super("SET GAME");
//        setBackground(Color.white);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
        set = new Set(9);
        myBoard = set.getBoard();
        myPcs = new PropertyChangeSupport(this);
        setPanel = initSetPanel();
        add(setPanel, BorderLayout.CENTER);
        StatPanel statPanel = new StatPanel();
        statPanel.addPropertyChangeListener(this);
        add(statPanel, BorderLayout.EAST);

        pack();
        setVisible(true);
    }
    private JPanel initSetPanel(){
        JPanel setPanel = new JPanel();
        setPanel.setLayout(new GridLayout(0, 3, 10, 10));
        setPanel.setBackground(Color.GRAY);

        for (int i = 0; i < myBoard.size(); i++) {
            CardPanel newCard = new CardPanel(i);
            newCard.setMyCard(myBoard.get(i));
            newCard.addPropertyChangeListener(this);
            set.addPropertyChangeListener(newCard);
            setPanel.add(newCard);
            cardPanels.add(newCard);
            myPcs.addPropertyChangeListener(newCard);
        }

        return setPanel;
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName());
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
                        while (myBoard.size() < cardPanels.size()){
                            setPanel.remove(cardPanels.size() - 1);
                            cardPanels.remove(cardPanels.size() - 1);
                        }
                        myPcs.firePropertyChange(UPDATE_BOARD.toString(), null, null);
                        pack();
                    }
                }
            } else {
//                System.out.println("Removing a card; Before: " + myCardSet);
                myCardSet.remove(Integer.valueOf((int) evt.getNewValue()));
//                System.out.println("after: " + myCardSet);
            }
        } else if (prop == ADD_CARDS) {
            setBackground(Color.GREEN);
            set.addCards(3);
            myBoard = set.getBoard();
            while (myBoard.size() > cardPanels.size()){
                CardPanel newCard = new CardPanel(cardPanels.size());
                newCard.setMyCard(myBoard.get(cardPanels.size()));
                newCard.addPropertyChangeListener(this);
                set.addPropertyChangeListener(newCard);
                setPanel.add(newCard);
                cardPanels.add(newCard);
                myPcs.addPropertyChangeListener(newCard);
            }
            myPcs.firePropertyChange(UPDATE_BOARD.toString(), null, null);
            pack();
        }
    }
}
