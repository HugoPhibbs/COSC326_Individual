package E3_koch_snowflake;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Program class to draw a Koch snowflake according to input from a user
 */
public class KochSnowflake {

    /**
     * Order of this Snowflake
     */
    private final int order;

    /**
     * ArrayList of Line2D.Double objects containing the sides of this snowflake
     */
    private final ArrayList<Line2D.Double> sides;

    /**
     *
     */
    private final Point2D.Double center;

    /**
     * Creates a KochSnowflake
     *
     * @param order      order for the Koch Snowflake to be created
     * @param sideLength double for a length of one side for an order 1 koch snowflake
     * @param center     Line2D.Double object for the centre of this snowflake
     */
    KochSnowflake(int order, double sideLength, Point2D.Double center) {
        this.order = order;
        this.center = center;
        this.sides = findSidesRec(1, order, order1Sides(sideLength, center));
    }

    /**
     * Creates a KochSnowflake
     *
     * @param order  int for the order of this snowflake
     * @param center Point2D.Double for the center of this Snowflake
     * @param sides  ArrayList containing Line2D.Double objects for the sides of this snowflake
     */
    KochSnowflake(int order, Point2D.Double center, ArrayList<Line2D.Double> sides) {
        this.order = order;
        this.center = center;
        this.sides = sides;
    }

    /**
     * Finds out if a size inputted by a user is valid or not
     *
     * @param order String for size entered
     * @return boolean as described
     */
    public static boolean orderIsValid(String order) {
        if (order == null) {
            return false;
        }
        if (isInt(order)) {
            return Integer.parseInt(order) > 0;
        }
        return false;
    }

