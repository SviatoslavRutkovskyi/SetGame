package FrontendSwing;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;

public class SetGUI extends JFrame implements PropertyChangeListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    public SetGUI() {
        super("SET GAME");
//        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setVisible(true);
        SetPanel setPanel = new SetPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(setPanel);
        pack();
        setVisible(true);
    }
    private void init(){

    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
