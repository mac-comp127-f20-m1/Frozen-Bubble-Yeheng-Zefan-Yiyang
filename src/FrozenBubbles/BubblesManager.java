package FrozenBubbles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
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
        for (Point p : points) {
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

    private List<Point> getUnmodifiedPointList() {
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


    private Set<Bubble> getFloatingBubble() {
        Set<Bubble> topBubble = new HashSet<>();
        Set<Bubble> allBubbles = new HashSet<>(listBubble);;
        Set<Bubble> floatingBubble = new HashSet<>(allBubbles);
        
        for (Bubble bubble : listBubble) {
            if(bubble.getY()==0){
                topBubble.add(bubble);
            }
        }


        Set<Bubble> connectedBubbles = findConnectedBubbles(topBubble, null);
        for(Bubble b:connectedBubbles){
            floatingBubble.remove(b);
        }
        
        return floatingBubble;
        
    }

    public void fallBubble() {
        for (Bubble b : getFloatingBubble()) {
            bubbles.remove(b);
            listBubble.remove(b);
        }
        
    }

    public void addBubble(Bubble bubble) {
        bubbles.add(bubble);
    }

    public void addBubbleToList(Bubble bubble) {
        listBubble.add(bubble);
    }


    public void destroyBubbles(Bubble cannonBubble) {
        // Color color = Color.GRAY;
        // if (cannonBubble != null) {
        //     color = cannonBubble.getColor();
        // }我觉得这些可以删？
        Color color = cannonBubble.getColor();
        Set<Bubble> sameColorBubbles = new HashSet<>();
        sameColorBubbles = findConnectedBubbles(Set.of(cannonBubble), color);
        if (sameColorBubbles.size() >= 3) {
            for (Bubble bubble : sameColorBubbles) {
                // if (bubble != null) {这个if statement是不是也可以删？只保留里面的东西就行
                    bubbles.remove(bubble);
                    listBubble.remove(bubble);
                // }
            }
        }
    }
    private Set<Bubble> findConnectedBubbles(Set<Bubble> startBubble, Color color) {
        Set<Bubble> connected = new HashSet<>(startBubble);
        Set<Bubble> bubblesToVisit = startBubble.stream()
            .flatMap(bubble -> bubble.getNeighbours(canvas).stream())
            .collect(Collectors.toSet());

        while (bubblesToVisit.size() != 0){
            Set<Bubble> currentBubbleFroup = bubblesToVisit;
            bubblesToVisit = new HashSet<>();
            for (Bubble bubble : currentBubbleFroup) {
                if (color == null || bubble.getColor() == color) {
                    if (bubble != null && !connected.contains(bubble)) {
                        connected.add(bubble);
                        for (Bubble neighbourBubble : bubble.getNeighbours(canvas)){
                            bubblesToVisit.add(neighbourBubble);
                            neighbourBubble.getNeighbours(canvas).remove(bubble);
                        }
                    }
                }
            }
        }
        return connected;
    }
    public void updatePointList(CanvasWindow canvas) {
        for (Point p : allPoints) {
            if (canvas.getElementAt(p.getX() + DIAMETER / 2, p.getY() + DIAMETER / 2) == null
                && points.contains(p) == false) {
                points.add(p);
            }
        }
    }
}
