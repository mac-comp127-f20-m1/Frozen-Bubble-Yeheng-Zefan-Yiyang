/**
 * This class create the cannon bubble with the input x position and y position of the upper left,
 * the width, the height, and the color.
 * 
 * The CannonBubble class extends the Ellipse, so it can be treated as a GraphicsObject,
 * specifically Ellipse, on the canvas.
 * 
 * Specifically, this class includes algorithms updating the position of the cannon bubble and
 * testing whether the cannon bubble hits the walls or the bubbles. The update of the position will
 * be influenced after the cannon bubble hits the walls or the bubbles.
 * 
 * Edted by Zefan Qian, Scott Yeheng Zong.
 */
package FrozenBubble;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import java.awt.Color;

public class CannonBubble extends Ellipse {
    private Color color;

    private double xVelocity;
    private double yVelocity;

    private double xPosition;
    private double yPosition;

    private double xMaxPosition;
    private double yMaxPosition;

    private double speed;

    private double width;
    private double height;

    public CannonBubble(double x, double y, double width, double height, double speed,
        double xMaxPosition, double yMaxPosition, CanvasWindow canvas) {

        super(x, y, width, height);

        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.xMaxPosition = xMaxPosition;
        this.yMaxPosition = yMaxPosition;
        this.speed = speed;


        Color colorRandom = getRandomColor();
        setFillColor(colorRandom);
        this.color = colorRandom;

        canvas.onClick(event -> setVelocity(setDegree(event.getPosition())));

        setFilled(true);
    }

    /**
     * Update the position of the cannon bubble with the period dt.
     * 
     * Change the speed of the cannon bubble when it hits the walls.
     * 
     * @param dt      the time period to update the position of the cannon bubble
     * @param bubbles the cannon bubble whose position will be changed
     * @param canvas  the canvas where the cannon bubble is
     */
    public void updatePosition(double dt, GraphicsGroup bubbles, CanvasWindow canvas) {
        double updateXPosition;
        double updateYPosition;
        updateXPosition = xPosition + dt * xVelocity;
        updateYPosition = yPosition + dt * yVelocity;

        if (!testHit(bubbles, canvas)) {
            if (updateXPosition < 0 || updateXPosition > xMaxPosition - getWidth()) {
                xVelocity = -xVelocity;
            } else if (updateYPosition <= 0) {
                xVelocity = 0;
                yVelocity = 0;
                updateYPosition = 1;
            } else if ((updateXPosition == 0 && updateYPosition == 0)
                || (updateXPosition == xMaxPosition - getWidth() && updateYPosition == yMaxPosition - getHeight())) {
                xVelocity = -xVelocity;
                yVelocity = -yVelocity;
            } else {
                xPosition = updateXPosition;
                yPosition = updateYPosition;
            }
        } else {
            updateXPosition -= dt * xVelocity;
            updateYPosition -= dt * yVelocity;
        }
        setPosition(updateXPosition, updateYPosition);
    }

    /**
     * get a random color for the cannon bubble
     */
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

    /**
     * Test whether the cannon bubble hits the bubbles
     * 
     * @param bubbles the cannon bubble which is tested
     * @param canvas  the canvas where the cannon bubble at
     * @return whether the cannon bubble hits bubbles
     */
    public boolean testHit(GraphicsGroup bubbles, CanvasWindow canvas) {
        for (int i = 0; i < 360; i++) {
            if (bubbles.getElementAt((xPosition + 15) + 15.1 * Math.cos(i * Math.PI / 180),
                (yPosition + 15) + 15.1 * Math.sin(i * Math.PI / 180) + 0.01) != null || yPosition <= 15) {
                xVelocity = 0;
                yVelocity = 0;
                canvas.remove(this);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the color of the cannon bubble.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the x velocity of the cannon bubble.
     */
    public double getXVelocity() {
        return xVelocity;
    }

    /**
     * @return the x position of the cannon bubble.
     */
    public double getXPosition() {
        return xPosition;
    }

    /**
     * @return the y position of the cannon bubble
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * @return the x position of the center of the cannon bubble
     */
    public double getCenterX() {
        return xPosition + width / 2;
    }

    /**
     * @return the y position of the center of the cannon bubble
     */
    public double getCenterY() {
        return yPosition + height / 2;
    }

    /**
     * Find the neighbour bubbles of the cannon bubble.
     * 
     * @param canvas the canvas where the cannon bubble at
     * @return the set of the neighbour bubbles of the cannon bubble
     */
    public Set<Bubble> getNeighbours(CanvasWindow canvas) {

        Set<Bubble> neighbourBubbles = new HashSet<>();
        double centerX = getCenterX();
        double centerY = getCenterY();

        // add bubble1(the one at the top-left) to the set
        if (canvas.getElementAt(centerX - width / 2, centerY - height) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX - width / 2, centerY - height));
        }

        // add bubble2(the one at the top-right) to the set
        if (canvas.getElementAt(centerX + width / 2, centerY - height) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX + width / 2, centerY - height));
        }

        // add bubble3(the one at the most left) to the set
        if (canvas.getElementAt(centerX - width, centerY) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX - width, centerY));
        }

        // add bubble4(the one at the most right) to the set
        if (canvas.getElementAt(centerX + width, centerY) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX + width, centerY));
        }

        // add bubble5(the one at the bottom-left) to the set
        if (canvas.getElementAt(centerX - width / 2, centerY + height) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX - width / 2, centerY + height));
        }

        // add bubble6(the one at the bottom-right) to the set
        if (canvas.getElementAt(centerX + width / 2, centerY + height) != null) {
            neighbourBubbles.add((Bubble) canvas.getElementAt(centerX + width / 2, centerY + height));
        }

        return neighbourBubbles;
    }

    /**
     * Set the initial degree of the cannon bubble.
     * 
     * @param point the point where the mouse it at
     * @return the degree created by the point
     */
    private double setDegree(Point point) {
        double hypotenuse = Math.hypot(point.getX() - 300, point.getY() - 685);
        double cathetus = point.getX() - 300;
        return Math.acos(cathetus / hypotenuse);
    }

    /**
     * Set the initial velocity of the cannon bubble.
     * 
     * @param degree the initial degree of the cannon bubble
     */
    private void setVelocity(double degree) {
        yVelocity = -speed * Math.sin(degree);
        xVelocity = speed * Math.cos(degree);
    }
}