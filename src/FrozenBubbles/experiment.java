package FrozenBubbles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;

public class experiment {
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("ExperimentalCanvas",600, 400);
        GraphicsGroup bubble = new GraphicsGroup();
        Ellipse ball = new Ellipse(100, 100, 30, 30);
        Ellipse ball2 = new Ellipse(130, 100,30,30);
        // canvas.add(ball);
        // canvas.add(ball2);
        bubble.add(ball);
        bubble.add(ball2);
        
        System.out.println(canvas.getElementAt(100,100));
        List<String> aList = new ArrayList<>();
        Set<String> aSet = new HashSet<>();
        aSet.add("a");
        aSet.add("a");
        aSet.remove(null);
        System.out.println(aSet);
        System.out.println(aList);
    }
        
    }

