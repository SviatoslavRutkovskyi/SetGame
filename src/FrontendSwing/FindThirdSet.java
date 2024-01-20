package FrontendSwing;

import Model.Card;
import Model.Set;
import Properties.Number;
import Properties.Opacity;
import Properties.Shape;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

public class FindThirdSet extends JFrame {
    private Set mySet;

    private Card myCard1;
    private Card myCard2;
    private Card myFinalCard;
    private CardPanel cardPanel1;
    private CardPanel cardPanel2;
    public FindThirdSet() {
        mySet = new Set(2);

        setLayout(new BorderLayout());
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        setPreferredSize(new Dimension(550,250));
        JPanel twoCardPanel = createCardPanel();
        JPanel selector = createSelectorPanel();
        JPanel infoPanel = createInfoPanel();

        add(twoCardPanel, BorderLayout.CENTER);
        add(selector, BorderLayout.EAST);
//        add(infoPanel, BorderLayout.NORTH);
        setResizable(false);
        pack();
        setVisible(true);
    }
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        JTextArea howToPlay = new JTextArea("Pick a Card that would form a set with the 2 cards provided on the left. " +
                "Then click the SELECT button to confirm your choice. " +
                "Remember, in order to create a set, each property needs to either fully match across all 3 cards, " +
                "or be different for each card. Once this is the case for all 4 properties, a set is formed. " +
                "Have fun! ");
//        howToPlay.setColumns(50);
        howToPlay.setLineWrap(true);
        howToPlay.setWrapStyleWord(true);
        howToPlay.setFocusable(false);
        infoPanel.add(howToPlay);
        return infoPanel;
    }
    private JPanel createCardPanel() {
        JPanel twoCardPanel = new JPanel();
        twoCardPanel.setLayout(new GridLayout(2,1,10, 10));
        twoCardPanel.setBackground(new Color(213, 162, 232));

        ArrayList<Card> board = mySet.getBoard();


        cardPanel1 = new CardPanel(0);
        myCard1 = board.get(0);
        cardPanel1.setMyCard(myCard1);
        twoCardPanel.add(cardPanel1);

        cardPanel2 = new CardPanel(1);
        myCard2 = board.get(1);
        cardPanel2.setMyCard(myCard2);
        twoCardPanel.add(cardPanel2);
        return twoCardPanel;
    }
    private JPanel createSelectorPanel() {
        JPanel selectorMain = new JPanel(new BorderLayout());

        JPanel selector = new JPanel(new GridLayout(4,1));
        JComboBox<Properties.Color> colorPicker = new JComboBox<>(Properties.Color.values());
        JComboBox<Properties.Number> numberPicker = new JComboBox<>(Properties.Number.values());
        JComboBox<Properties.Opacity> opacityPicker = new JComboBox<>(Properties.Opacity.values());
        JComboBox<Properties.Shape> shapePicker = new JComboBox<>(Properties.Shape.values());
        selector.add(colorPicker);
        selector.add(numberPicker);
        selector.add(opacityPicker);
        selector.add(shapePicker);

        selectorMain.add(selector, BorderLayout.WEST);

        JPanel cardSelectorPanel = new JPanel(new GridLayout(2,1,20,20));
//        final Card[] card = {new Card((Properties.Color) colorPicker.getSelectedItem(), (Number) numberPicker.getSelectedItem(), (Shape) shapePicker.getSelectedItem(), (Opacity) opacityPicker.getSelectedItem())};
        CardPanel cardPanel = new CardPanel(0);
        myFinalCard = new Card((Properties.Color) colorPicker.getSelectedItem(),
                (Number) numberPicker.getSelectedItem(), (Shape) shapePicker.getSelectedItem(),
                (Opacity) opacityPicker.getSelectedItem());
        cardPanel.setMyCard(myFinalCard);
        AbstractAction makeCard = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFinalCard = new Card((Properties.Color) colorPicker.getSelectedItem(),
                        (Number) numberPicker.getSelectedItem(), (Shape) shapePicker.getSelectedItem(),
                        (Opacity) opacityPicker.getSelectedItem());
                cardPanel.setMyCard(myFinalCard);
                cardPanel.repaint();
            }
        };
        JButton selectButton = new JButton("SELECT");
        selectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSet();
            }
        });


        colorPicker.addActionListener(makeCard);
        numberPicker.addActionListener(makeCard);
        opacityPicker.addActionListener(makeCard);
        shapePicker.addActionListener(makeCard);

        cardSelectorPanel.add(cardPanel);
        cardSelectorPanel.add(selectButton);
        selectorMain.add(cardSelectorPanel);
        return selectorMain;
    }
    private void checkSet() {
        if(mySet.isSet(myCard1, myCard2, myFinalCard)) {
            mySet = new Set(2);
            myCard1 = mySet.getBoard().get(0);
            myCard2 = mySet.getBoard().get(1);
            cardPanel1.setMyCard(myCard1);
            cardPanel2.setMyCard(myCard2);
            cardPanel1.repaint();
            cardPanel2.repaint();
        }
    }
}
