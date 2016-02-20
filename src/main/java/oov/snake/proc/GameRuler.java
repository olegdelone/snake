package oov.snake.proc;

import oov.snake.draw.BoxPoint;
import oov.snake.draw.Cells;
import oov.snake.draw.Food;
import oov.snake.draw.Snake;

import java.awt.*;
import java.util.Random;

/**
 * 07.09.13 18:15: Original version (OOBUKHOV)<br/>
 */
public class GameRuler implements Runnable {

    private Snake snake;
    private Food food;
    private volatile short tick;

    public short getTick() {
        return tick;
    }

    static Random random = new Random();


    private Direction direction = Direction.values()[random.nextInt(4)];

    private final int yCapacity;
    private final int xCapacity;
    private int cellW;
    private int cellH;

    public synchronized void setDirection(Direction direction) {
        if(direction == this.direction){
            return;
        }
        if(this.direction == Direction.DOWNWARD && direction == Direction.UPWARD
                || this.direction == Direction.UPWARD && direction == Direction.DOWNWARD
                || this.direction == Direction.LEFTWARD && direction == Direction.RIGHTWARD
                || this.direction == Direction.RIGHTWARD && direction == Direction.LEFTWARD
                ){
            return;
        }
        Point nextPoint = snake.nextPoint(direction);
        if(snake != null && snake.size()>1 && nextPoint.equals(snake.getAfterHead())){
            return;
        }
        System.out.println("direction is set = " + direction);
        this.direction = direction;
    }

    public enum Direction {
        UPWARD('w'),
        DOWNWARD('s'),
        LEFTWARD('a'),
        RIGHTWARD('d');
        private char code;

        private Direction(char code) {
            this.code = code;
        }

        public char getCode() {
            return code;
        }

        public static Direction getByCode(char c) {
            for (Direction direction : values()) {
                if (direction.getCode() == c) {
                    return direction;
                }
            }
            throw new IllegalArgumentException("wrong key typed: " + c);
        }
    }


    public GameRuler(int width, int height, int cellW, int cellH) {
        this.cellH = cellH;
        this.cellW = cellW;
        xCapacity = calculateCapacity(width, cellW);
        yCapacity = calculateCapacity(height, cellH);
        Cells cells = new Cells(cellW, cellH, xCapacity, yCapacity);
        snake = new Snake(cellW, cellH, xCapacity, yCapacity, obtainRandPoint());
        food = new Food(new BoxPoint(obtainRandPoint(), Color.BLUE, Color.ORANGE), cellW, cellH);
        Engine.getInstance().add(snake);
        Engine.getInstance().add(food);
        Engine.getInstance().add(cells);
    }


    public int calculateCapacity(int dimension, int cellD) {
        return (int) Math.floor(dimension / cellD);
    }

    private Point obtainRandPoint() {
        int busyCells = 0;
        if (snake != null) {
            busyCells = snake.size();
        }

        int freeCellsSize = yCapacity * xCapacity - busyCells;
        if(freeCellsSize == 0){
            return null;
        }
        Random rand = new Random();
        int randPos = rand.nextInt(freeCellsSize);

        for (int i = 0; i < xCapacity; i++) {
            for (int j = 0; j < yCapacity; j++) {
                Point point = new Point(i, j);
                if (snake == null ? randPos-- == 0 : !snake.contains(point) && randPos-- == 0) {
                    return point;
                }
            }
        }
        throw new IllegalStateException("obtainRandPoint: freeCellsSize: " + freeCellsSize + "; randPos: " + randPos
                + "; snackPoints: " + snake);
    }


    @Override
    public void run() {

        long timeStart = System.currentTimeMillis();

        while (true) {
            long timeCurrent = System.currentTimeMillis();
            long delta = timeCurrent - timeStart;

            if (delta >= Engine.GAME_TIME) {
                tick++;
                if(tick%3==0){
                    Point nextPoint = snake.nextPoint(direction);
                    if (food.getPoint().equals(nextPoint)) {
                        snake.doGrowUp(food.getPoint());
                        Engine.getInstance().remove(food);
                        Point point = obtainRandPoint();
                        if(point == null){
                            throw new IllegalStateException("You are winner");
//                            break;
                        }
                        food = new Food(new BoxPoint(point, Color.BLUE, Color.ORANGE), cellW, cellH);
                        Engine.getInstance().add(food);
//                        Point point = snake.contains() // todo red color
                    } else if(snake.contains(nextPoint)){
                        throw new IllegalStateException("Game over at " + nextPoint);
                    }

                    snake.move(direction);

                }
                food.blink();
                timeStart = timeCurrent;
            }
        }
    }

}
