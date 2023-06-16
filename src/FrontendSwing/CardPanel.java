package FrontendSwing;

import Model.Card;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class CardPanel extends JPanel {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private Card myCard = null;

    public CardPanel() {
        super();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    public void setMyCard(Card card) {
        myCard = card;
    }
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

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
        switch (myCard.getNumber()){

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
        switch (myCard.getShape()){

            case OVAL -> {
                for (int i = 0; i < shapeNum; i++) {
                    myShape = new Ellipse2D.Float(xOffset,yOffset,30,60);
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
        switch (myCard.getOpacity()){

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

//        g2d.draw
    }
}
