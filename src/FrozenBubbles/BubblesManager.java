package FrozenBubbles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
    private List<Double> evenLineYPosition = new ArrayList<>();
    private List<Double> oddLineYPosition = new ArrayList<>();
    private List<Double> yPosition = new ArrayList<>();
    private List<Point> points = new ArrayList<>();
    private List<Bubble> bubbleAround = new ArrayList<>();

    private List<List<Double>> listBubblePosition = new ArrayList<>();
    private List<Bubble> listBubble = new ArrayList<>();

    private Map<Bubble, List<Bubble>> map = new HashMap<>();

    public BubblesManager(CanvasWindow canvas) {

        this.canvas = canvas;
        bubbles = new GraphicsGroup();

    }

    public void generateBubbles() {

        for (int i = 0; i < ROW; i += 2) {
            for (int j = 0; j < COLUMN; j++) {
                double xPosition = DIAMETER * j;
                evenLineXPosition.add(xPosition);
                double yPosition = (DIAMETER - 2.5) * i;
                Color color = getRandomColor();
                Bubble bubble = new Bubble(xPosition, yPosition, DIAMETER, DIAMETER, color);
                bubbles.add(bubble);
                listBubble.add(bubble);
                listBubblePosition.add(List.of(xPosition, yPosition));
            }
        }

        for (int i = 1; i < ROW; i += 2) {
            for (int j = 0; j < COLUMN - 1; j++) {
                double xPosition = 0.135 / 0.27 * DIAMETER + DIAMETER * j;
                oddLineXPosition.add(xPosition);
                double yPosition = (DIAMETER - 2.5) * i;
                Color color = getRandomColor();
                Bubble bubble = new Bubble(xPosition, yPosition, DIAMETER, DIAMETER, color);
                listBubble.add(bubble);
                listBubblePosition.add(List.of(xPosition, yPosition));
                bubbles.add(bubble);
            }
        }

        for (int i = 0; i < MAX_RAW; i++) {
            double y = (DIAMETER - 2.5) * i;
            yPosition.add(y);
        }

        for (int i = 0; i < yPosition.size(); i = i + 2) {
            evenLineYPosition.add(yPosition.get(i));
        }

        for (int i = 1; i < yPosition.size(); i = i + 2) {
            oddLineYPosition.add(yPosition.get(i));
        }

        for (double evenX : evenLineXPosition) {
            for (double evenY : evenLineYPosition) {
                Point evenLinePoint = new Point(evenX, evenY);
                points.add(evenLinePoint);
            }
        }

        for (double oddX : oddLineXPosition) {
            for (double oddY : oddLineYPosition) {
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


    public Color getRandomColor() {
        Random rand = new Random();
        int i = rand.nextInt(4);
        if (i == 0) {
            return Color.RED;
        } else if (i == 1) {
            return Color.YELLOW;
        } else if (i == 2) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    public GraphicsGroup getGraphicsGroup() {
        return bubbles;
    }


    public void correctCannonBubble(CannonBubble cannonBubble) {
        double currentY = cannonBubble.getY();
        double currentX = cannonBubble.getX();
        double supposedY = yPosition.stream().min(Comparator.comparing(y -> Math.abs(y - currentY))).orElse(null);
        double i = supposedY / 27.5;


        if (i % 2 == 0) {
            double supposedX = evenLineXPosition.stream().min(Comparator.comparing(x -> Math.abs(x - currentX)))
                .orElse(null);
            Point supposedPoint = points.stream()
                .min(Comparator.comparing(p -> Math.hypot((p.getX() - supposedX), (p.getY() - supposedY))))
                .orElse(null);

            while (points.remove(supposedPoint)) {
                points.remove(supposedPoint);
            }
            cannonBubble.setPosition(supposedPoint);
        } else {
            double supposedX = oddLineXPosition.stream().min(Comparator.comparing(x -> Math.abs(x - currentX)))
                .orElse(null);
            Point supposedPoint = points.stream()
                .min(Comparator.comparing(p -> Math.hypot((p.getX() - supposedX), (p.getY() - supposedY))))
                .orElse(null);

            while (points.remove(supposedPoint)) {
                points.remove(supposedPoint);
            }
            cannonBubble.setPosition(supposedPoint);

        }
        // Bubble newBubble = new Bubble(cannonBubble.getX(), cannonBubble.getY(), cannonBubble.getWidth(),
        //     cannonBubble.getHeight(), cannonBubble.getColor());
        //     // System.out.println("currentcolor is "+ currentColor);
        //     // System.out.println("cannonBubble.getColor() is "+cannonBubble.getColor());
        //     // System.out.println(newBubble.getColor());
        // canvas.add(newBubble);
        // canvas.remove(cannonBubble);
        // bubbles.add(newBubble);
        // listBubble.add(newBubble);
        // listBubblePosition.add(List.of(cannonBubble.getX(), cannonBubble.getY()));
    }

    // public void updateMap() {
    //     // List<Bubble> bubbleAround = new ArrayList<>();
    //     Bubble bubble = listBubble.get(listBubble.size() - 1);
    //     if (bubbles.getElementAt(bubble.getX() - DIAMETER, bubble.getY()) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() - DIAMETER
    //                 && listBubblePosition.get(i).get(1) == bubble.getY()) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     if (bubbles.getElementAt(bubble.getX() + DIAMETER, bubble.getY()) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() + DIAMETER
    //                 && listBubblePosition.get(i).get(1) == bubble.getY()) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     if (bubbles.getElementAt(bubble.getX() - DIAMETER * 0.5, (bubble.getY() - DIAMETER + 2.5)) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() - 0.5 * DIAMETER
    //                 && listBubblePosition.get(i).get(1) == (bubble.getY() - DIAMETER + 2.5)) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     if (bubbles.getElementAt(bubble.getX() - DIAMETER * 0.5, (bubble.getY() + DIAMETER - 2.5)) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() - DIAMETER * 0.5
    //                 && listBubblePosition.get(i).get(1) == bubble.getY() + DIAMETER - 2.5) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     if (bubbles.getElementAt(bubble.getX() + DIAMETER * 0.5, (bubble.getY() - DIAMETER + 2.5)) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() + 0.5 * DIAMETER
    //                 && listBubblePosition.get(i).get(1) == bubble.getY() - DIAMETER + 2.5) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     if (bubbles.getElementAt(bubble.getX() + DIAMETER * 0.5, (bubble.getY() + DIAMETER - 2.5)) != null) {
    //         for (int i = 0; i < listBubble.size(); i++) {
    //             if (listBubblePosition.get(i).get(0) == bubble.getX() + 0.5 * DIAMETER
    //                 && listBubblePosition.get(i).get(1) == bubble.getY() + DIAMETER - 2.5) {
    //                 bubbleAround.add(listBubble.get(i));
    //             }
    //         }
    //     }
    //     map.put(bubble, bubbleAround);
    // }


    // public Map<Bubble, List<Bubble>> createInitialMap() {
    //     for (Bubble bubble : listBubble) {
    //         // List<Bubble> bubbleAround = new ArrayList<>();
    //         if (bubbles.getElementAt(bubble.getX() - DIAMETER, bubble.getY()) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() - DIAMETER
    //                     && listBubblePosition.get(i).get(1) == bubble.getY()) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         if (bubbles.getElementAt(bubble.getX() + DIAMETER, bubble.getY()) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() + DIAMETER
    //                     && listBubblePosition.get(i).get(1) == bubble.getY()) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         if (bubbles.getElementAt(bubble.getX() - DIAMETER * 0.5, (bubble.getY() - DIAMETER + 2.5)) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() - 0.5 * DIAMETER
    //                     && listBubblePosition.get(i).get(1) == (bubble.getY() - DIAMETER + 2.5)) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         if (bubbles.getElementAt(bubble.getX() - DIAMETER * 0.5, (bubble.getY() + DIAMETER - 2.5)) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() - DIAMETER * 0.5
    //                     && listBubblePosition.get(i).get(1) == bubble.getY() + DIAMETER - 2.5) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         if (bubbles.getElementAt(bubble.getX() + DIAMETER * 0.5, (bubble.getY() - DIAMETER + 2.5)) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() + 0.5 * DIAMETER
    //                     && listBubblePosition.get(i).get(1) == bubble.getY() - DIAMETER + 2.5) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         if (bubbles.getElementAt(bubble.getX() + DIAMETER * 0.5, (bubble.getY() + DIAMETER - 2.5)) != null) {
    //             for (int i = 0; i < listBubble.size(); i++) {
    //                 if (listBubblePosition.get(i).get(0) == bubble.getX() + 0.5 * DIAMETER
    //                     && listBubblePosition.get(i).get(1) == bubble.getY() + DIAMETER - 2.5) {
    //                     bubbleAround.add(listBubble.get(i));
    //                 }
    //             }
    //         }
    //         map.put(bubble, bubbleAround);
    //     }
    //     return map;
    // }

    // public Map<Bubble, List<Bubble>> getMap() {
    //     return map;
    // }

    // public void ballCancel(CannonBubble cannonBubble, List<Bubble> sameColorWithCannonball, List<Bubble> visited) {
    //     for (int i = 0; i < bubbleAround.size(); i++) {
    //         if (bubbleAround.get(i) != null && cannonBubble != null){
    //             if (bubbleAround.get(i).getColor() == cannonBubble.getColor()) {
    //                 sameColorWithCannonball.add(bubbleAround.get(i));
    //                 bubbleAround.remove(i);
    //                 return;
    //             } else if (bubbleAround.get(i).getColor() != cannonBubble.getColor()) {
    //                 visited.add(bubbleAround.get(i));
    //                 return;
    //             }
    //             for (Bubble bubble : sameColorWithCannonball) {
    //                 map.get(bubble);
    //                 ballCancel(cannonBubble, sameColorWithCannonball, visited);
    //                 if (sameColorWithCannonball.size() >= 3) {
    //                     canvas.remove(cannonBubble);
    //                 }
    //             }
    //         }
    //     }
    // }

    private List<Bubble> floatingBubblesList(){
        List<Bubble> floatingBubbles = new ArrayList<>();
        for (Bubble bubble : listBubble){
            if (bubble.getNeighbours(canvas).size() == 0){
                floatingBubbles.add(bubble);
            }
        }
        return floatingBubbles;
    }
    public void fallBubble(){
        for(Bubble b: floatingBubblesList()){
            bubbles.remove(b);
            listBubble.remove(b);
        }
    }

    public void addBubble (Bubble bubble){
        bubbles.add(bubble);
    }

    public void addBubbleToList(Bubble bubble){
        listBubble.add(bubble);
    }
    

    public void destroyBubbles(Bubble cannonBubble) {
        canvas.remove(cannonBubble);
        Color color = Color.GRAY;
        if (cannonBubble != null){
            color = cannonBubble.getColor();
        }
        Set<Bubble> neighbour = cannonBubble.getNeighbours(canvas);
        Set<Bubble> updateNeighbour = new HashSet<>();
        // System.out.println(neighbour);
        Set<Bubble> sameColorBubbles = new HashSet<>();

        while (neighbour.size() != 0){
            updateNeighbour = neighbour;
            neighbour = new HashSet<>();
            for (Bubble bubble : updateNeighbour) {
                if (bubble != null && bubble.getColor() == color && !sameColorBubbles.contains(bubble)) {
                    sameColorBubbles.add(bubble);
                    for (Bubble bubbleNeighbour : bubble.getNeighbours(canvas)){
                        neighbour.add(bubbleNeighbour);
                        bubbleNeighbour.getNeighbours(canvas).remove(bubble);
                    }

                }
            }
        }
        
        sameColorBubbles.add((Bubble)canvas.getElementAt(cannonBubble.getCenterX(), cannonBubble.getCenterY()));
        System.out.println(sameColorBubbles);
        
        if (sameColorBubbles.size() >= 3) {
            for (Bubble bubble : sameColorBubbles) {
                // canvas.remove(bubble);
                if (bubble != null){
                    // if (bubble.getColor() == color){
                        bubbles.remove(bubble);
                        listBubble.remove(bubble);
                    // }
                }
                //bubbles.remove(cannonBubble);
                // canvas.remove(cannonBubble);
                
                // canvas.remove(canvas.getElementAt(cannonBubble.getCenter()));
                // canvas.remove(cannonBubble);
            }
            // canvas.remove(cannonBubble);
        }
        // destroyFloatingBubble(canvas);
        // else{
        //     bubbles.add(new Bubble(cannonBubble.getX(), cannonBubble.getY(), cannonBubble.getWidth(), cannonBubble.getHeight(), cannonBubble.getColor()));
        //     canvas.remove(cannonBubble);
        // }
    }
    private void destroyFloatingBubble(CanvasWindow canvas){
        for(Bubble b: listBubble){
            if(b.getNeighbours(canvas).size()==0){
                bubbles.remove(b);
                // listBubble.remove(b);
            }
        }
    }
}
