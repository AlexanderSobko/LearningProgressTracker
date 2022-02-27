package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Notify implements State {

    static Map<String, ArrayList<String>> notifications = new HashMap<>();
    private static final String TOT_NOTIFY = "Total %d students have been notified";

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        for (ArrayList<String> mess : notifications.values()) {
            mess.forEach(System.out::println);
        }
        System.out.println(String.format(TOT_NOTIFY, notifications.size()));
        notifications.clear();
        Main.programState = new Manu();
    }
}
