package FrozenBubbles;


import java.awt.Color;


import edu.macalester.graphics.CanvasWindow;





public class FrozenBubblesGame {
    public CanvasWindow canvas;

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final int SPEED = 50;

    private static final int DIAMETER = 30;

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

        cannonBubble = new CannonBubble(285, 670, DIAMETER, DIAMETER, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        manager = new BubblesManager(canvas);
        manager.generateBubbles();
        //manager.createInitialMap();

    }

    public static void main(String[] args) {
        FrozenBubblesGame frozenBubblesGame = new FrozenBubblesGame();
        frozenBubblesGame.run();
    }

    private void run() {
        addObjects(); 
        canvas.onClick(event -> { setBoolean();  processGame1();  addObjects(); });
        // canvas.onClick(event -> { setBoolean(); processGame2(); addObjects(); });
    }

    private void addObjects() {
        canvas.add(cannonBubble);
        canvas.add(cannon);
    }

    private void processGame1() {
        if (startBallMoving) {
            canvas.animate(() -> {
                if (!cannonBubble.testHit(manager.getGraphicsGroup(),canvas)) {
                    cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
                } else {
                    manager.correctCannonBubble(cannonBubble);
                    

                    Bubble newBubble = new Bubble(cannonBubble.getX(), cannonBubble.getY(), cannonBubble.getWidth(),
                    cannonBubble.getHeight(), cannonBubble.getColor());
                    canvas.add(newBubble);
                    canvas.remove(cannonBubble);
                    manager.addBubble(newBubble);
                    manager.addBubbleToList(newBubble);

                    manager.destroyBubbles(newBubble);
                    
                    manager.fallBubble();
                    cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
                    // Color color = getRandomColor();
                    // We do not need to getRandomColor() there because when we create a cannonBubble
                    // it already give the cannonBubble a color.
                    // cannonBubble.setFillColor(color);
                    // System.out.println("randomnization is "+color);
                    System.out.println("new cannBubble color is"+cannonBubble.getColor());
                    canvas.add(cannonBubble);
                }

            });
        }
    }

    // private void processGame2() {
    //     if (startBallMoving) {
    //         canvas.animate(() -> {
    //             if (!cannonBubble.testHit(manager.getGraphicsGroup(),canvas)) {
    //                 cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
    //             }

    //         });

    //         if (cannonBubble.testHit(manager.getGraphicsGroup(),canvas)) {
    //             manager.correctCannonBubble(cannonBubble);

    //             //manager.updateMap();
    //             cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
    //             canvas.add(cannonBubble);
    //         }
    //     }
    // };
    private void setBoolean() {
        startBallMoving = true;
    }
}
