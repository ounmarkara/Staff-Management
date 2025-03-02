import java.util.Scanner;

public class Main {

    static Funtionality repo = new Funtionality();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        repo.mockData();

        while (true) {
            repo.displayMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    repo.insertEmployee();
                    break;
                case "2":
                    repo.updateEmployee();
                    break;
                case "3":
                    repo.displayEmployees();
                    break;
                case "4":
                    repo.removeEmployee();
                    break;
                case "5":
                    System.out.println("* Good bye! * 😭😭");
                    return;
                default:
                    System.out.println("invalid input");
            }
        }
    }
}