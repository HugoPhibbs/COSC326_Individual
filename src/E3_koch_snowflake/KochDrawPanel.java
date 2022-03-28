package E3_koch_snowflake;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * JPanel to draw a Koch Snowflake
 *
 * Inspired (not copied) by code from https://stackhowto.com/how-to-draw-lines-rectangles-and-circles-in-jframe/
 *       and https://stackoverflow.com/questions/7759549/java-draw-line-based-on-doubles-sub-pixel-precision
 */
public class KochDrawPanel extends JPanel {

    /**
     * Previous size of this JPanel before it was last resized
     */
    Dimension prevSize;

    /**
     * JFrame that stores this JPanel
     */
    JFrame frame;

    /**
     * The KochSnowflake that is being drawn by this class
     */
    KochSnowflake snowflake = null;

    /**
     * Constructor for a KochDrawPanel
     *
     */
    KochDrawPanel() {
        ///init();
    }

    /**
     * Adds a new snowflake onto this JPanel
     *
     * @param order int for which order snowflake should be drawn
     */
    public void addNewSnowflake(int order) {
        snowflake = new KochSnowflake(order, sideLength(), center());
        paint(getGraphics());
    }

    /**
     * Determines the side of a snowflake that should be drawn on this JPanel
     *
     * This is simply a proportion of the minimum side length
     *
     * @return double for side length as described
     */
    private double sideLength() {
        Dimension dims = getSize();
        return (Math.min(dims.getHeight(), dims.getWidth()) * 0.7);
    }

    /**
     * Resets the previous size of this JPanel to the current size of this JPanel
     */
    public void resetPrevSize() {
        prevSize = getSize();
    }

    /**
     * Sets the frame of this JPanel
     *
     * @param frame JFrame to be set.
     */
    public void setFrame(JFrame frame) {
        this.frame = frame;
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
        clearDrawing(graphics2D);
        if (snowflakeAdded()) {
            paintSnowflake(graphics2D);
        }
        else {
            super.paint(graphics);
        }
    }

    /**
     * Clears a drawing of a KochSnowflake from this Panel
     *
     * @param graphics2D Graphics2D object used to draw on this panel
     */
    private void clearDrawing(Graphics2D graphics2D) {
        super.paint(graphics2D);
    }

    /**
     * Finds out if a snowflake has been belongs to this Panel yet
     *
     * @return boolean as described
     */
    private boolean snowflakeAdded() {
        return snowflake != null;
    }

    /**
     * Paints a koch snowflake onto this JPanel
     */
    private void paintSnowflake(Graphics2D graphics2D) {
        adjustSnowflake();
        resetPrevSize();
        drawSides(graphics2D);
    }

    /**
     * Draws the sides of the snowflake belonging to this Panel onto itself
     */
    private void drawSides(Graphics2D graphics2D) {
        assert snowflakeAdded(): "A snowflake hasn't been added to this JPanel yet!";
        for (Line2D side : snowflake.getSides()) {
            graphics2D.draw(side);
        }
    }

    /**
     * Adjusts the KochSnowflake object that belongs to this class.
     *
     * This is intended to be called each time the JFrame containing this JPanel is resized.
     */
    public void adjustSnowflake() {
        assert snowflakeAdded(): "A snowflake hasn't been added to this JPanel yet!";
        double resizeCoef = resizeCoef();
        Point2D.Double newCenter = center();
        this.snowflake = this.snowflake.transform(newCenter, resizeCoef);
    }

    /**
     * Finds the center of this JPanel
     *
     * @return Point2D.Double object for the centre of this JPanel
     */
    public Point2D.Double center() {
        Dimension dims = getSize();
        return new Point2D.Double(dims.getWidth()/2, dims.getHeight()/2);
    }

    /**
     * Finds the coefficient that the snowflake should be resized by whenever the JFrame is resized
     *
     * If this JPanel has not been resized before, then it just returns 1
     *
     * I.e. what all the measurements for the snowflake should be multiplied by when the JFrame is resized
     *
     * @return double for the resize coefficient as described
     */
    public double resizeCoef() {
        Dimension currDims = getSize();
        if (prevSize == null) {
            return 1;
        }
        if (currDims.getHeight() <= currDims.getWidth()){
            return currDims.getHeight() / prevSize.getHeight();
        }
        else {
            return currDims.getWidth() / prevSize.getWidth();
        }
    }
}
