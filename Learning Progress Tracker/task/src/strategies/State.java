package strategies;

import model.User;

import java.util.ArrayList;
import java.util.Scanner;

public interface State {

    void execute(Scanner scanner, ArrayList<User> users);

}
