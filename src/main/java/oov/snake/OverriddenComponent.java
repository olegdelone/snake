package oov.snake;

import javax.swing.*;
import java.awt.*;

/**
 * 07.09.13 18:34: Original version (OOBUKHOV)<br/>
 */
public class OverriddenComponent extends JComponent {

    public OverriddenComponent() {
        super();
    }

    private static volatile JComponent instance;

    public static JComponent getInstance() {
        if (instance == null) {
            synchronized (OverriddenComponent.class) {
                if (instance == null) {
                    instance = new OverriddenComponent();
                }
            }
        }
        return instance;
    }


    @Override
    public void paintComponent(Graphics g) {
    }
}
