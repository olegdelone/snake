package oov.snake.draw;

import oov.snake.AppProperties;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Olezha
 * Date: 10.07.14
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public class BoxPoint extends Point implements Drawable {
    private Color bodyColor;
    private Color innerColor;

    public BoxPoint(int x, int y, Color bodyColor, Color innerColor) {
        super(x, y);
        this.bodyColor = bodyColor;
        this.innerColor = innerColor;
    }

    public BoxPoint(Point p, Color bodyColor, Color innerColor) {
        super(p);
        this.bodyColor = bodyColor;
        this.innerColor = innerColor;
    }

    public Color getBodyColor() {
        return bodyColor;
    }

    public Color getInnerColor() {
        return innerColor;
    }

    public void setBodyColor(Color bodyColor) {
        this.bodyColor = bodyColor;
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
    }

    @Override
    @Deprecated
    public void draw(Graphics g) {
        int cellW = Integer.valueOf(AppProperties.get("canvas.cell.width"));
        int cellH = Integer.valueOf(AppProperties.get("canvas.cell.height"));
        g.setColor(getBodyColor());
        int x = this.x*cellW;
        int y = this.y*cellH;
        g.drawRect(x, y, cellW - 1, cellH - 1);
        g.setColor(getInnerColor());
        g.fillRect(x+1, y+1, cellW-2, cellH-2);
    }
}
