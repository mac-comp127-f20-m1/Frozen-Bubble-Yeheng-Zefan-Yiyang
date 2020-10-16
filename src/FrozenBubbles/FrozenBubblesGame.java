package FrozenBubbles;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.MouseInfo;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;

public class FrozenBubblesGame{
    public CanvasWindow canvas;

    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 800;
    public static final int SPEED = 200;
    public static final int CANNON_LENGTH = 40;

    public CannonBubble cannonBubble;
    public Cannon cannon;
    public BubblesManager manager;

    public FrozenBubblesGame(){
        canvas = new CanvasWindow("FrozenBubble", CANVAS_WIDTH, CANVAS_HEIGHT);

        cannon = new Cannon(285, 315, 315, 285, 700, 700, 740, 740);
        cannon.setFillColor(Color.BLACK);

        cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT);
        cannonBubble.setFillColor(getRandomColor());

        manager = new BubblesManager(canvas);
        manager.generateBubbles();

    }

    public static void main(String[] args) {
        FrozenBubblesGame frozenBubblesGame = new FrozenBubblesGame();
        frozenBubblesGame.run();
    }

    public void run(){
        processGame();
        addObjects();
    }

    public void addObjects(){
        canvas.add(cannonBubble);
        canvas.add(cannon);
    }

    public void processGame(){
        //mouseMove();
        canvas.animate(()->{
            cannonBubble.updatePosition(0.1, manager.getGraphicsGroup());

            if (cannonBubble.xVelocity == 0){
                manager.addCannonBubble(cannonBubble);
                cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT);
                cannonBubble.setFillColor(getRandomColor());
                canvas.add(cannonBubble);
            }
        });
    }

    public Color getRandomColor(){
        Random rand = new Random();
        int i = rand.nextInt(3);
        if (i == 0){
            return Color.RED;
        }else if (i == 1){
            return Color.YELLOW;
        }else{
            return Color.BLUE;
        }
    }


    // public void mouseMove(){

    //     if (MouseInfo.getPointerInfo().getLocation().getY() < 740){

    //         double angle = Math.atan(Math.abs(740 - MouseInfo.getPointerInfo().getLocation().getY()) / Math.abs(300 - MouseInfo.getPointerInfo().getLocation().getX()));

    //         if (MouseInfo.getPointerInfo().getLocation().getX() < 300){
                
    //             // canvas.onMouseMove(event -> cannonBubble.setPosition(event.getPosition().getX() * CANNON_LENGTH / Math.sqrt(Math.pow(750 - event.getPosition().getY(), 2) + Math.pow(event.getPosition().getX() - 300, 2)),
    //             // event.getPosition().getY() * CANNON_LENGTH / Math.sqrt(Math.pow(event.getPosition().getY(), 2) + Math.pow(event.getPosition().getX(), 2))));

    //             double x3 = 300 - 15 * Math.sin(angle);
    //             double y3 = 740 + 15 * Math.cos(angle);
    //             double x2 = 300 + 15 * Math.sin(angle);
    //             double y2 = 740 - 15 * Math.cos(angle);
    //             double x1 = x2 + 40 * Math.cos(angle);
    //             double y1 = y2 - 40 * Math.sin(angle);
    //             double x0 = x3 + 40 * Math.cos(angle);
    //             double y0 = y3 - 40 * Math.sin(angle);

    //             canvas.onMouseMove(event -> cannon.setVertices(List.of(new Point(x0, y0), new Point(x1, y1), new Point(x2, y2), new Point(x3, y3)), true));
    //         }else{
    //             double x3 = 300 - 15 * Math.sin(angle);
    //             double y3 = 740 - 15 * Math.cos(angle);
    //             double x2 = 300 + 15 * Math.sin(angle);
    //             double y2 = 740 + 15 * Math.cos(angle);
    //             double x1 = x2 + 40 * Math.cos(angle);
    //             double y1 = y2 + 40 * Math.sin(angle);
    //             double x0 = x3 + 40 * Math.cos(angle);
    //             double y0 = y3 + 40 * Math.sin(angle);

    //             canvas.onMouseMove(event -> cannon.setVertices(List.of(new Point(x0, y0), new Point(x1, y1), new Point(x2, y2), new Point(x3, y3)), true));

    //         }

    //     }


   // }

    // public void shootCannonBall(){
    //     canvas.onClick();
    // }
}