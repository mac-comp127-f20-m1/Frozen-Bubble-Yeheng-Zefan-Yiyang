package FrozenBubbles;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

public class experiment {
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("ExperimentalCanvas",600, 400);
        Ellipse ball = new Ellipse(100, 100, 30, 30);
        Ellipse ball2 = new Ellipse(130, 100,30,30);
        canvas.add(ball);
        canvas.add(ball2);
        System.out.println(canvas.getElementAt(130.1,115));
    }
        
    }

