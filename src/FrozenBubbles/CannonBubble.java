package FrozenBubbles;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;

import java.util.Random;

import java.awt.Color;

public class CannonBubble extends Ellipse{
    public Color color;

    public double xVelocity;
    public double yVelocity;

    public double xPosition;
    public double yPosition;

    public double xMaxPosition;
    public double yMaxPosition;

    public CannonBubble(double x, double y, double width, double height, double speed, 
                        double xMaxPosition, double yMaxPosition) {

        super(x, y, width, height);

        this.xPosition = x;
        this.yPosition = y;
        this.xMaxPosition = xMaxPosition;
        this.yMaxPosition = yMaxPosition;

        Random rand = new Random();

        xVelocity = speed * Math.sin(rand.nextDouble() * Math.PI);
        yVelocity = - speed * Math.cos((rand.nextDouble() - 0.5) * Math.PI);

        setFilled(true);
    }

    public void updatePosition(double dt, GraphicsGroup bubbles){
        double updateXPosition;
        double updateYPosition;
        updateXPosition = xPosition + dt * xVelocity;
        updateYPosition = yPosition + dt * yVelocity;

        if (!testHit(bubbles)){
            if (updateXPosition < 0 || updateXPosition > xMaxPosition - getWidth()){
                xVelocity = -xVelocity;
            }else if (updateYPosition <= 0){
                xVelocity = 0;
                yVelocity = 0;
                updateYPosition = 1;
            }else if ((updateXPosition == 0 && updateYPosition == 0) || (updateXPosition == xMaxPosition - getWidth() && updateYPosition
            == yMaxPosition - getHeight())){
                xVelocity = - xVelocity;
                yVelocity = - yVelocity;
            }else{
                xPosition = updateXPosition;
                yPosition = updateYPosition;
            }
        }else{
            updateXPosition -= dt * xVelocity;
            updateYPosition -= dt * yVelocity;
        }
        setPosition(updateXPosition, updateYPosition);
    }

    public boolean testHit(GraphicsGroup bubbles){
        for (int i = 0; i < 360; i++){
            if (bubbles.getElementAt((xPosition + 15) + 15.1 * Math.cos(i * Math.PI / 180), (yPosition + 15) + 15.1 * Math.sin(i * Math.PI / 180) + 0.01) != null){
                xVelocity = 0;
                yVelocity = 0;
                return true;
            }
        }

        // if(bubbles.getElementAt(xPosition - 0.01, yPosition + 0.5 * getHeight()) != null){
        //     xVelocity = 0;
        //     yVelocity = 0;
        //     return true;
        // }

        // if(bubbles.getElementAt(xPosition + getWidth() + 0.01, yPosition + 0.5 * getHeight()) != null){
        //     xVelocity = 0;
        //     yVelocity = 0;
        //     return true;
        // }

        // if(bubbles.getElementAt(xPosition + 0.5 * getWidth(), yPosition - 0.01) != null){
        //     xVelocity = 0;
        //     yVelocity = 0;
        //     return true;
        // }

        // if(bubbles.getElementAt(xPosition + 0.5 * getWidth(), yPosition + getHeight() + 0.01) != null){
        //     xVelocity = 0;
        //     yVelocity = 0;
        //     return true;
        // }

        return false;
    }



}