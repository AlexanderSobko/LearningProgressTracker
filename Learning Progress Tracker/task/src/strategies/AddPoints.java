package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddPoints implements State {

    private static String NOT_FOUND = "No student is found for id=%s\n";
    private static String NOTIFY_MESSAGE = "To: %s\n" +
            "Re: Your Learning Progress\n" +
            "Hello, %s %s! You have accomplished our %s course!";

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        System.out.println("Enter an id and points or 'back' to return");
        String data = scanner.nextLine().trim();
        while (!data.equals("back")) {
            add(data, users);
            data = scanner.nextLine().trim();
        }
        Main.programState = new Manu();
    }

    void add(String data, ArrayList<User> users) {
        if (data.matches("[0-9a-zA-Z]* [0-9]* [0-9]* [0-9]* [0-9]*")) {
            String[] input = data.split(" ");
            if (input[0].matches("[A-Za-z]+") || users.stream().noneMatch(user -> user.getId() == Integer.parseInt(input[0]))) {
                System.out.printf(NOT_FOUND, input[0]);
            } else {
                User user = users.stream().filter(o -> o.getId() == Integer.parseInt(input[0])).findAny().orElse(null);
                user.setJavaScore(Integer.parseInt(input[1]));
                user.setDsaScore(Integer.parseInt(input[2]));
                user.setDataBasesScore(Integer.parseInt(input[3]));
                user.setSpringScore(Integer.parseInt(input[4]));
                System.out.println("Points updated.");
                addMessage(user);
            }
        } else {
            System.out.println("Incorrect points format.");
        }
    }

    private void addMessage(User user) {
        if (!user.isJavaComp() && user.getJavaScore() >= 600) {
            addMessageHelper(user,"Java");
            user.setJavaComp(true);
        }
        if (!user.isDsaComp() && user.getDsaScore() >= 400) {
            addMessageHelper(user,"DSA");
            user.setDsaComp(true);
        }
        if (!user.isDbComp() && user.getDataBasesScore() >= 480) {
            addMessageHelper(user,"DataBase");
            user.setJavaComp(true);
        }
        if (!user.isSpringComp() && user.getSpringScore() >= 550) {
            addMessageHelper(user,"Spring");
            user.setJavaComp(true);
        }
    }

    private void addMessageHelper(User user, String type) {
        String mail = user.getMail();
        String message = String.format(NOTIFY_MESSAGE, user.getMail(), user.getFirstName(), user.getLastName(), type);

        if (Notify.notifications.containsKey(mail)) {
            Notify.notifications.get(user.getMail()).add(message);
        } else {
            Notify.notifications.put(mail,  new ArrayList<>(List.of(message)));
        }
    }

}
