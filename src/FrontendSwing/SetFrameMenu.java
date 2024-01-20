package FrontendSwing;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class SetFrameMenu extends JMenuBar {
    private final SetGUI mySetGUI;
    public SetFrameMenu(final SetGUI theSetGUI){
        mySetGUI = theSetGUI;
        createMenu();
    }
    private void createMenu() {

        add(new JMenuItem(new AbstractAction("Practice Mode") {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindThirdSet practice = new FindThirdSet();
            }
        }));
        add(new JMenuItem(new AbstractAction("About this Game") {
            @Override
            public void actionPerformed(final ActionEvent e0) {
                mySetGUI.makeHowToPlayVisible();
            }
        }));

    }
}
