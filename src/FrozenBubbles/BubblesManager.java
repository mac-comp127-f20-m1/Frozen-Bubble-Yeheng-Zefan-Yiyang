package FrozenBubbles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
    private List<Point> pList = new ArrayList<>();
    private List<Point> allPoints;
    

    private List<List<Double>> listBubblePosition = new ArrayList<>();
    private List<Bubble> listBubble = new ArrayList<>();


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
        canvas.add(bubbles);
        // System.out.println("evenX"+evenLineXPosition);
        // System.out.println("oddX"+oddLineXPosition);
        // System.out.println("y"+yPosition);
        for(Point p : points){
            pList.add(p);
        }
        allPoints = getUnmodifiedPointList();
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
    private List<Point> getUnmodifiedPointList(){
        return Collections.unmodifiableList(pList);
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
    }
    

    private Set<Bubble> floatingBubble(){
        Set<Bubble> floatingBubbles = new HashSet<>();
        System.out.println("aaaaaaaaaaaaaaaaaaaa");
        for (Bubble bubble : listBubble){
            Set<Bubble>neighbour = bubble.getNeighbours(canvas);
            // Set<Bubble> updateNeighbour = new HashSet<>();
            // Set<Bubble> potentialFloatingBubbles = new HashSet<>();
            // Set<Bubble> visitedBubbles = new HashSet<>();
            if (neighbour.size() == 0){
                floatingBubbles.add(bubble);
            }
            if(neighbour.size()==1){
                for(Bubble b: neighbour){
                    if(b.getNeighbours(canvas).size()==1){
                        floatingBubbles.add(b);
                        floatingBubbles.add(bubble);
                    }
                }
            }
            // else{
            //     potentialFloatingBubbles.add(bubble);
            //     visitedBubbles.add(bubble);
            //     while(neighbour.size()!=0){
            //         // 这个while的condition不对！我这个写出来是无限循环的loop！
            //         // 我的想法是如果visit过的所有bubble的集合等于这些bubble的所有neighbour的（重复neighbour留一个）
            //         // 集合，这些visit过的bubble就独立，就要掉下去。但我。。。实在没想明白该怎么写。。。
            //         updateNeighbour = neighbour;
            //         neighbour = new HashSet<>();
            //         for(Bubble b: updateNeighbour){
            //             if(!potentialFloatingBubbles.contains(b)){
            //                 potentialFloatingBubbles.add(b);
            //             }
            //             if(!visitedBubbles.contains(b)){
            //                 visitedBubbles.add(b);
            //             }
            //             for (Bubble bubbleNeighbour : b.getNeighbours(canvas)){
            //                 neighbour.add(bubbleNeighbour);
            //             }
            //         }
            //     }
            //     if(potentialFloatingBubbles.equals(visitedBubbles)){
            //         for(Bubble fBubble:potentialFloatingBubbles){
            //             floatingBubble().add(fBubble);
            //         }
            //     }
            // }
        }
        return floatingBubbles;
    }
    public void fallBubble(){
        for(Bubble b: floatingBubble()){
            bubbles.remove(b);
            listBubble.remove(b);
        }
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb");
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
        // System.out.println(sameColorBubbles);
        
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
    
    public void updatePointList(CanvasWindow canvas){
        System.out.println("the size of pList is "+allPoints.size());
        for(Point p :allPoints){
            if(canvas.getElementAt(p.getX()+DIAMETER/2, p.getY()+DIAMETER/2)==null&&points.contains(p)==false){
                points.add(p);
            }
        }
        System.out.println("The size of points is "+points.size());
    }
}
