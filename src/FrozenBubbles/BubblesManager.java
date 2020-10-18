package FrozenBubbles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;

public class BubblesManager {

    private CanvasWindow canvas;
    private GraphicsGroup bubbles;

    private static final int ROW = 8;   
    private static final int COLUMN = 20;
    private static final int DIAMETER = 30;
    private static final int MAX_RAW = 25;
    private List<Double> evenLineXPosition = new ArrayList<>();
    private List<Double> oddLineXPosition = new ArrayList<>();
    private List<Double> evenLineYPosition =new ArrayList<>();
    private List<Double> oddLineYPosition = new ArrayList<>();
    private List<Double> yPosition = new ArrayList<>();
    private List<Point> points = new ArrayList<>();

    public BubblesManager(CanvasWindow canvas){
        this.canvas = canvas;
        bubbles = new GraphicsGroup();
    }
    public void generateBubbles(){
        for (int i = 0; i < ROW; i += 2){
            for (int j = 0; j < COLUMN; j++){
                double xPosition = DIAMETER * j;
                evenLineXPosition.add(xPosition);
                double yPosition = (DIAMETER - 2.5) * i;
                Color color = getRandomColor();
                Bubbles bubble = new Bubbles(xPosition, yPosition, DIAMETER, DIAMETER, color);
                bubbles.add(bubble);
            }
        }    
        for (int i = 1; i < ROW; i += 2){
            for (int j = 0; j < COLUMN - 1; j++){
                double xPosition = 0.135 / 0.27 * DIAMETER + DIAMETER * j;
                oddLineXPosition.add(xPosition);
                double yPosition = (DIAMETER - 2.5) * i;
                
                Color color = getRandomColor();
                Bubbles bubble = new Bubbles(xPosition, yPosition, DIAMETER, DIAMETER, color);
                bubbles.add(bubble);
            }
        }
        for(int i =0; i<MAX_RAW;i++){
            double y = (DIAMETER - 2.5) * i;
            yPosition.add(y);
        }
        for(int i =0; i<yPosition.size();i=i+2){
            evenLineYPosition.add(yPosition.get(i));
        }
        for(int i =1; i<yPosition.size();i=i+2){
            oddLineYPosition.add(yPosition.get(i));
        }
        for(double evenX:evenLineXPosition){
            for (double evenY:evenLineYPosition){
                Point evenLinePoint = new Point(evenX, evenY);
                points.add(evenLinePoint);
            }
        }
        for(double oddX:oddLineXPosition){
            for (double oddY:oddLineYPosition){
                Point oddLinePoint = new Point(oddX, oddY);
                points.add(oddLinePoint);
            }
        }
        // System.out.println("evenX"+evenLineXPosition);
        // System.out.println("oddX"+oddLineXPosition);
        // System.out.println("y"+yPosition);
        // System.out.println("Point"+points);

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

    public GraphicsGroup getGraphicsGroup(){
        return bubbles;
    }
    
    
    public void correctCannonBubble(CannonBubble cannonBubble){
        double currentY = cannonBubble.getY();
        double currentX = cannonBubble.getX();
        double supposedY = yPosition.stream().min(Comparator.comparing(y->Math.abs(y-currentY))).orElse(null);
        double i = supposedY/27.5;
        if(i%2 ==0){
            double supposedX = evenLineXPosition.stream().min(Comparator.comparing(x->Math.abs(x-currentX))).orElse(null);
            Point supposedPoint = points.stream().min(Comparator.comparing(p->Math.hypot((p.getX()-supposedX),(p.getY()-supposedY)))).orElse(null);
            
            while(points.remove(supposedPoint)){
                points.remove(supposedPoint);
            }
            cannonBubble.setPosition(supposedPoint);
        }
        else{
            double supposedX = oddLineXPosition.stream().min(Comparator.comparing(x->Math.abs(x-currentX))).orElse(null);
            Point supposedPoint = points.stream().min(Comparator.comparing(p->Math.hypot((p.getX()-supposedX),(p.getY()-supposedY)))).orElse(null);
        
            while(points.remove(supposedPoint)){
                points.remove(supposedPoint);
            }
            cannonBubble.setPosition(supposedPoint);
        }
        bubbles.add(cannonBubble);
    }
    

}