/**
 * This class initializes and processes the game.
 * 
 * Specifically, after the mouse is clicked, the cannon bubble will be shot based on the position of
 * the mouse. The class will constantly update the position of the cannon bubble based on the
 * algorithm in the CannonBubble class. When the cannon bubble hits the bubbles, the class will deal
 * with the interaction of the bubble and the cannon bubble based on the algorithm in the
 * BubbleManager. Then, the class will create a new cannonbubble to repeat the process above.
 * 
 * Edited by Scott Yeheng Zong, Zefan Qian.
 * 
 * We thank Professor Paul Cantrell for helping us with the approriate use of OnClick()
 */
package FrozenBubble;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.ui.Button;

public class FrozenBubbleGame {
    public CanvasWindow canvas;

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final int SPEED = 50;
    private static final int DIAMETER = 30;

    private CannonBubble cannonBubble;
    private Cannon cannon;
    private BubbleManager manager;
    private GraphicsGroup buttonLayer;
    private FrozenBubbleGame frozenBubblesGame;

    private boolean startBallMoving;

    /**
     * Initialize the canvas, cannon bubble, and the bubble manager
     */
    public FrozenBubbleGame() {

        canvas = new CanvasWindow("FrozenBubble", CANVAS_WIDTH, CANVAS_HEIGHT);

        cannon = new Cannon(285, 315, 315, 285, 700, 700, 740, 740);
        cannon.setFillColor(Color.BLACK);

        cannonBubble = new CannonBubble(285, 670, DIAMETER, DIAMETER, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
        System.out.println("The color of the new cannonBubble is " + cannonBubble.getColor());
        // The println method makes sure palyers can still know the cannonBubble's color
        // even if the computer screen does not show the whole canvas window.

        manager = new BubbleManager(canvas);
        manager.generateBubbles();

        buttonLayer = new GraphicsGroup();
        createExitButton();
        createResetGameButton();


    }

    public static void main(String[] args) {
        FrozenBubbleGame frozenBubblesGame = new FrozenBubbleGame();
        frozenBubblesGame.run();
    }

    /**
     * Start the game
     */
    private void run() {
        addObjects();
        canvas.onClick(event -> { setBoolean(); processGame(); addObjects(); });
    }

    /**
     * Add all the initialized objects on the canvas
     */
    private void addObjects() {
        canvas.add(cannonBubble);
        canvas.add(cannon);
        canvas.add(buttonLayer);
    }

    /**
     * Process the game.
     * 
     * This method will constantly update the position of the cannon bubble and deal with the
     * interaction between the cannon bubble and the bubbles.
     */
    private void processGame() {
        if (startBallMoving) {
            canvas.animate(() -> {
                if (!cannonBubble.testHit(manager.getGraphicsGroup(), canvas)) {
                    cannonBubble.updatePosition(0.1, manager.getGraphicsGroup(), canvas);
                }

                else {
                    manager.correctCannonBubble(cannonBubble);
                    canvas.remove(cannonBubble);

                    Bubble newBubble = new Bubble(cannonBubble.getX(), cannonBubble.getY(), cannonBubble.getWidth(),
                        cannonBubble.getHeight(), cannonBubble.getColor());
                    manager.addBubble(newBubble);
                    manager.addBubbleToList(newBubble);

                    manager.destroyBubbles(newBubble);
                    manager.fallBubble();
                    manager.updatePointList(canvas);

                    cannonBubble = new CannonBubble(285, 670, 30, 30, SPEED, CANVAS_WIDTH, CANVAS_HEIGHT, canvas);
                    System.out.println("The color of new cannonBubble is " + cannonBubble.getColor());
                    // The println method makes sure palyers can still know the cannonBubble's color
                    // even if the computer screen does not show the whole canvas window.
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

    /**
     * create a button with title "Exit", and the canvas window will be closed if it the button clicked.
     */
    private void createExitButton() {
        Button exitButton = new Button("Exit");
        buttonLayer.add(exitButton);
        exitButton.setPosition(500, 300);
        exitButton.onClick(() -> canvas.closeWindow());
    }

    /**
     * create a button with title "Reset", and the FrozenBubbles game will be reseted if the button is
     * clicked.
     */
    private void createResetGameButton() {
        Button resetGameButton = new Button("Reset");
        buttonLayer.add(resetGameButton);
        resetGameButton.setPosition(500, 350);
        resetGameButton.onClick(() -> {
            frozenBubblesGame = new FrozenBubbleGame();
            frozenBubblesGame.run();
        });
    }
}
