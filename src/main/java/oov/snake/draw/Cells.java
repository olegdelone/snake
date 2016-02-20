package oov.snake.draw;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Olezha
 * Date: 08.08.14
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class Cells implements Drawable {
    int cellW;
    int cellH;
    int xCapacity;
    int yCapacity;

    public Cells(int cellW, int cellH, int xCapacity, int yCapacity) {
        this.cellW = cellW;
        this.cellH = cellH;
        this.xCapacity = xCapacity;
        this.yCapacity = yCapacity;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        int yMax = cellH*yCapacity;
        int xMax = cellW*xCapacity;
        for (int i = 0; i <= xCapacity; i++) {
            g.drawLine(i*cellW, 0, i*cellW, yMax);
        }
        for (int i = 0; i <= xCapacity; i++) {
            g.drawLine(0, i*cellH, xMax, i*cellH);
        }
    }
}
