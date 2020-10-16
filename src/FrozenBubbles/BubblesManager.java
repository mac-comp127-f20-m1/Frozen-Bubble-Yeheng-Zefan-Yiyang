package FrozenBubbles;

import java.util.Random;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;

public class BubblesManager {

    public CanvasWindow canvas;
    public GraphicsGroup bubbles;

    public static final int ROW = 8;
    public static final int COLUMN = 20;
    public static final int DIAMETER = 30;

    public BubblesManager(CanvasWindow canvas){
        this.canvas = canvas;
        bubbles = new GraphicsGroup();
    }
    public void generateBubbles(){
        for (int i = 0; i < ROW; i += 2){
            for (int j = 0; j < COLUMN; j++){
                double xPosition = DIAMETER * j;
                double yPosition = (DIAMETER - 2.5) * i;
                Color color = getRandomColor();
                Bubbles bubble = new Bubbles(xPosition, yPosition, DIAMETER, DIAMETER, color);
                bubbles.add(bubble);
            }
        }    
        for (int i = 1; i < ROW; i += 2){
            for (int j = 0; j < COLUMN - 1; j++){
                double xPosition = 0.135 / 0.27 * DIAMETER + DIAMETER * j;
                double yPosition = (DIAMETER - 2.5) * i;
                Color color = getRandomColor();
                Bubbles bubble = new Bubbles(xPosition, yPosition, DIAMETER, DIAMETER, color);
                bubbles.add(bubble);
            }
        }
        canvas.add(bubbles);
    }
    
    public Color getRandomColor(){
        Random rand = new Random();
        int i = rand.nextInt(3);
        if (i == 0){
            return Color.RED;
        }else if (i == 1){
            return Color.YELLOW;
        }else{
            return Color.BLUE;
        }
    }
}