package Model;

import Properties.Color;
import Properties.Opacity;
import Properties.Shape;

/**
 * Card class. Card has 4 properties: color, number, shape, and opacity.
 */
public class Card {
    private final Color color;
    private final Properties.Number number;
    private final Shape shape;
    private final Opacity opacity;
    public Card(Color color, Properties.Number number, Shape shape, Opacity opacity) {
        this.color = color;
        this.number = number;
        this.shape = shape;
        this.opacity = opacity;
    }
    public Color getColor() {
        return color;
    }
    public Properties.Number getNumber() {
        return number;
    }
    public Shape getShape() {
        return shape;
    }
    public Opacity getOpacity() {
        return opacity;
    }


    @Override
    public String toString() {
        return "[" + color.toString() + ", " + number.toString() + ", " +  shape.toString() + ", " + opacity.toString() + "]";
    }
}
