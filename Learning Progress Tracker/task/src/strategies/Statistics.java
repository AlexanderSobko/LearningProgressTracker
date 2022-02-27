package strategies;

import model.User;
import tracker.Main;

import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics implements State {

    private static final String[] courses = new String[]{"Java", "DSA", "Databases", "Spring"};
    private static final String HEADER = "Type the name of a course to see details or 'back' to quit:";
    private static final String GENERAL_STATISTICS = "Most popular: %s\n" +
            "Least popular: %s\n" +
            "Highest activity: %s\n" +
            "Lowest activity: %s\n" +
            "Easiest course: %s\n" +
            "Hardest course: %s\n";

    private static final Function<ArrayList<User>, long[]> POP_FUNCTION = (users) -> {
            long[] course = new long[4];
            course[0] = users.stream().filter(o -> o.getJavaScore() > 0).count();
            course[1] = users.stream().filter(o -> o.getDsaScore() > 0).count();
            course[2] = users.stream().filter(o -> o.getDataBasesScore() > 0).count();
            course[3] = users.stream().filter(o -> o.getSpringScore() > 0).count();
            return course;
    };
    private static final Function<ArrayList<User>, long[]> ACTIVE_FUNCTION = (users) -> {
        long[] course = new long[4];
        course[0] = users.stream().mapToInt(User::getJavaScore).sum();
        course[1] = users.stream().mapToInt(User::getDsaScore).sum();
        course[2] = users.stream().mapToInt(User::getDataBasesScore).sum();
        course[3] = users.stream().mapToInt(User::getSpringScore).sum();
        return course;
    };
    private static final Function<ArrayList<User>, long[]> EASY_HARD_FUNCTION = (users) -> {
        long[] course = new long[4];
        course[0] = users.stream().mapToInt(User::getJavaScore).sum();
        course[1] = users.stream().mapToInt(User::getDsaScore).sum();
        course[2] = users.stream().mapToInt(User::getDataBasesScore).sum();
        course[3] = users.stream().mapToInt(User::getSpringScore).sum();
        return course;
    };
    private static final Function<ArrayList<User>, ArrayList<User>> USER_JAVA = (users) ->
        users.stream().filter(user -> user.getJavaScore() > 0).collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<User>, ArrayList<User>> USER_DSA = (users) ->
            users.stream().filter(user -> user.getDsaScore() > 0).collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<User>, ArrayList<User>> USER_DB = (users) ->
            users.stream().filter(user -> user.getDataBasesScore() > 0).collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<User>, ArrayList<User>> USER_SPRING = (users) ->
            users.stream().filter(user -> user.getSpringScore() > 0).collect(Collectors.toCollection(ArrayList::new));

    private static final Comparator<User> COMPARE_JAVA = Comparator.comparingInt(User::getJavaScore).reversed().thenComparing(User::getId);
    private static final Comparator<User> COMPARE_DSA = Comparator.comparingInt(User::getDsaScore).reversed().thenComparing(User::getId);
    private static final Comparator<User> COMPARE_DB = Comparator.comparingInt(User::getDataBasesScore).reversed().thenComparing(User::getId);
    private static final Comparator<User> COMPARE_STRING = Comparator.comparingInt(User::getSpringScore).reversed().thenComparing(User::getId);


    @Override
    public void execute(Scanner scanner, ArrayList<User> users) {
        System.out.println(HEADER);
        printGeneralStatistic(users);
        String data = scanner.nextLine().trim();

        while (!data.equals("back")) {
            switch (data) {
                case "Java":
                    calculateUserStatistic(users, USER_JAVA, COMPARE_JAVA, data);
                    break;
                case "DSA":
                    calculateUserStatistic(users, USER_DSA, COMPARE_DSA, data);
                    break;
                case "Databases":
                    calculateUserStatistic(users, USER_DB, COMPARE_DB, data);
                    break;
                case "Spring":
                    calculateUserStatistic(users, USER_SPRING, COMPARE_STRING, data);
                    break;
                default:
                    System.out.println("Unknown course.");
            }
            data = scanner.nextLine();
        }

        Main.programState = new Manu();
    }

    private void printGeneralStatistic(ArrayList<User> users) {
        String mostP = "n/a", leastP = "n/a";
        String highestAc = "n/a", lowestAc = "n/a";
        String easyCourse = "n/a", hardCourse = "n/a";

        if (!users.isEmpty()) {
            String[] pop = calculateStatistic(users, POP_FUNCTION);
            mostP = pop[0];
            leastP = pop[1];
            String[] active = calculateStatistic(users, ACTIVE_FUNCTION);
            highestAc = "Java, DSA, Databases, Spring, ";
            lowestAc = "n/a";
            String[] easyHard = calculateStatistic(users, EASY_HARD_FUNCTION);
            easyCourse = easyHard[0];
            hardCourse = easyHard[1];
        }

        System.out.printf(GENERAL_STATISTICS, mostP, leastP,
                highestAc, lowestAc, easyCourse, hardCourse);
    }

    private String[] calculateStatistic(ArrayList<User> users, Function<ArrayList<User>, long[]> function) {
        String high = "";
        String low = "";
        long[] course = function.apply(users);
        long max = Arrays.stream(course).max().getAsLong();
        long min = Arrays.stream(course).min().getAsLong();
        for (int i = 0; i < course.length; i++) {
            if (course[i] == max) {
                high += courses[i] + ", ";
            } else if (course[i] == min) {
                low += courses[i] + ", ";
            }
        }
        if (high.isEmpty()) {
            return new String[] {"n/a", low.substring(0, low.length() - 2)};
        } else if (low.isEmpty()) {
            return new String[] {high.substring(0, high.length() - 2), "n/a"};
        } else {
            return new String[] {high.substring(0, high.length() - 2), low.substring(0, low.length() - 2)};
        }
    }

    private void calculateUserStatistic(ArrayList<User> users, Function<ArrayList<User>, ArrayList<User>> function, Comparator<User> comparator, String type) {
        System.out.println(type + "\nid    points    completed");
        ArrayList<User> list = new ArrayList<>(function.apply(users));
        list.sort(comparator);
        for (User user : list) {
            int index = user.getId();
            int points = 0;
            double progress = 0.0;
            switch (type) {
                case "Java":
                    points = user.getJavaScore();
                    progress = points / 600.0 * 100;
                    break;
                case "DSA":
                    points = user.getDsaScore();
                    progress = points / 400.0 * 100 + 0.0005;
                    break;
                case "Databases":
                    points = user.getDataBasesScore();
                    progress = points / 480.0 * 100 + 0.0005;
                    break;
                case "Spring":
                    points = user.getSpringScore();
                    progress = points / 550.0 * 100 + 0.0005;
                    break;
            }
            if ( points > 99) {
                System.out.println(String.format("%d %d       %.1f", index, points, progress) + "%");
            } else if (points > 9) {
                System.out.println(String.format("%d %d        %.1f", index, points, progress) + "%");
            } else {
                System.out.println(String.format("%d %d         %.1f", index, points, progress) + "%");
            }

        }

    }


}
