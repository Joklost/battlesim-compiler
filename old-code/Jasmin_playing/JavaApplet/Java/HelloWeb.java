import java.applet.*;
import java.awt.*;

public class HelloWeb extends Applet
{
    private Font font;

    // during initialization, obtain Helvetica BOLD 48 point
    public void init() {
        font = new Font("Helvetica", Font.BOLD, 48);
    }

    // to repaint the applet window, draw the string Hello World
    // using the font we created earlier.
    public void paint(Graphics g)
    {
        g.setFont(font);
        g.drawString("Hello Web!", 25, 50);
    }
}
