package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Exit implements State {

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        Main.isOn = false;
        System.out.println("Bye!");
    }

}
