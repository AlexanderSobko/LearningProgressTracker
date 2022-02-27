package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class List implements State {

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        if (users.isEmpty()) {
            System.out.println("No students found");
        } else {
            System.out.println("Students:");
            users.forEach((user) -> System.out.println(user.getId()));
        }
        Main.programState = new Manu();
    }
}
