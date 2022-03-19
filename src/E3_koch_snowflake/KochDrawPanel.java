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
     * Previous size of the JFrame that was used to store this JPanel
     *
     * This is the size of the JFrame when it was last resized
     */
    Dimension previousJFrameSize;

    /**
     * JFrame that stores this JPanel
     */
    JFrame frame;

    /**
     * int for the order of Koch snowflake to be drawn
     */
    int order;

    /**
     * The KochSnowflake that is being drawn by this class
     */
    KochSnowflake snowflake;

    /**
     * Constructor for a Koch Snow Flake drawing panel
     *
     * @param jFrameDims Dimension object for the dimensions of the JFrame used to store this JPanel
     * @param order int for the order of snowflake being created
     * @param snowflake KochSnowflake object that is to be drawn
     */
    KochDrawPanel(KochSnowflake snowflake, int order, Dimension jFrameDims) {
        this.snowflake = snowflake;
        this.order = order;
        this.previousJFrameSize = jFrameDims;
        initFrame(jFrameDims);
    }

    /**
     * Initializes and fills the JFrame that is used to hold this JPanel
     *
     * @param jFrameDims Dimension object for dimensions of the JFrame to be created
     */
    private void initFrame(Dimension jFrameDims) {
        frame = new JFrame(String.format("Koch Snowflake of order %s", order));
        frame.setSize(jFrameDims.width, jFrameDims.height);
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
        adjustSnowflake();
        previousJFrameSize = frame.getSize();
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
     * Finds the dimensions of the JFrame that holds this JPanel
     *
     * @return Dimension object describing the width and height of the inputted JFrame
     */
    private Dimension jFrameDimensions() {
        return new Dimension(frame.getWidth(), frame.getHeight());
    }

    /**
     * Finds the coefficient that the snowflake should be resized by whenever the JFrame is resized
     *
     * I.e. what all the measurements for the snowflake should be multiplied by when the JFrame is resized
     *
     * @return double for the resize coefficient as described
     */
    public double resizeCoef() {
        Dimension currJFrameDims = jFrameDimensions();
        if (currJFrameDims.getHeight() <= currJFrameDims.getWidth()){
            return currJFrameDims.getHeight() / previousJFrameSize.getHeight();
        }
        else {
            return currJFrameDims.getWidth() / previousJFrameSize.getWidth();
        }
    }
}
