/**
 * This class initializes and processes the game. 
 * 
 * Specifically, after the mouse is clicked, the cannon bubble will be shot based on the position of 
 * the mouse. The class will constantly update the position of the cannon bubble based on the algorithm
 * in the CannonBubble class. When the cannon bubble hits the bubbles, the class will deal with the 
 * interaction of the bubble and the cannon bubble based on the algorithm in the BubbleManager. Then,
 * the class will create a new cannonbubble to repeat the process above.
 * 
 * Edited by Scott Zong, Zefan Qian, Yiyang Shi
 * 
 * We thank Professor Paul Cantrell for helping us with the approriate use of OnClick()
 */
package frozenBubbles;

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

    /**
     * Initialize the canvas, cannon bubble, and the bubble manager
     */
    public FrozenBubblesGame() {

        canvas = new CanvasWindow("FrozenBubble", CANVAS_WIDTH, CANVAS_HEIGHT);
        
        cannon = new Cannon(285, 315, 315, 285, 700, 700, 740, 740);
        cannon.setFillColor(Color.BLACK);

        cannonBubble = new CannonBubble(285, 670, DIAMETER, DIAMETER, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);

        manager = new BubblesManager(canvas);
        manager.generateBubbles();
       

    }

    public static void main(String[] args) {
        FrozenBubblesGame frozenBubblesGame = new FrozenBubblesGame();
        frozenBubblesGame.run();
    }

    /**
     * Start the game
     */
    private void run() {
        addObjects(); 
        canvas.onClick(event -> {setBoolean(); processGame1(); addObjects(); });
    }

    /**
     * Add all the initialized objects on the canvas
     */
    private void addObjects() {
        canvas.add(cannonBubble);
        canvas.add(cannon);
    }

    /**
     * Process the game.
     * 
     * This method will constantly update the position of the cannon bubble and deal
     * with the interaction between the cannon bubble and the bubbles.
     */
    private void processGame1() {
        if (startBallMoving) {
            canvas.animate(() -> {
                if (!cannonBubble.testHit(manager.getGraphicsGroup(),canvas)) {
                    cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
                }
                
                else{   
                    manager.correctCannonBubble(cannonBubble);

                    Bubble newBubble = new Bubble(cannonBubble.getX(), cannonBubble.getY(), cannonBubble.getWidth(),
                    cannonBubble.getHeight(), cannonBubble.getColor());

                    canvas.remove(cannonBubble);
                    manager.addBubble(newBubble);
                    manager.addBubbleToList(newBubble);
                    manager.destroyBubbles(newBubble);

                    manager.fallBubble();
                    manager.updatePointList(canvas);

                    cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
                    System.out.println("new cannBubble color is" + cannonBubble.getColor());
                    canvas.add(cannonBubble);
                }

            });
        }
    }

    /**
     * Start moving the ball after the mouse is clicked.
     */
    private void setBoolean() {
        startBallMoving = true;
    }
}
