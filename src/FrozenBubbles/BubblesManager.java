/**
 * This class generates all the bubbles with random colors (red, yellow, blue, green) and manages the interaction 
 * between the cannon bubble and the bubbles.
 * 
 * Specifically, this class will correct the position of the cannon bubble when it hits the bubbles. If there are
 * more than three bubbles with the same color of the cannon bubble, all the connected bubble with the same color
 * will be destroyed. Then, if there are bubbles not connected to the wall, they will also be destroyed.
 * 
 * Edited by Scott Zong, Zefan Qian.
 * 
 * We thank Professor Paul Cantrell for helping us finishing the algorithm destroying the floating bubbles.
*/
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

    /**
     * Initialize the canvas and create a graphics group to include all the bubbles.
     * @param canvas the canvas where all the bubbles are at
     */
    public BubblesManager(CanvasWindow canvas) {
        this.canvas = canvas;
        bubbles = new GraphicsGroup();
    }

    /**
     * Generate the initial bubbles and store all the information of bubbles to different lists for future processing.
     */
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

    /**
     * Set a random color for the initial bubbles.
     * @return a random color from red, yellow, green, and blue with equal possibilities
     */
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

    /**
     * @return all the bubbles as a graphics group
     */
    public GraphicsGroup getGraphicsGroup() {
        return bubbles;
    }

    /**
     * @return an unmodifiable list of all the points
     */
    private List<Point> getUnmodifiedPointList() {
        return Collections.unmodifiableList(pList);
    }

    /**
     * Correct the position of the cannonBubble so it does not appear at a weird place.
     * @param cannonBubble the cannon bubble which hit the bubbles
     */
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

    /**
     * Find bubbles that are connected and floating, (being floating means that all the connected bubbles 
     * do not connect to the walls).
     * @return a set of bubbles which are connected and floating.
     */
    private Set<Bubble> getFloatingBubble() {

        Set<Bubble> topBubble = new HashSet<>();
        Set<Bubble> allBubbles = new HashSet<>(listBubble);;
        Set<Bubble> floatingBubble = new HashSet<>(allBubbles);
        
        for (Bubble bubble : listBubble) {
            if(bubble.getY() == 0){
                topBubble.add(bubble);
            }
        }

        Set<Bubble> connectedBubbles = findConnectedBubbles(topBubble, null);

        for(Bubble b:connectedBubbles){
            floatingBubble.remove(b);
        }
        
        return floatingBubble;
    }

    /**
     * Remove all the floating bubbles from the canvas.
     */
    public void fallBubble() {
        for (Bubble b : getFloatingBubble()) {
            bubbles.remove(b);
            listBubble.remove(b);
        }
        
    }

    /**
     * Add a bubble to the graphicgroup of all the bubbles
     * @param bubble the bubble will be added
     */
    public void addBubble(Bubble bubble) {
        bubbles.add(bubble);
    }

    /**
     * Add a bubble to the list of all the bubbles
     * @param bubble the bubble wiil be added
     */
    public void addBubbleToList(Bubble bubble) {
        listBubble.add(bubble);
    }

    /**
     * After the cannon bubble hit the bubbles, if there are more than three bubbles, including the cannon
     * bubble, connected with the same color, all of them with the same color with the cannon bubble will 
     * be destroyed.
     * @param cannonBubble the cannon bubble which hit the bubbles
     */
    public void destroyBubbles(Bubble cannonBubble) {
        Color color = cannonBubble.getColor();
        Set<Bubble> sameColorBubbles = new HashSet<>();
        sameColorBubbles = findConnectedBubbles(Set.of(cannonBubble), color);
        if (sameColorBubbles.size() >= 3) {
            for (Bubble bubble : sameColorBubbles) {
                bubbles.remove(bubble);
                listBubble.remove(bubble);
            }
        }
    }

    /**
     * Find the bubble connected together with the given color.
     * @param startBubble a set of the cannon bubble itself
     * @param color the color of the cannon bubble
     * @return a set of bubbles connected together.
     */
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

    /**
     * Update the point list when there are new bubbles created.
     * @param canvas the canvas where all the bubbles are at.
     */
    public void updatePointList(CanvasWindow canvas) {
        for (Point p : allPoints) {
            if (canvas.getElementAt(p.getX() + DIAMETER / 2, p.getY() + DIAMETER / 2) == null
                && points.contains(p) == false) {
                points.add(p);
            }
        }
    }
}
