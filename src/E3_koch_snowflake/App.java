package E3_koch_snowflake;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Scanner;

/**
 * Class for a Koch snowflake drawer app
 */
public class App {

    public static void main(String[] args) {
        new App().start();
    }

    /**
     * Starts the Koch Snowflake drawing program
     */
    private void start() {
        int reqOrder = getOrder();
        // TODO, make side length a proportion by making og triangle have a length of 1
        Dimension jFrameDims = new Dimension(1, 1); // TODO
        KochSnowflake kochSnowflake = new KochSnowflake(reqOrder, sideLength(jFrameDims), snowflakeCenter(jFrameDims));
        new KochDrawPanel(kochSnowflake, reqOrder, jFrameDims);
        System.out.println("Please see the newly created JFrame for the snowflake!");
    }


    /**
     * Determines the side length a first order snowflake to be drawn
     *
     * @param jFrameDims Dimension object for the dimensions of the JFrame window that displays the created snowflake
     * @return int side length as described
     */
    public int sideLength(Dimension jFrameDims) {
        return (int) (jFrameDims.getHeight() * 0.7);
    }

    /**
     * Asks user which order koch snowflake they would like,
     *
     * @return snow flake size entered by user
     */
    private int getOrder() {
        String inputMsg = "Enter which order Koch Snowflake you would like\nThen press enter (may take a while):";
        System.out.println(inputMsg);
        Scanner scanner = new Scanner(System.in);
        String order = scanner.nextLine();
        while (!(KochSnowflake.orderIsValid(order))) {
            System.out.println("Inputted order is not a positive non-zero integer!, Please re-enter:");
            order = scanner.nextLine();
        }
        return Integer.parseInt(order);
    }

    /**
     * Finds the center of the snowflake to be drawn
     *
     * @param jFrameDims Dimension object for the size of the to be created JFrame to display the snowflake
     * @return Point2D object as described
     */
    private Point2D.Double snowflakeCenter(Dimension jFrameDims) {
        return new Point2D.Double(jFrameDims.getWidth()/2, jFrameDims.getHeight()/2);
    }
}
