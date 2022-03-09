package E3_koch_snowflake;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * JPanel to draw a Koch Snowflake
 *
 * Inspired (not copied) by code from https://stackhowto.com/how-to-draw-lines-rectangles-and-circles-in-jframe/
 *       and https://stackoverflow.com/questions/7759549/java-draw-line-based-on-doubles-sub-pixel-precision
 */
public class KochDrawPanel extends JPanel {

    /**
     * ArrayList of Line2D.Double objects for sides of a Koch snowflake
     */
    ArrayList<Line2D.Double> sides;

    /**
     * int for the order of Koch snowflake to be drawn
     */
    int order;

    /**
     * Constructor for a Koch Snow Flake drawing panel
     *
     * @param sides ArrayList containing Line2D.Double objects to be drawn
     */
    KochDrawPanel(ArrayList<Line2D.Double> sides, int order) {
        this.sides = sides;
        this.order = order;
        initFrame();
    }

    /**
     * Initializes and fills the JFrame that is used to hold this JPanel
     *
     */
    private void initFrame() {
        JFrame frame = new JFrame(String.format("Koch Snowflake of order %s", order));
        frame.setSize(750, 750);
        frame.setVisible(true);
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Overrides the paint() method in JPanel.
     *
     * Paints a Koch Snowflake instead
     *
     * @param graphics Graphics object to be drawn on
     */
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (Line2D side : sides) {
            graphics2D.draw(side);
        }
    }
}
