package FrozenBubbles;

import edu.macalester.graphics.Ellipse;

import java.awt.Color;

public class Bubbles extends Ellipse{

    public Bubbles(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.setFillColor(color);
    }
    
}