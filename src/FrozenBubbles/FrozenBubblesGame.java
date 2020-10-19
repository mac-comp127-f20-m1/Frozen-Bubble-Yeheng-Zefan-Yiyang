package FrozenBubbles;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;


public class FrozenBubblesGame{
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

    public FrozenBubblesGame(){
        canvas = new CanvasWindow("FrozenBubble", CANVAS_WIDTH, CANVAS_HEIGHT);

        cannon = new Cannon(285, 315, 315, 285, 700, 700, 740, 740);
        cannon.setFillColor(Color.BLACK);

        cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT,canvas);

        manager = new BubblesManager(canvas);
        manager.generateBubbles();
        manager.createInitialMap();

    }

    public static void main(String[] args) {
        FrozenBubblesGame frozenBubblesGame = new FrozenBubblesGame();
        frozenBubblesGame.run();
    }

    private void run(){
        processGame();
        addObjects();
    }

    private void addObjects(){
        canvas.add(cannonBubble);
        canvas.add(cannon);
    }

    private void processGame(){
        //mouseMove();
        canvas.animate(()->{
            if (!cannonBubble.testHit(manager.getGraphicsGroup())){
                cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(),canvas);
            }
            cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(),canvas);

            if (cannonBubble.getXVelocity() == 0){
                manager.correctCannonBubble(cannonBubble);
                //eliminateBubbles(manager, canvas, cannonBubble);
                manager.updateMap();
                cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT,canvas);
                cannonBubble.setFillColor(getRandomColor());
                canvas.add(cannonBubble);
            }
            
        });
    }

    private Color getRandomColor(){
        Random rand = new Random();
        int i = rand.nextInt(4);
        if (i == 0){
            return Color.RED;
        }else if (i == 1){
            return Color.YELLOW;
        }else if (i == 2){
            return Color.GREEN;
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

    // public void eliminateBubbles(BubblesManager bubbles, CanvasWindow canvas, CannonBubble cannonbubble){

    //     List<List<Integer>> list = new ArrayList<>();

    //     double yPosition = cannonbubble.getYPosition();
    //     int yNum = (int)(yPosition / (DIAMETER - 2.5));

    //     double xPosition = cannonbubble.getXPosition();

    //     int xNum = 0;

    //     if (yNum % 2 == 0){
    //         xNum = (int)(xPosition / DIAMETER);
    //     }else{
    //         xNum = (int)((xPosition - 0.135 / 0.27 * DIAMETER) / DIAMETER);
    //     }

    //     Color color = bubbles.getColor(xNum, yNum);

    //     canvas.remove(cannonbubble);

        
    // }
}