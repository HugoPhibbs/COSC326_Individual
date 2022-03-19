package E3_koch_snowflake;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame that is used for this application
 */
public class MainFrame extends JFrame {

    /**
     * KochDrawPanel that is used to draw a Koch Snowflake
     */
    KochDrawPanel drawPanel;

    /**
     * Button for user to confirm their choice for which order Snowflake they would like
     */
    JButton enterOrderButton;

    /**
     * TextField for user to to enter which order snowflake they would like
     */
    JTextField enterOrderTextField;
    private JButton button1;
    private JTextField textField1;

    /**
     * Constructor for a MainFrame
     *
     */
    MainFrame() {
        super();
        init();
    }

    /**
     * Determines the size of the JFrame window that is going to be used to display the SnowFlake to a user
     *
     * @return Dimension object for the size of the JFrame as described
     */
    private Dimension dims() {
        Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
        int minDim = (int) Math.min(screenDims.getHeight(), screenDims.getWidth());
        int sideLength = (int) (minDim * 0.5);
        return new Dimension(sideLength, sideLength);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
    }


    /**
     * Initializes this JFrame
     *
     */
    private void init() {
        setSize(dims());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Creates the components that this JFrame holds
     */
    private void createComponents() {
        createPanels();
        createEnterOrderTextField();
        createEnterOrderButton();
        createResultLabel();
    }

    private void createPanels() {
        createDrawPanel();

    }

    /**
     * Creates a JPanel that holds components relating to asking a user which order snowflake they would like
     */
    private void createInputPanel() {

    }

    /**
     * Creates a JLabel that asks a user which order snowflake they would like
     */
    private void createEnterOrderLabel() {

    }

    /**
     * Creates the JPanel that is used to draw a Koch Snowflake
     */
    private void createDrawPanel() {

    }

    /**
     * Creates a label that displays a result of the user entering in which
     * order snowflake they would like
     */
    private void createResultLabel() {

    }

    /**
     * Creates the button that a user presses when they want to
     * submit which order they would like
     */
    private void createEnterOrderButton() {

    }

    /**
     * Creates a textField that a user enters which order TextField they would like
     */
    public void createEnterOrderTextField() {

    }
}