    /**
     * Checks if an inputted string is an integer or not
     *
     * @param str String to be checked
     * @return boolean as described
     */
    private static boolean isInt(String str) {
        float num;
        try {
            num = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return (num == (int) num);
    }

    /**
     * Getter method for the order of this SnowFlake
     *
     * @return int as described
     */
    public int getOrder() {
        return order;
    }

    /**
     * Getter method for the sides of this SnowFlake
     *
     * @return ArrayList containing Line2D.Double objects as described
     */
    public ArrayList<Line2D.Double> getSides() {
        return sides;
    }

    /**
     * Finds the points for an order 1 snowflake.
     * <p>
     * This is used to start the recursion
     * <p>
     * Is centered on the origin
     *
     * @param sideLength double for the length of a triangle side
     * @param center     Point2D.Double object for the centre of the Snowflake
     * @return Array of Line2D.Double objects for the starting triangle
     */
    private ArrayList<Line2D.Double> order1Sides(double sideLength, Point2D.Double center) {
        double height = sideLength * Math.sin(Math.toRadians(60));
        Point2D b = new Point2D.Double(center.getX() - sideLength / 2, center.getY() + height / 2);
        Point2D a = new Point2D.Double(center.getX(), center.getY() - height / 2);
        Point2D c = new Point2D.Double(center.getX() + sideLength / 2, center.getY() + height / 2);
        return new ArrayList<>(Arrays.asList(new Line2D.Double(a, b), new Line2D.Double(b, c), new Line2D.Double(c, a)));
    }

    /**
     * The main part of this program. Does the recursion required to find all the line segments for an order of
     * Koch Snowflake
     *
     * @param currOrder int for the current order of the snowflake
     * @param reqOrder  int for the required order of snowflake to be drawn
     * @param currSides Line2D.Double array containing the current sides of the snowflake
     * @return Line2D.Double ArrayList containing all of the sides of a Koch Snowflake with order reqOrder
     */
    public ArrayList<Line2D.Double> findSidesRec(int currOrder, int reqOrder, ArrayList<Line2D.Double> currSides) {
        if (currOrder == reqOrder) {
            return currSides;
        } else {
            ArrayList<Line2D.Double> newSides = new ArrayList<Line2D.Double>();
            for (Line2D.Double side : currSides) {
                ArrayList<Line2D.Double> splitSide = splitSide(side);
                newSides.addAll(splitSide);
            }
            return findSidesRec(currOrder + 1, reqOrder, newSides);
        }
    }

    /**
     * Splits a side of a koch snow flake according to how a koch snowflake is created
     * into 4 sides
     *
     * @param side Line2D object representing the side of a koch snow flake
     * @return ArrayList containing Line2D.Double side objects of the inputted side split up
     */
    private ArrayList<Line2D.Double> splitSide(Line2D.Double side) {
        Point2D.Double p1 = (Point2D.Double) side.getP1();
        Point2D.Double p5 = (Point2D.Double) side.getP2();
        double xDiff = coordDiff(p1, p5, "x");
        double yDiff = coordDiff(p1, p5, "y");
        Point2D.Double p2 = addToPoint(p1, xDiff / 3, yDiff / 3);
        Point2D.Double p4 = addToPoint(p1, xDiff * 2 / 3, yDiff * 2 / 3);
        Point2D.Double p3 = findP3(side);
        Line2D.Double line1 = new Line2D.Double(p1, p2);
        Line2D.Double line2 = new Line2D.Double(p2, p3);
        Line2D.Double line3 = new Line2D.Double(p3, p4);
        Line2D.Double line4 = new Line2D.Double(p4, p5);
        return new ArrayList<>(Arrays.asList(line1, line2, line3, line4));
    }

    /**
     * Finds the 3rd point for the side of a koch snowflake
     * <p>
     * Does some matrix geometry!
     * <p>
     * This does all the hard computation that is required for the splitting of a side, other calculations
     * are relatively trivial
     *
     * @param side Line2D.Double object that is the side is being split up
     * @return Point2D object for the correct 3rd point in the split side of a koch snowflake
     */
    private Point2D.Double findP3(Line2D.Double side) {
        double xDiff = lineXDiff(side) / 3;
        double yDiff = lineYDiff(side) / 3;
        double[] v = {xDiff, yDiff};
        double turnRads = Math.toRadians(60); // Radians of turn
        double[] u = {xDiff * Math.cos(turnRads) - yDiff * (Math.sin(turnRads)), xDiff * Math.sin(turnRads) + yDiff * Math.cos(turnRads)};
        double[] w = {v[0] + u[0], u[1] + v[1]};
        return new Point2D.Double(side.getX1() + w[0], side.getY1() + w[1]);
    }

    /**
     * Finds the different in x coordinates from the start to the end of a line
     *
     * @param line Line2D object
     * @return double as described
     */
    private double lineXDiff(Line2D line) {
        return line.getX2() - line.getX1();
    }

    /**
     * Finds the different in x coordinates from the start to the end of a line
     *
     * @param line Line2D object
     * @return double as described
     */
    private double lineYDiff(Line2D line) {
        return line.getY2() - line.getY1();
    }

    /**
     * Adds x and y values to a Point2D object, then returns the new point
     *
     * @param point Point2D object
     * @param x     double for x val to be added
     * @param y     double for y val to be added
     * @return Point2D object as described
     */
    private Point2D.Double addToPoint(Point2D.Double point, double x, double y) {
        return new Point2D.Double(point.getX() + x, point.getY() + y);
    }

    /**
     * Finds the difference in coordinates for a given axes between two points
     * <p>
     * Not expressed in absolute terms, it's treated from difference in coordinates going from P1->P2
     *
     * @param p1   Line2D.Double object for the first point
     * @param p2   Line2.Double object for the second point
     * @param axis String for which axis the coordinate difference should be calculated for
     * @return double for difference as described
     */
    private double coordDiff(Point2D.Double p1, Point2D.Double p2, String axis) {
        assert axis.equals("x") || axis.equals("y") : "Axes must be either x or y!";
        if (axis.equals("x")) {
            return p2.getX() - p1.getX();
        } else {
            return p2.getY() - p1.getY();
        }
    }

    /**
     * Translates a side of the snowflake by an x and y value
     *
     * @param side  Line2D.Double for a side of the snowflake
     * @param xDiff double for what x points on the inputted side should be translated by
     * @param yDiff double for what y points on the inputted side should be translated by
     * @return Line2D.Double for the inputted side translated as described
     */
    private Line2D.Double translateSide(Line2D.Double side, double xDiff, double yDiff) {
        return new Line2D.Double(
                addToPoint((Point2D.Double) side.getP1(), xDiff, yDiff),
                addToPoint((Point2D.Double) side.getP2(), xDiff, yDiff));
    }

    /**
     * Resizes the side of a snowflake by a coefficient
     *
     * @param side       side object that represents the side of a Koch Snowflake
     * @param resizeCoef double for how the size of this line should be changed
     * @return a new Line2D object for the inputted side resized appropriately
     */
    private Line2D.Double resizeSide(Line2D side, double resizeCoef) {
        return new Line2D.Double(
                side.getX1() * resizeCoef, side.getY1() * resizeCoef,
                side.getX2() * resizeCoef, side.getY2() * resizeCoef);
    }

    /**
     * Resizes the sides of this snowflake by a resize coefficient
     *
     * @param resizeCoef double for how the sizes of the lines should be changed
     * @return ArrayList of Line2D.Double objects for the sides of this snowflake resized
     */
    private ArrayList<Line2D.Double> resizeSides(double resizeCoef) {
        ArrayList<Line2D.Double> newSides = new ArrayList<>();
        for (Line2D.Double side : sides) {
            newSides.add(resizeSide(side, resizeCoef));
        }
        return newSides;
    }

    /**
     * Translates a list of sides by an x and y value
     *
     * @param x double for the change in x values
     * @param y double for the change in y values
     * @return Arraylist containing Line2D.Double objects for the sides translated by inputted x and y
     */
    private ArrayList<Line2D.Double> translateSides(ArrayList<Line2D.Double> sides, double x, double y) {
        ArrayList<Line2D.Double> newSides = new ArrayList<>();
        for (Line2D.Double side : sides) {
            newSides.add(translateSide(side, x, y));
        }
        return newSides;
    }

    /**
     * Finds the center of a snowflake with given sides
     *
     * Assumes that the sides actually describes a koch snowflake. This method isn't very stable and relies heavily on
     * how sides are handled throughout this program. e.g. it assumes that the first point of the concealed order 1 snowflake
     * is the first point in the inputted sides, and that the sides are in order.
     *
     * Works by finding the order 1 snowflake that is concealed in within the inputted snowflake itself. From there, it is easy
     * to find the center of this order 1 snowflake through simple geometry.
     *
     * @param snowflakeSides ArrayList containing Line2D.Double objects for the sides of a snowflake
     * @return Point2D object as described
     */
    private Point2D.Double snowflakeCenter(ArrayList<Line2D.Double> snowflakeSides) {
        int numSides = snowflakeSides.size();
        assert numSides % 3 == 0: "Inputted sides don't describe a koch snowflake";
        Point2D.Double p1 = (Point2D.Double) snowflakeSides.get(0).getP1();
        Point2D.Double p2 = (Point2D.Double) snowflakeSides.get((int) (numSides * (1f/3))).getP1();
        Point2D.Double p3 = (Point2D.Double) snowflakeSides.get((int) (numSides * (2f/3))).getP1();
        double xCenter = (p1.getX() + p2.getX() + p3.getX()) / 3;
        double yCenter = (p1.getY() + p2.getY() + p3.getY()) / 3;
        return new Point2D.Double(xCenter, yCenter);
    }

    /**
     * Transforms this Snowflake to a new centre and resizes its sides
     *
     * @param newCenter  Point2D.Double object for the new center of this snowflake, when used with a GUI, this is
     *                   intended to be where the snowflake should be centered on the frame
     * @param resizeCoef double for how the side of this snowflake should change
     * @return a new KochSnowflake that is this one transformed
     */
    public KochSnowflake transform(Point2D.Double newCenter, double resizeCoef) {
        ArrayList<Line2D.Double> resizedSides = resizeSides(resizeCoef);
        Point2D.Double resizedSidesCenter = snowflakeCenter(resizedSides);
        double xDiff = coordDiff(resizedSidesCenter, newCenter, "x");
        double yDiff = coordDiff(resizedSidesCenter, newCenter, "y");
        ArrayList<Line2D.Double> recenteredResizedSides = translateSides(resizedSides, xDiff, yDiff);
        return new KochSnowflake(this.order, newCenter, recenteredResizedSides);
    }
}
