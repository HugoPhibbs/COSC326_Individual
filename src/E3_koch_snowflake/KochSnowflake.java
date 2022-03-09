package E3_koch_snowflake;

import javax.sound.sampled.Line;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


// TODO support creating heaps of jframe windows!, ie a batch input from a user

/**
 * Program class to draw a Koch snowflake according to input from a user
 */
public class KochSnowflake {

    public static void main(String[] args) {
        KochSnowflake flake = new KochSnowflake();
        flake.start();
    }

    /**
     * Asks user which order koch snowflake they would like,
     *
     * @return snow flake size entered by user
     */
    private int getOrder() {
        String inputMsg = "Enter which order Koch Snowflake you would like:";
        System.out.println(inputMsg);
        Scanner scanner = new Scanner(System.in);
        String order = scanner.nextLine();
        while (!(orderIsValid(order))) {
            System.out.println("Inputted order is not a positive non-zero integer!, Please re-enter:");
            order = scanner.nextLine();
        }
        return Integer.parseInt(order);
    }

    /**
     * Finds the points for an order 1 snowflake.
     *
     * This is used to start the recursion
     *
     * Is centered on the origin
     *
     * @param sideLength double for the length of a triangle side
     * @param center Point2D.Double object for the centre of the Snowflake
     * @return Array of Line2D.Double objects for the starting triangle
     */
    private ArrayList<Line2D.Double> order1Snowflake(double sideLength, Point2D.Double center){
        double height = sideLength * Math.sin(Math.toRadians(60));
        Point2D a = new Point2D.Double(center.getX()-sideLength/2, center.getY()-height/2);
        Point2D b = new Point2D.Double(center.getX(), center.getY() + height/2);
        Point2D c = new Point2D.Double(center.getX() + sideLength/2, center.getY()-height/2);
        return new ArrayList<Line2D.Double>(Arrays.asList(new Line2D.Double(a, b), new Line2D.Double(b, c), new Line2D.Double(c, a)));
    }

    /**
     * The main part of this program. Does the recursion required to find all the line segments for an order of
     * Koch Snowflake
     *
     * @param currOrder int for the current order of the snowflake
     * @param reqOrder int for the required order of snowflake to be drawn
     * @param currSides Line2D.Double array containing the current sides of the snowflake
     * @return Line2D.Double ArrayList containing all of the sides of a Koch Snowflake with order reqOrder
     */
    public ArrayList<Line2D.Double> findSidesRec(int currOrder, int reqOrder, ArrayList<Line2D.Double> currSides){
        if (currOrder == reqOrder){
            return currSides;
        }
        else{
            ArrayList<Line2D.Double> newSides = new ArrayList<Line2D.Double>();
            for (Line2D.Double side : currSides) {
                ArrayList<Line2D.Double> splitSide = splitSide(side);
                newSides.addAll(splitSide);
            }
            return findSidesRec(currOrder+1, reqOrder, newSides);
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
        double xDiff = p5.getX() - p1.getX();
        double yDiff = p5.getY() - p1.getY();
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
     *
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
     * Finds out if a size inputted by a user is valid or not
     *
     * @param order String for size entered
     * @return boolean as described
     */
    private boolean orderIsValid(String order) {
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
    private boolean isInt(String str) {
        float num;
        try {
            num = Float.parseFloat(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return (num == (int) num);
    }

    /**
     * Starts the Koch Snowflake drawing program
     */
    private void start() {
        int reqOrder = getOrder();
        ArrayList<Line2D.Double> sides = findSidesRec(1, reqOrder, order1Snowflake(500, new Point2D.Double(375, 425)));
        new KochDrawPanel(sides, reqOrder);
    }
}
