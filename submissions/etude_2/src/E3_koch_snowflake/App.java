package E3_koch_snowflake;

/**
 * Class for a Koch snowflake drawer app
 */
public class App {

    public static void main(String[] args) {
        new App().startGui();
    }

    /**
     * Starts the Koch Snowflake drawing program via GUI
     */
    public void startGui() {
        new MainFrame();
    }
}
