/**
 * Create a single bubble with the input x position and y position of the upper left, the width, the height, and the color.
 * 
 * The Bubble class extends the Ellipse, so it can be treated as a GraphicsObject, specifically Ellipse, on the canvas.
 * 
 * Edited by Scott Zong, Zefan Qian.
 */
package FrozenBubbles;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

import java.awt.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Bubble
 */
public class Bubble extends Ellipse{
    private Color color;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;

    public Bubble(double x, double y, double width, double height, Color color) {

        super(x, y, width, height);
        this.xPosition = x;
        this.yPosition= y;
        this.width = width;
        this.height = height;
        this.color = color;

        setFillColor(color);
    }

    /**
     * @return the color of the bubble
     */
    public Color getColor(){
        return color;
    }

    /**
     * @return the x Position of the center
     */
    public double getCenterX(){
        return xPosition + width / 2;
    }

    /**
     * @return the y Position of the center
     */
    public double getCenterY(){
        return yPosition + height / 2;
    }

    /**
     * Find the neighbour bubbles
     * @param canvas the canvas where the bubble is at
     * @return the set of the neighboured bubbles
     */
    public Set<Bubble> getNeighbours(CanvasWindow canvas){

        Set<Bubble> neighbourBubbles = new HashSet<>();
        double centerX = getCenterX();
        double centerY = getCenterY();

        // add bubble1(the one at the top-left) to the set
        if(canvas.getElementAt(centerX - width / 2, centerY - height) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX - width / 2, centerY - height));
        }

        // add bubble2(the one at the top-right) to the set
        if(canvas.getElementAt(centerX + width / 2, centerY - height) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX + width / 2, centerY - height));
        }

        // add bubble3(the one at the most left) to the set
        if(canvas.getElementAt(centerX - width, centerY) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX - width, centerY));
        }

        // add bubble4(the one at the most right) to the set
        if(canvas.getElementAt(centerX + width, centerY) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX + width, centerY));
        }

        // add bubble5(the one at the bottom-left) to the set
        if(canvas.getElementAt(centerX - width / 2, centerY + height) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX - width / 2, centerY + height));
        }

        // add bubble6(the one at the bottom-right) to the set
        if(canvas.getElementAt(centerX + width / 2, centerY + height) != null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX + width / 2, centerY + height));
        }

        return neighbourBubbles;
    }

}