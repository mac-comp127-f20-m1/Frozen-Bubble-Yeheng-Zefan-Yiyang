package FrozenBubbles;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

        canvas.onClick(event->setVelocity(setDegree(event.getPosition())));
        // not sure if it is better to use onClick.() or onMouseDown.()
        


        setFilled(true);
    }

    public void updatePosition(double dt, GraphicsGroup bubbles,CanvasWindow canvas){
        double updateXPosition;
        double updateYPosition;
        updateXPosition = xPosition + dt * xVelocity;
        updateYPosition = yPosition + dt * yVelocity;

        if (!testHit(bubbles,canvas)){
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

    public boolean testHit(GraphicsGroup bubbles,CanvasWindow canvas){
        for (int i = 0; i < 360; i++){
            if (bubbles.getElementAt((xPosition + 15) + 15.1 * Math.cos(i * Math.PI / 180), (yPosition + 15) + 15.1 * Math.sin(i * Math.PI / 180) + 0.01) != null){
                xVelocity = 0;
                yVelocity = 0;
                canvas.remove(this);
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
    public double getCenterX(){
        return xPosition+width/2;
    }
    public double getCenterY(){
        return yPosition+height/2;
    }
    public Set<Bubble> getNeighbours(CanvasWindow canvas){
        Set<Bubble> neighbourBubbles = new HashSet<>();
        double centerX = getCenterX();
        double centerY = getCenterY();
        if(canvas.getElementAt(centerX-width/2, centerY-height)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX-width/2, centerY-height));
            // add bubble1(the one at the top-left) to the set
        }
        if(canvas.getElementAt(centerX+width/2, centerY-height)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX+width/2, centerY-height));
            // add bubble2(the one at the top-right) to the set
        }
        if(canvas.getElementAt(centerX-width, centerY)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX-width, centerY));
            // add bubble3(the one at the most left) to the set
        }
        if(canvas.getElementAt(centerX+width, centerY)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX-width/2, centerY));
            // add bubble4(the one at the most right) to the set
        }
        if(canvas.getElementAt(centerX-width/2, centerY+height)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX-width/2, centerY+height));
            // add bubble5(the one at the bottom-left) to the set
        }
        if(canvas.getElementAt(centerX+width/2, centerY+height)!=null){
            neighbourBubbles.add((Bubble)canvas.getElementAt(centerX+width/2, centerY+height));
            // add bubble6(the one at the bottom-right) to the set
        }
        return neighbourBubbles;

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