package FrozenBubbles;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.MouseButtonEvent;


public class FrozenBubblesGame {
    public CanvasWindow canvas;

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final int SPEED = 50;
    private static final int CANNON_LENGTH = 40;

    private static final int DIAMETER = 30;
    private static final int ROW = 8;
    private static final int COLUMN = 20;

    private CannonBubble cannonBubble;
    private Cannon cannon;
    private BubblesManager manager;
    private boolean startBallMoving;

    public FrozenBubblesGame() {

        canvas = new CanvasWindow("FrozenBubble", CANVAS_WIDTH, CANVAS_HEIGHT);
        // mouseLayer = new GraphicsGroup();
        // canvas.add(mouseLayer);
        cannon = new Cannon(285, 315, 315, 285, 700, 700, 740, 740);
        cannon.setFillColor(Color.BLACK);

        cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        manager = new BubblesManager(canvas);
        manager.generateBubbles();
        manager.createInitialMap();

    }

    public static void main(String[] args) {
        FrozenBubblesGame frozenBubblesGame = new FrozenBubblesGame();
        frozenBubblesGame.run();
    }

    private void run() {
        canvas.onClick(event -> { setBoolean(); processGame1(); addObjects(); });
        // canvas.onClick(event -> { setBoolean(); processGame2(); addObjects(); });
    }

    private void addObjects() {
        canvas.add(cannonBubble);
        cannonBubble.setCenter(300, 685);
        canvas.add(cannon);
        cannon.setCenter(300, 720);
    }

    private void processGame1() {
        if (startBallMoving) {
            canvas.animate(() -> {
                if (!cannonBubble.testHit(manager.getGraphicsGroup())) {
                    cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
                } else {
                    manager.correctCannonBubble(cannonBubble);
                    manager.destroyBubbles(cannonBubble);
                    manager.updateMap();
                    cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
                    cannonBubble.setFillColor(getRandomColor());

                    canvas.add(cannonBubble);
                }

            });
        }
    }

    private void processGame2() {
        if (startBallMoving) {
            canvas.animate(() -> {
                if (!cannonBubble.testHit(manager.getGraphicsGroup())) {
                    cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
                }

            });

            if (cannonBubble.testHit(manager.getGraphicsGroup())) {
                manager.correctCannonBubble(cannonBubble);

                manager.updateMap();
                cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
                cannonBubble.setFillColor(getRandomColor());

                canvas.add(cannonBubble);
            }
        }
    };


    private Color getRandomColor() {
        Random rand = new Random();
        int i = rand.nextInt(4);
        if (i == 0) {
            return Color.RED;
        } else if (i == 1) {
            return Color.YELLOW;
        } else if (i == 2) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    private void setBoolean() {
        startBallMoving = true;
    }
}
