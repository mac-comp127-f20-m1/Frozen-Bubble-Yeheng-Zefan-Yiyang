package FrozenBubbles;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.awt.Color;

public class CannonBubble extends Ellipse{
    private Color color;

    private double xVelocity;
    private double yVelocity;

    private double xPosition;
    private double yPosition;

    private double xMaxPosition;
    private double yMaxPosition;

    private double degree;
    private double speed;

    public CannonBubble(double x, double y, double width, double height, double speed, 
                        double xMaxPosition, double yMaxPosition, CanvasWindow canvas) {

        super(x, y, width, height);

        this.xPosition = x;
        this.yPosition = y;
        this.xMaxPosition = xMaxPosition;
        this.yMaxPosition = yMaxPosition;
        this.speed = speed;
        

        

        Color colorRandom = getRandomColor();
        setFillColor(colorRandom);
        this.color = colorRandom;

        canvas.onClick(event->setVelocity(setDegree(event.getPosition())));
        


        setFilled(true);
    }

    public void updatePosition(double dt, GraphicsGroup bubbles,CanvasWindow canvas){
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

    public Color getColor(){
        return color;
    }

    public double getXVelocity(){
        return xVelocity;
    }

    public double getXPosition(){
        return xPosition;
    }

    public double getYPosition(){
        return yPosition;
    }

    private double setDegree(Point point){
        double hypotenuse = Math.hypot(point.getX()-300,point.getY()-685);
        double cathetus = point.getX()-300;
        return Math.acos(cathetus/hypotenuse);
    }

    private void setVelocity(double degree){
        yVelocity = -speed * Math.sin(degree);
        xVelocity = speed * Math.cos(degree);
    }
}