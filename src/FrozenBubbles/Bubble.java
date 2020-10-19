package FrozenBubbles;

import edu.macalester.graphics.Ellipse;

import java.awt.Color;

public class Bubble extends Ellipse{
    private Color color;
    private double xPosition;
    private double yPosition;

    public Bubble(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.setFillColor(color);
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    private void addNeighbor(){
        
    }

    private void removeNeighbor(){

    }
    
    
}