package FrontendSwing;

import static Properties.SetProp.*;

import Model.Card;
import Properties.SetProp;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

public class CardPanel extends JPanel implements PropertyChangeListener{

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private final Shape borderShape = new RoundRectangle2D.Double(0,0,200,100,20,20);
    /**
     * Card that is represented by this class.
     */
    private Card myCard = null;
    /**
     * Card id.
     */
    private final int id;
    /**
     * Boolean that shows if card is selected or not.
     */
    private boolean selected;

    private final PropertyChangeSupport myPcs;

    public CardPanel(int idNum) {
        super();
        id = idNum;
        myPcs = new PropertyChangeSupport(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(new MouseClickListener());
    }

    /**
     * Sets a card to this class.
     * @param card card set to this class.
     */
    public void setMyCard(Card card) {
        myCard = card;
    }

    /**\
     * Paints the panel according to the card that is passed in.
     * @param theGraphics the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        if (myCard == null) {
            System.out.println("Oi bruv, card at id " + id + " is null");
        } else {
            super.paintComponent(theGraphics);
            final Graphics2D g2d = (Graphics2D) theGraphics;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (selected) {
//                setBackground(Color.CYAN);
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(10));
                g2d.draw(borderShape);
            }

            switch (myCard.getColor()) {
                case GREEN -> g2d.setPaint(Color.GREEN);
                case RED -> g2d.setPaint(Color.RED);
                case BLUE -> g2d.setPaint(Color.BLUE);
            }
//        RectangularShape myShape = null;
            Shape myShape;
            ArrayList<Shape> myShapes = new ArrayList<>();
            int xOffset = 35;
            int yOffset = 20;
            int shapeNum = 0;
            switch (myCard.getNumber()) {
                case ONE -> {
                    xOffset = 85;
                    shapeNum = 1;
                }
                case TWO -> {
                    xOffset = 60;
                    shapeNum = 2;
                }
                case THREE -> shapeNum = 3;
            }
            switch (myCard.getShape()) {
                case OVAL -> {
                    for (int i = 0; i < shapeNum; i++) {
                        myShape = new Ellipse2D.Float(xOffset, yOffset, 30, 60);
                        myShapes.add(myShape);
                        xOffset += 50;
                    }
                }
                case WORM -> {
                    for (int i = 0; i < shapeNum; i++) {
                        myShape = new Arc2D.Double(xOffset, yOffset, 30, 30, 50, 220,
                                Arc2D.OPEN);
                        myShapes.add(myShape);
                        myShape = new Arc2D.Double(xOffset - 7, yOffset + 29, 30, 30, 310, 130,
                                Arc2D.OPEN);
                        myShapes.add(myShape);
                        myShape = new Arc2D.Double(xOffset + 20, yOffset, 30, 30, 130, 140,
                                Arc2D.OPEN);
                        myShapes.add(myShape);
                        myShape = new Arc2D.Double(xOffset + 13, yOffset + 29, 30, 30, 230, 210,
                                Arc2D.OPEN);
                        myShapes.add(myShape);
                        xOffset += 50;
                    }

                }
                case RHOMBUS -> {
//                myShape = new Rectangle(10, 10, 30, 50);
                    for (int i = 0; i < shapeNum; i++) {
                        myShape = new Polygon(new int[]{xOffset, xOffset + 15, xOffset + 30, xOffset + 15},
                                new int[]{yOffset + 30, yOffset, yOffset + 30, yOffset + 60}, 4);
                        myShapes.add(myShape);
                        xOffset += 50;
                    }
                }
            }
            g2d.setStroke(new BasicStroke(4));
//        g2d.draw(myShape);
            switch (myCard.getOpacity()) {

                case SOLID -> {
                    for (Shape shape : myShapes) {
                        g2d.fill(shape);
                    }
                }
                case SEMI -> {

                    for (Shape shape : myShapes) {
                        g2d.draw(shape);
                    }
                    GradientPaint gp4 = new GradientPaint(0.5f, 25,
                            Color.WHITE, 2, 25, g2d.getColor(), true);
                    g2d.setPaint(gp4);
                    for (Shape shape : myShapes) {
                        g2d.fill(shape);

                    }
                }
                case CLEAR -> {
                    for (Shape shape : myShapes) {
                        g2d.draw(shape);
                    }
                }
            }
        }
//        g2d.draw
    }

    /**
     * Resets the card selection if a set is found.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SetProp prop = SetProp.valueOf(evt.getPropertyName());
        if (prop == SET_FOUND) {
            selected = false;
//            System.out.println("Repainting card at id: " + id);
            repaint();
        }
    }

    /**
     * Flips the selected boolean when clicked.
     */
    class MouseClickListener extends MouseInputAdapter {
        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            selected = !selected;
            repaint();
            myPcs.firePropertyChange(CARD_SELECT.toString(), selected, id);
        }
    }
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
}

