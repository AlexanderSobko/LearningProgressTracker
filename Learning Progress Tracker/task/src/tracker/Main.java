package tracker;

import model.User;
import strategies.Manu;
import strategies.State;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<User> users = new ArrayList<>();
    public static boolean isOn = true;
    public static State programState;


    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);
        programState = new Manu();
        while (isOn) {
            programState.execute(scanner, users);
        }
    }

}
