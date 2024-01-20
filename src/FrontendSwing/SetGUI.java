 package FrontendSwing;

import static Properties.SetProp.*;

import Model.Card;
import Model.Set;
import Properties.SetProp;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class SetGUI extends JFrame implements PropertyChangeListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    /**
     * Array of cardPanel classes.
     */
    private final ArrayList<CardPanel> cardPanels = new ArrayList<>();
    /**
     * Array that stores current board position.
     */
    private ArrayList<Card> myBoard;
    /**
     * Array that contains indexes of selected cards
     */
    private final ArrayList<Integer> myCardSet = new ArrayList<>();
    /**
     * Set controller class
     */
    private final Set set;
    /**
     * Panel that contains all set cards
     */
    private final JPanel setPanel;

    /**
     * Currently unused.
     */
    private final PropertyChangeSupport myPcs;

    /** How to play JFrame. */
    private JFrame myHowToPlayOption;

    private JButton checkSetCount;
    public SetGUI() {
        super("SET GAME");
//        setBackground(Color.white);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
        setJMenuBar(new SetFrameMenu(this));
        setMenuOptions();
        setResizable(false);


        set = new Set(9);
        myBoard = set.getBoard();
        myPcs = new PropertyChangeSupport(this);
        setPanel = initSetPanel();



        add(setPanel, BorderLayout.CENTER);
//        add(new FindThirdSet(), BorderLayout.CENTER);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());

        JPanel controlPanel = createControlPanel();
        sidePanel.add(controlPanel, BorderLayout.NORTH);

        StatPanel statPanel = new StatPanel();
        myPcs.addPropertyChangeListener(statPanel);
        set.addPropertyChangeListener(statPanel);
        sidePanel.add(statPanel, BorderLayout.CENTER);

        add(sidePanel, BorderLayout.EAST);

        pack();

        setVisible(true);
    }

    /**
     * Creates the setPanel and returns it
     * @return JPanel setPanel
     */
    private JPanel initSetPanel(){
        JPanel setPanel = new JPanel();
        setPanel.setLayout(new GridLayout(0, 3, 10, 10));
        setPanel.setBackground(new Color(213, 162, 232));

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
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0,1,10,10));
//        controlPanel.setBackground(new Color(213, 162, 232));
        controlPanel.setBorder(BorderFactory.createLineBorder(new Color(213, 162, 232), 10));

        AtomicInteger setCount = new AtomicInteger();

        JButton addCards = new JButton("ADD CARDS");
        addCards.setPreferredSize(new Dimension(120,75));
        addCards.addActionListener(e -> {
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

            checkSetCount.setText("CHECK SETS");
            myPcs.firePropertyChange(ADD_CARDS.toString(), null, null);
            pack();
        });
        checkSetCount = new JButton("CHECK SETS");
        checkSetCount.setPreferredSize(new Dimension(120,75));
        checkSetCount.addActionListener(e -> {
            setCount.set(set.findSets());
            checkSetCount.setText(setCount + " SETS");

        });
        controlPanel.add(addCards);
        controlPanel.add(checkSetCount);
        setVisible(true);
        return controlPanel;
    }

    private void setMenuOptions() {
        final int howToWidth = 500;
        final int howToHeight = 500;

        //setup how to play menu
        myHowToPlayOption = new JFrame("How To Play");
        final JLabel howToPlay = new JLabel(textReader("src/Resources/HowToPlay.txt"));
        howToPlay.setFont(new Font(Font.SERIF, Font.PLAIN,  20));
        myHowToPlayOption.setSize(howToWidth, howToHeight);
        myHowToPlayOption.setLocationRelativeTo(null);
        myHowToPlayOption.setResizable(false);
        myHowToPlayOption.add(howToPlay);
       }

    /**
     * Calls public visibility method in how to play Jframe.
     */
    public void makeHowToPlayVisible() {
        myHowToPlayOption.setVisible(true);
    }

    /**
     * Listens to cardPanel classes, and statPanel and controls set class accordingly.
     * Calls a set if 3 cards are selected.
     * Adds 3 cards if add Cards is called.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        System.out.println(evt.getPropertyName());
        final SetProp prop = SetProp.valueOf(evt.getPropertyName());
//        System.out.println("Property Change called, selected: " + evt.getOldValue() + " id: " + evt.getNewValue());
        if (prop == CARD_SELECT) {

            if (evt.getOldValue().equals(true)) {
                myCardSet.add((int) evt.getNewValue());
            } else {
//                System.out.println("Removing a card; Before: " + myCardSet);
                myCardSet.remove(Integer.valueOf((int) evt.getNewValue()));
//                System.out.println("after: " + myCardSet);
            }
                if (myCardSet.size() == 3) {
                    if (set.callSet(myCardSet.get(0), myCardSet.get(1), myCardSet.get(2))) {

                        checkSetCount.setText("CHECK SETS");

//                        System.out.println(myCardSet);
                        myCardSet.clear();
                        myBoard = set.getBoard();
                        for (int i = 0; i < myBoard.size(); i++) {
                            cardPanels.get(i).setMyCard(myBoard.get(i));
                        }
                        while (myBoard.size() < cardPanels.size()){
                            setPanel.remove(cardPanels.size() - 1);
                            cardPanels.remove(cardPanels.size() - 1);
                        }
//                        myPcs.firePropertyChange(UPDATE_BOARD.toString(), null, null);
                        pack();
                    }
                }

        }
    }
    private String textReader(final String theFilePath)  {
        final StringBuilder fileData = new StringBuilder();
        final String htmlBreak = "<html>";
        final String brBreak = "<br/<";
        try {
            final File file = new File(theFilePath);
            final Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                final String textLine = scan.nextLine();
                fileData.append(htmlBreak);
                fileData.append(textLine);
                fileData.append(brBreak);
            }
            scan.close();
            return fileData.toString();
        } catch (final FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
