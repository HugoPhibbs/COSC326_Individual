package E3_koch_snowflake;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame that is used for this application
 */
public class MainFrame extends JFrame {

    /**
     * Main panel to store all other components\
     */
    private final JPanel mainPanel = new JPanel();

    /**
     * Panel to hold components relating to getting an order of input from a user
     */
    private JPanel inputPanel;

    /**
     * KochDrawPanel that is used to draw a Koch Snowflake
     */
    KochDrawPanel drawPanel;

    /**
     * TextField for user to to enter which order snowflake they would like
     */
    JTextField inputOrderTextField;

    /**
     * Label to let user know if their input is correct or not, giving them feedback if needed
     */
    JLabel inputOrderResultLabel;

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

    /**
     * Initializes this JFrame
     */
    private void init() {
        setCharacteristics();
        initMainPanel();
        initDrawPanel();
        initInputPanel();
    }

    /**
     * Initializes the panel for drawing a snowflake
     */
    private void initDrawPanel() {
        this.drawPanel = new KochDrawPanel();
        drawPanel.setFrame(this);
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        drawPanel.resetPrevSize();
    }

    /**
     * Initializes the main panel that is the master component to other components
     */
    private void initMainPanel() {
        mainPanel.setLayout(new BorderLayout());
    }

    /**
     * Initializes the input order panel
     */
    private void initInputPanel() {
        this.inputPanel = new JPanel();
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        inputPanel.setBackground(Color.GREEN);
        inputPanel.setSize(50, 50);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        initInputOrderLabel();
        initInputOrderTextField();
        initInputOrderButton();
        initResultLabel();
    }

    /**
     * Initializes the input order label
     */
    private void initInputOrderLabel() {
        JLabel inputLabel = new JLabel("Input an order for a snowflake");
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(inputLabel);
    }

    /**
     * Initializes a label to let user know if their input is correct or not, giving them feedback if needed
     */
    private void initResultLabel() {
        inputOrderResultLabel = new JLabel();
        inputPanel.add(inputOrderResultLabel);
        inputOrderResultLabel.setForeground(Color.RED);
    }


    /**
     * Initializes the input order button
     */
    private void initInputOrderButton() {
        JButton inputButton = new JButton("Enter");
        inputButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(inputButton);
        inputButton.addActionListener(e -> inputOrderButtonPressed());
    }

    /**
     * Handles the pressing of button to input an order of snowflake from a user
     */
    private void inputOrderButtonPressed() {
        String order = inputOrderTextField.getText();
        if (!KochSnowflake.orderIsValid(order)) {
            invalidOrderEntered();
        }
        else {
            validOrderEntered(Integer.parseInt(order));
        }
    }

    /**
     * Handles when a valid order of snowflake has been entered by a user
     *
     * @param order int for an inputted order
     */
    private void validOrderEntered(int order) {
        inputOrderTextField.setText("");
        drawPanel.addNewSnowflake(order);
        setTitle(String.format("Koch Snowflake of order %s", order));
        inputOrderResultLabel.setText("");
    }

    /**
     * Handles when a user enters an input that is invalid
     */
    private void invalidOrderEntered() {
        inputOrderResultLabel.setText(KochSnowflake.orderIsValidRequirements());
    }

    /**
     * Initializes the input order text-field
     */
    private void initInputOrderTextField() {
        inputOrderTextField = new JTextField();
        inputOrderTextField.setMaximumSize(new Dimension(50, 50));
        inputPanel.add(inputOrderTextField);
    }

    /**
     * Sets the characteristics of this frame
     */
    private void setCharacteristics() {
        setContentPane(mainPanel);
        setSize(dims());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
