import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Set {
    private ArrayList<Card> deck;
    public Set() {
        init();
    }
    private void init() {
        deck = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Number number : Number.values()) {
                for (Shape shape : Shape.values()) {
                    for (Opacity opacity : Opacity.values()) {
                        deck.add(new Card(color, number, shape, opacity));
                    }
                }
            }
        }
//        testSet();
        HashMap<Integer, Card> board = new HashMap<>();
        Random rand = new Random();
        for (int i = 0; i < 9; i++) {
            int index = rand.nextInt(deck.size());
            board.put(i, deck.remove(index));
        }

    }
    private boolean isSet(Card card1, Card card2, Card card3) {
        return checkProp(card1.color, card2.color, card3.color) &&
                checkProp(card1.number, card2.number, card3.number) &&
                checkProp(card1.shape, card2.shape, card3.shape) &&
                checkProp(card1.opacity, card2.opacity, card3.opacity);
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
    private void testSet() {
        ArrayList<Card> testDeck = new ArrayList<>(deck);
        int total = 0;
        int sets = 0;
        for (int i = 0; i < testDeck.size(); i++) {
            for (int j = i + 1; j < testDeck.size(); j++) {
                for (int k = j + 1; k < testDeck.size(); k++) {
//                    System.out.println("Testing Cards: " + testDeck.get(i) + ", " + testDeck.get(j) + ", " +
//                            testDeck.get(k) + "; Set? " + isSet(testDeck.get(i), testDeck.get(j), testDeck.get(k)));
                    total++;
                    if(isSet(testDeck.get(i), testDeck.get(j), testDeck.get(k))) {
                        sets++;
                        System.out.println("Testing Cards: " + testDeck.get(i) + ", " + testDeck.get(j) + ", " +
                                testDeck.get(k) + "; Set? true");
                    }
                }

            }
        }
        System.out.println("Total combinations: " + total);
        System.out.println("Total sets: " + sets);
        System.out.println("Ratio: " + (sets * 100.0)/total  + "%");


    }






    private static class Card {
        Color color;
        Number number;
        Shape shape;
        Opacity opacity;
        Card(Color color, Number number, Shape shape, Opacity opacity) {
            this.color = color;
            this.number = number;
            this.shape = shape;
            this.opacity = opacity;
        }

        @Override
        public String toString() {
            return "[" +color.toString() + ", " + number.toString() + ", " +  shape.toString() + ", " + opacity.toString() + "]";
        }
    }
    // enum properties
    enum Color {
        GREEN, RED, BLUE
    }
    enum Number {
        ONE, TWO, THREE
    }
    enum Shape {
        OVAL, WORM, RHOMBUS
    }
    enum Opacity {
        SOLID, SEMITRANSPARENT, TRANSPARENT
    }
}
