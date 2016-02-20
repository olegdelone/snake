package oov.snake.draw;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Olezha
 * Date: 11.07.14
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class Food implements Drawable {

    private BoxPoint boxPoint;

    private Color tmpColor;

    private Color blink = Color.CYAN;

    public Point getPoint() {
        return boxPoint;
    }

    public Food(BoxPoint boxPoint, int cellW, int cellH) {
        this.boxPoint = boxPoint;
        this.cellW = cellW;
        this.cellH = cellH;
    }


    private int cellW;
    private int cellH;

    @Override
    public void draw(Graphics g) {
        int x = boxPoint.x*cellW;
        int y = boxPoint.y*cellH;
        g.setColor(boxPoint.getBodyColor());
        g.drawRect(x, y, cellW - 1, cellH - 1);
        g.setColor(boxPoint.getInnerColor());
        g.fillRect(x+1, y+1, cellW-2, cellH-2);
    }

    public void blink() {
        if(tmpColor == null){
            tmpColor = boxPoint.getInnerColor();
            boxPoint.setInnerColor(blink);
        } else {
            boxPoint.setInnerColor(tmpColor);
            tmpColor = null;
        }
    }
}
