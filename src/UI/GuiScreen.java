package UI;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Marko
 * Date: 12/13/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class GuiScreen {
    protected JFrame frame;
    protected JPanel panel;
    protected boolean fullScreen = false;

    public void showScreen()
    {
        if(fullScreen)
            MainGui.get().showFullScreen(panel, frame);
        else
            MainGui.get().showScreen(panel, frame);

        frame.setVisible(true);
    }

    public GuiScreen setFrame(JFrame frame)
    {
        this.frame = frame;
        return this;
    }
}
