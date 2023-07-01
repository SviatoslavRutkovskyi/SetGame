package Model;

import Properties.Color;
import Properties.Opacity;
import Properties.SetProp;
import Properties.Shape;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Set {
    /**
     * (Passed in) number of initial cards
     */
    private final int initCards;
    /**
     * ArrayList that contains all possible cards. (81 total)
     * Does not contain duplicates
     */
    private ArrayList<Card> deck;
    /**
     * ArrayList that contains all the cards in the current board.
     */
    private ArrayList<Card> board;
    /**
     * Random object to draw cards from the deck.
     */
    private final Random rand = new Random();
    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(this);
    /**
     * Sets the default number of cards on the board.
     * @param initCards initial number of cards on the board.
     */
    public Set(int initCards) {
        this.initCards = initCards;
        init();
    }

    /**
     * returns a copy of the current board in the form of ArrayList
     * @return a copy of the current board in the form of ArrayList
     */
    public ArrayList<Card> getBoard() {
        return new ArrayList<>(board);
    }


    /**
     * Receives 3 cars as parameters, checks if they form a set.
     * If they do, replaces the cards with new cards from the deck.
     * If the number of cards is bigger than the initial value, just removes them without replacing.
     * @param a card 1
     * @param b card 2
     * @param c card 3
     * @return true if the given cards made a set
     */
    public boolean callSet(int a, int b, int c) {       // does not work properly if the init number is not divisible by the number of cards added
        boolean result = isSet(board.get(a), board.get(b), board.get(c));
        if (result) {
            int[] cards = new int[]{a, b, c};
            if (board.size() > initCards || deck.size() == 0) {
                Arrays.sort(cards);
                for (int i = cards.length - 1; i >= 0; i--) {
                    board.remove(cards[i]);
                }
            } else{
                for (int card : cards) {
                    board.set(card, deck.remove(rand.nextInt(deck.size())));
                }
            }
            myPcs.firePropertyChange(SetProp.SET_FOUND.toString(), null, new ArrayList<>(board));
        }
        return result;
    }

    /**
     * Adds a given number of cards to the board
     * @param cards number of cars to be added
     * @return true if all the cards were added
     */
    public boolean addCards(int cards) {
        boolean result = true;
        for (int i = 0; i < cards; i++) {
            if (!deck.isEmpty()) {
                board.add(deck.remove(rand.nextInt(deck.size())));
            } else {
                System.err.println("DECK HAS ENDED");
                result = false;
            }
        }
        //myPcs.firePropertyChange(SetProp.UPDATE_BOARD.toString(), null, new ArrayList<>(board));
        return result;
    }

    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * initializes the deck and the board
     */
    private void init() {
        deck = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Properties.Number number : Properties.Number.values()) {
                for (Shape shape : Shape.values()) {
                    for (Opacity opacity : Opacity.values()) {
                        deck.add(new Card(color, number, shape, opacity));
                    }
                }
            }
        }
//        testSet();
        board = new ArrayList<>();
        addCards(initCards);

    }

    /**
     * gets 3 cards in, checks if they form a set.
     * If they do returns true.
     * Does not check for the same card.
     * @param card1 Card 1
     * @param card2 Card 2
     * @param card3 Card 3
     * @return true if the 3 cards form a set
     */
    private boolean isSet(Card card1, Card card2, Card card3) {
        return checkProp(card1.getColor(), card2.getColor(), card3.getColor()) &&
                checkProp(card1.getNumber(), card2.getNumber(), card3.getNumber()) &&
                checkProp(card1.getShape(), card2.getShape(), card3.getShape()) &&
                checkProp(card1.getOpacity(), card2.getOpacity(), card3.getOpacity());
    }

    /**
     * Checks if given 3 enums are equal or all different
     * @param a param 1
     * @param b param 2
     * @param c param 3
     * @return bool that is true if either all given enums are the same or different
     */
    private boolean checkProp(Enum a, Enum b, Enum c) {
        boolean result = true;
        if (a != b || b != c) { //checks for equality
            result = a != b && b != c && c != a; //if not, checks if all are different
        }
        return result;
    }

    /**
     * Tests the Card class
     */
    private void testCards() {
        ArrayList<Card> testDeck = new ArrayList<>(deck);
        int total = 0;
        int sets = 0;
        for (int i = 0; i < testDeck.size(); i++) {
            for (int j = i + 1; j < testDeck.size(); j++) {
                for (int k = j + 1; k < testDeck.size(); k++) {
//                    System.out.println("Testing Cards: " + testDeck.get(i) + ", " + testDeck.get(j) + ", " +
//                            testDeck.get(k) + "; Model.Set? " + isSet(testDeck.get(i), testDeck.get(j), testDeck.get(k)));
                    total++;
                    if(isSet(testDeck.get(i), testDeck.get(j), testDeck.get(k))) {
                        sets++;
                        System.out.println("Testing Cards: " + testDeck.get(i) + ", " + testDeck.get(j) + ", " +
                                testDeck.get(k) + "; Model.Set? true");
                    }
                }
            }
        }
        System.out.println("Total combinations: " + total);
        System.out.println("Total sets: " + sets);
        System.out.println("Ratio: " + (sets * 100.0)/total  + "%");
    }

}
