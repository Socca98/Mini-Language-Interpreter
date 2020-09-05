package view.CLI;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu()
    {
        commands = new HashMap<>();
    }

    public void addCommand(Command c)
    {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            System.out.println(String.format("%4s : %s", com.getKey(), com.getDescription()));
        }
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input an option: ");
            Command com = commands.get(scanner.nextLine());
            if (com == null) {
                System.out.println("Invalid Option!\n");
                continue;
            }
            com.execute();
        }
    }
}
