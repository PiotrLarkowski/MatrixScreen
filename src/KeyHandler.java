import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean escPressed = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_ESCAPE){
            escPressed = true;
        }
        if(e.getKeyChar()==KeyEvent.VK_ENTER && escPressed){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
