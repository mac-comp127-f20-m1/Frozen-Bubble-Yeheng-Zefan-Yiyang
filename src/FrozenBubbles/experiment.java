package FrozenBubbles;

import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

public class experiment {
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow("ExperimentalCanvas",600, 400);
        Ellipse ball = new Ellipse(100, 100, 30, 30);
        Ellipse ball2 = new Ellipse(130, 100,30,30);
        canvas.add(ball);
        canvas.add(ball2);
        
        System.out.println(canvas.getElementAt(100,100));
        List<String> aList = new ArrayList<>();
        aList.add("a");
        aList.add("b");
        aList.add("b");
        aList.add("c");
        while(aList.remove("a")){
            aList.remove("a");
        }
        while(aList.remove("b")){
            aList.remove("b");
        }
        System.out.println(aList);
    }
        
    }

