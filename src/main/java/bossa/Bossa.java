package bossa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import bossa.storage.Storage;

public class Bossa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage("data/bossa.txt");
        List<Task> tasks = storage.load();

        System.out.println("Hello! I am Bossa (Level 7 version)");
        System.out.println("Type 'bye' to exit");

        while (true) {
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Your tasks have been saved!");
                break;
            } else if (input.startsWith("add ")) {
                String description = input.substring(4);
                Task t = new ToDo(description);
                tasks.add(t);
                storage.save(tasks);
                System.out.println("Added: " + t);
            } else if (input.equals("list")) {
                System.out.println("Here are your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (input.startsWith("done ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    tasks.get(index).markDone();
                    storage.save(tasks);
                    System.out.println("Marked as done: " + tasks.get(index));
                } catch (Exception e) {
                    System.out.println("Invalid index.");
                }
            } else if (input.startsWith("delete ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task removed = tasks.remove(index);
                    storage.save(tasks);
                    System.out.println("Removed: " + removed);
                } catch (Exception e) {
                    System.out.println("Invalid index.");
                }
            } else {
                System.out.println("Unknown command");
            }
        }

        sc.close();
    }
}
