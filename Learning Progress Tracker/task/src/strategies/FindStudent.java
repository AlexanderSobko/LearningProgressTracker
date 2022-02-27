package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class FindStudent implements State {


    private static final String FIND_OUTPUT = "%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n";

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        System.out.println("Enter an id or 'back' to return");
        String data = scanner.nextLine().trim();
        while (!data.equals("back")) {
            if (data.matches("[0-9]*")) {
                int id = Integer.parseInt(data);
                if (users.stream().anyMatch(user -> user.getId() == id)) {
                    User user = users.stream().filter(o -> o.getId() == id).findAny().orElse(null);
                    System.out.printf(FIND_OUTPUT, id, user.getJavaScore(), user.getDsaScore(),
                            user.getDataBasesScore(), user.getSpringScore());
                } else {
                    System.out.printf("No student is found for id=%d\n", id);
                }
            }
            data = scanner.nextLine().trim();
        }
        Main.programState = new Manu();
    }
}
