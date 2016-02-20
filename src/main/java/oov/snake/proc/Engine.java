package oov.snake.proc;

import oov.snake.Play;
import oov.snake.draw.Drawable;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Olezha
 * Date: 11.07.14
 * Time: 0:51
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends Vector<Drawable> implements Runnable {


    volatile boolean run = true;

    public void stop(){
        run = false;
    }

    private Engine() {
    }

    private static volatile Engine instance;

    public static Engine getInstance() {
        if (instance == null) {
            synchronized (Engine.class) {
                if (instance == null) {
                    instance = new Engine();
                }
            }
        }
        return instance;
    }

    public final static int GAME_TIME = 100; // todo level parametrized
    private final static int RENDER_TIME = 1000 / 60;
//    private Collection<Drawable> drawables = new Vector<Drawable>();

//    public void addDrawable(Drawable drawable) {
//        drawables.add(drawable);
//    }

    @Override
    public void run() {

        long timeStart = System.currentTimeMillis();

        while (run) {

            long timeCurrent = System.currentTimeMillis();
            long delta = timeCurrent - timeStart;
            if (delta >= RENDER_TIME) {
                fullRender();

                long operationTime = System.currentTimeMillis() - timeCurrent;
                long freeTime = RENDER_TIME - operationTime;
                if (freeTime > 10) {
                    try {
                        Thread.currentThread().sleep(freeTime);
                    } catch (InterruptedException e) {
                    }
                }
                timeStart = timeCurrent;
            }
        }
    }
    public void fullRender(){
        Play.reset(); // black scr

        for (Drawable drawable : this) {
            drawable.draw(Play.image.getGraphics());
        }

        Play.render();
    }
}
