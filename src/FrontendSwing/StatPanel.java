package FrontendSwing;

import static Properties.SetProp.ADD_CARDS;

import java.awt.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StatPanel extends JPanel{
    private final PropertyChangeSupport myPcs;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;

    public StatPanel() {
        super();
        myPcs = new PropertyChangeSupport(this);
        setBackground(Color.RED);
       // setPreferredSize(new Dimension(WIDTH, HEIGHT));
        init();
    }
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }
    private void init() {
        JButton addCards = new JButton("ADD CARDS");
        addCards.addActionListener(e -> myPcs.firePropertyChange(ADD_CARDS.toString(), null, null));
        add(addCards);
    }
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


    }
}


