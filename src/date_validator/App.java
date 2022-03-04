package date_validator;

import java.util.Scanner;

/**
 * Starts the date validator program
 */
public class App {

    public static void main(String[] args){
        App app = new App();
        app.start();
    }

    public void start(){
        System.out.println("Welcome to date parser, please enter a date!");
        Scanner scanner = new Scanner(System.in);
        String date = scanner.nextLine();
        DateParser dateParser = new DateParser();
        System.out.println(dateParser.parseDate(date));
    }
}
