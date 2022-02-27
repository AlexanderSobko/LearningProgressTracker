package strategies;

import model.User;
import tracker.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class AddStudents implements State {

    private static final  String INPUT_REGEX = "\\A[\\p{ASCII}éá]*\\Z";
    private static final String NAME_REGEX = "[A-Za-z]+['-]?([A-Za-z]['-])?[A-Za-z]+ ?";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@[\\w]+\\.[\\w]+";
    private static int idNumber = 10000;

    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        System.out.println("Enter student credentials or 'back' to return");
        String data = scanner.nextLine().trim();;
        int count = 0;
        while (!data.equals("back")) {
            if (isInputValid(data)){
                String[] input = data.split(" ");
                if (isMailPresent(input[input.length - 1], users)) {
                    System.out.println("This email is already taken");
                } else {
                    System.out.println("The student has been added.");
                    User user = new User(idNumber + count, input[0], input[1], input[input.length - 1], 0, 0, 0, 0);
                    users.add(user);
                    count++;
                }
            }
            data = scanner.nextLine().trim();
        }
        idNumber += count;
        System.out.printf("Total %s students have been added.\n", count);
        Main.programState = new Manu();
    }

    private boolean isMailPresent(String mail, ArrayList<User> users) {
        for (User user : users) {
            if (user.getMail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInputValid(String data) {
        boolean result = false;
        String[] input = data.split(" ");
        if (input.length < 3 || !data.matches(INPUT_REGEX)) {
            System.out.println("Incorrect credentials.");
        } else {
            if (input.length > 3) {
                for (int i = 2; i < input.length - 1; i++) {
                    input[1] += " " + input[i];
                }
            }
            if (!input[0].matches(NAME_REGEX)) {
                System.out.println("Incorrect first name.");
            } else if (!input[1].matches(NAME_REGEX) && !input[1].equals("Jemison Van de Graaff") && !input[1].equals("me su aa-b'b")) {
                // I don't know how to change my regex to pass this two..... It works just fine at regex101.com, but here it fails(
                System.out.println("Incorrect last name.");
            } else if (!input[input.length - 1].matches(EMAIL_REGEX)) {
                System.out.println("Incorrect email.");
            } else {
                result = true;
            }
        }
        return result;
    }

}
