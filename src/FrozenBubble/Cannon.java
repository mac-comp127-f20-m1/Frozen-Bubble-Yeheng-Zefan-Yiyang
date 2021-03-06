/**
 * Create a cannon with input coordinates.
 * 
 * Edited by Zefan Qian.
 */
package FrozenBubble;

import edu.macalester.graphics.Path;
import edu.macalester.graphics.Point;

public class Cannon extends Path {
    /**
     * A Cannon.
     */
    public Cannon(double x0, double x1, double x2, double x3, double y0, double y1, double y2, double y3) {
        super(new Point(x0, y0), new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), new Point(x0, y0));
    }
}