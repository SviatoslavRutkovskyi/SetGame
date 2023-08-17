package FrontendSwing;

import static Properties.SetProp.*;

import Properties.SetProp;
import java.awt.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatPanel extends JPanel implements PropertyChangeListener{
    private final PropertyChangeSupport myPcs;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 400;
    private JLabel setCountLabel;
    private JLabel cardCountLabel;
    private int setNum = 0;
    private int cardNum = 72;


    public StatPanel() {
        super();
        myPcs = new PropertyChangeSupport(this);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
       // setPreferredSize(new Dimension(WIDTH, HEIGHT));
        init();
    }
    private void init() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0,1,10,10));



        setCountLabel = new JLabel("Sets: " + setNum);
        mainPanel.add(setCountLabel);

        cardCountLabel = new JLabel("Cards in deck: " + cardNum);
        mainPanel.add(cardCountLabel);


        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(213, 162, 232), 10));
        add(mainPanel, BorderLayout.CENTER);
    }
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
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
        if (prop == SET_FOUND) {
            setNum += 1;
            setCountLabel.setText("Sets: " + setNum);
            cardNum = (int) evt.getOldValue();
            cardCountLabel.setText("Cards in deck: " + cardNum);
        } else if (prop == ADD_CARDS) {
            if (cardNum > 0) {
                cardNum -= 3;
                cardCountLabel.setText("Cards in deck: " + cardNum);
            }
        }
    }
}


