package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Manu implements State {

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        String data = scanner.nextLine().trim();
        switch (data) {
            case "add students":
                Main.programState = new AddStudents();
                break;
            case "exit":
                Main.programState = new Exit();
                break;
            case "back":
                System.out.println("Enter 'exit' to exit the program.");
                break;
            case "find":
                Main.programState = new FindStudent();
                break;
            case "add points":
                Main.programState = new AddPoints();
                break;
            case "list":
                Main.programState = new List();
                break;
            case "statistics":
                Main.programState = new Statistics();
                break;
            case "notify":
                Main.programState = new Notify();
                break;
            case "":
                System.out.println("No input.");
                break;
            default:
                System.out.println("Unknown command!");
        }
    }
}
