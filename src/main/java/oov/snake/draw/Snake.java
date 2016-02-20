package oov.snake.draw;

import oov.snake.proc.GameRuler;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Olezha
 * Date: 10.07.14
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public class Snake implements Drawable {

    private List<BoxPoint> bodyChunks;
    private BoxPoint head;

    private int cellW;
    private int cellH;

    private int xCapacity;
    private int yCapacity;

    public Snake(int cellW, int cellH, int xCapacity, int yCapacity, Point point) {
        this.cellW = cellW;
        this.cellH = cellH;
        this.xCapacity = xCapacity;
        this.yCapacity = yCapacity;
        bodyChunks = Collections.synchronizedList(new LinkedList<BoxPoint>());
        doGrowUp(point);
    }

    public BoxPoint getHead() {
        return head;
    }

    public BoxPoint getAfterHead() {
        return bodyChunks.get(1);
    }

    public int size() {
        return bodyChunks.size();
    }

    public boolean contains(Object o) {
        return bodyChunks.contains(o);
    }
    public Object get(Object o) {
        return bodyChunks.get(bodyChunks.indexOf(o));
    }


    public void doGrowUp(Point p) {
//        BoxPoint tail = bodyChunks.get(bodyChunks.size()-1);
//        int startPosX = (int)head.getX();
//        int startPosY = (int)head.getY();
        head = new BoxPoint(p, Color.BLUE, Color.GREEN);
        synchronized (bodyChunks) {
            bodyChunks.add(0, head);
        }
    }

    public Point nextPoint(GameRuler.Direction direction) {
        int xCurrent = head.x;
        int yCurrent = head.y;

        if (direction == GameRuler.Direction.LEFTWARD) {
            if (xCurrent <= 0) {
                xCurrent = xCapacity - 1;
            } else {
                xCurrent -= 1;
            }
        } else if (direction == GameRuler.Direction.RIGHTWARD) {
            if (xCurrent >= xCapacity - 1) {
                xCurrent = 0;
            } else {
                xCurrent += 1;
            }
        } else if (direction == GameRuler.Direction.DOWNWARD) {
            if (yCurrent >= yCapacity - 1) {
                yCurrent = 0;
            } else {
                yCurrent += 1;
            }
        } else if (direction == GameRuler.Direction.UPWARD) {
            if (yCurrent <= 0) {
                yCurrent = yCapacity - 1;
            } else {
                yCurrent -= 1;
            }
        }
        return new Point(xCurrent, yCurrent);
    }

    public void move(GameRuler.Direction direction) {

        Point p = nextPoint(direction);
        synchronized (bodyChunks) {
            BoxPoint tail = bodyChunks.remove(bodyChunks.size() - 1);
            tail.move(p.x, p.y);
            bodyChunks.add(0, tail);
            head = tail;
        }
    }

//    public int calculateAbsX(int x) {
//        return x * cellW;
//    }
//
//    public int calculateAbsY(int y) {
//        return y * cellH;
//    }

    @Override
    public void draw(Graphics g) {
        synchronized (bodyChunks) {
            for (BoxPoint bodyChunk : bodyChunks) {
                int x = bodyChunk.x * cellW;
                int y = bodyChunk.y * cellH;

                g.setColor(bodyChunk.getBodyColor());
                g.drawRect(x, y, cellW - 1, cellH - 1);
                g.setColor(bodyChunk.getInnerColor());
                g.fillRect(x + 1, y + 1, cellW - 2, cellH - 2);
//            bodyChunk.draw(g);
            }
        }
    }

    @Override
    public String toString() {
        return "Snake{" +
                "bodyChunks=" + bodyChunks +
                '}';
    }
}
