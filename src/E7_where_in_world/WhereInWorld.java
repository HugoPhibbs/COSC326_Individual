package E7_where_in_world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

// TODO how to handle incorrect input

public class WhereInWorld {

    ArrayList<String> directions = new ArrayList<String>(Arrays.asList("N", "E", "S", "S"));

    /**
     * Parses a String input from a user for a place on a map
     *
     * @param position String for
     * @return HashMap<String, String> Containing with keys describing a place on a map
     */
    private HashMap<String, String> parseInput(String position) {
        position = position.strip();
        String[] splitPosition = position.split(",");
        assert splitPosition.length == 2;
        String secondPart = splitPosition[1];
        String firstPart = splitPosition[0];
        return null;
    }

    /**
     * Splits the first part of a position string into its components
     *
     * @param firstPart String for the first part of the position string
     * @return String array containing the components
     */
    private String[] splitFirstPart(String firstPart) {
        String[] splitStr = firstPart.split(" ");
        if (directions.contains(splitStr[1])){
            return null;
        }
        return null;
    }

    /**
     * Checks if a String is numeric or not
     *
     * @param str String to be checked
     * @return boolean as described
     */
    private boolean isNumeric(String str){
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException ife) {
            return false;
        }
        return true;
    }

    private boolean firstPartIsValid(String firstPart) {
        String[] splitStr = firstPart.split(" ");
        if (splitStr.length == 2){
            // TODO
            return false;
        }
        if (!(splitStr.length == 1)) {
            return false;
        }
        return false;
    }


    private boolean firstPartIsValid(String[] splitFirstPart){
        return false; // TODO
    }
    /**
     * Gets the label from an inputted position string from a user.
     *
     * @param position String describing the position of a place on a map
     * @return String for the label of a position. Null if there is none
     */
    private String getLabel(String position) {
        String[] splitPosition = position.split(" ");
        String label = splitPosition[splitPosition.length-1];
        if (!directions.contains(label) & !isNumeric(label)) {
            return label;
        }
        return null;
    }

    /**
     * Finds out if a position String has a label or not
     *
     * @param position String for the position on a map
     * @return boolean as described
     */
    private boolean hasLabel(String position) {
        return (getLabel(position) != null);
    }

    /**
     * Strips the label off a position String.
     *
     * .
     *
     * @param position String for the position on a map
     * @return String for inputted position stripped of it's label
     * @throws AssertionError if the position does not have a label
     */
    private String stripLabel(String position) throws AssertionError{
        assert hasLabel(position);
        int labelLength = getLabel(position).length();
        position = position.substring(0, position.length()-labelLength-1);
        return position;
    }

    public void start() {

    }

    public void test() {
        String str = inputPosition();
        System.out.println(stripLabel(str));
    }

    /**
     * Asks user to input a position on a map, following the convention
     * of horizontal coordinates.
     *
     * @return String that was entered
     */
    private String inputPosition() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a horizontal coordinate (with an optional label):");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        WhereInWorld whereInWorld = new WhereInWorld();
        whereInWorld.test();
    }
}