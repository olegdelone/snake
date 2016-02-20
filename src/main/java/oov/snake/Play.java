package oov.snake;

import oov.snake.proc.Engine;
import oov.snake.proc.GameRuler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * 07.09.13 18:13: Original version (OOBUKHOV)<br/>
 */
public class Play {


    public static final int cellW = Integer.valueOf(AppProperties.get("canvas.cell.width"));
    public static final int cellH = Integer.valueOf(AppProperties.get("canvas.cell.height"));
    public static final int w = Integer.valueOf(AppProperties.get("canvas.width"));
    public static final int h = Integer.valueOf(AppProperties.get("canvas.height"));

    public final static BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    public final static JComponent component = new OverriddenComponent();

    public static void main(String[] args) {
        Dimension dimension = new Dimension(w, h);

        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
        component.setPreferredSize(dimension);

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(component, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setBounds(150, 150, w, h);
        frame.setVisible(true);

        final GameRuler gameRuler = new GameRuler(w, h, cellW, cellH);


        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    char key = e.getKeyChar();
                    try {
                        GameRuler.Direction direction = GameRuler.Direction.getByCode(key);

                        gameRuler.setDirection(direction);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
                return true;
            }
        });
        reset();
        render();

        Thread thread = new Thread(Engine.getInstance());
        thread.start();

        try {
            gameRuler.run();
        } catch (IllegalStateException e) {
            Engine.getInstance().stop();
            try {
                if (thread.isAlive()) {
                    thread.join();
                }
            } catch (InterruptedException e1) {
            }
            Engine.getInstance().fullRender();
            System.out.println("e = " + e);
            Color bgColor = Color.WHITE;
            Graphics gr = image.getGraphics();
            gr.setColor(bgColor);
            gr.drawString("Game over", w >> 1, h >> 1);
            render();
        }

    }


    public static void render() {
        component.getGraphics().drawImage(image, 0, 0, w, h, 0, 0, w, h, null);
    }

    public static void reset() {
        Color bgColor = Color.BLACK;
        Graphics gr = image.getGraphics();
        gr.setColor(bgColor);
        gr.fillRect(0, 0, w, h);

    }
}
