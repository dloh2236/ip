import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;  // Import the Scanner class

public class Duke {
     static String logo = "                  .-\"-.\n"
            + "                 /|6 6|\\\n"
            + " _  ._ _   _    {/(_0_)\\}\n"
            + "(_) | (/_ (_)    _/ ^ \\_\n"
            + "                (/ /^\\ \\)-'\n"
            + "                 \"\"' '\"\"\n";

    static String greet = logo + "Woof! I'm Oreo! How may I help you?\n";
    static String exit = "I will be sad to see you go! bye!\n";
    private ArrayList<Task> taskList;

    public Duke() {
        this.taskList = new ArrayList<>();
    }

    private void list() {
        if (Task.numberOfTasks == 0) {
            System.out.println(TextFormat.botReply("looks empty to me!"));
        } else {
            StringBuilder displayList = new StringBuilder();
            if (Task.numberOfTasks == Task.numberOfCompletedTasks) {
                displayList.append("Wow! You are ALL COMPLETE!!!!\n")
                        .append("TIME TO PLAY WITH MEEEEE :DDDD\n");
            } else if (Task.numberOfTasks > 10) {
                displayList.append("Seems like you have a lot of things to do...\n")
                        .append("Remember to play with me after :D\n");
            } else {
                displayList.append("Here are the things you told me to keep track of:\n");
            }
            for (int i = 0; i < Task.numberOfTasks; i++) {
                displayList.append(i + 1 + ".").append(taskList.get(i).toString());
            }
            System.out.println(TextFormat.botReply(displayList.toString()));
        }
    }

    private void changeMark(String command, Scanner tokeniser) {
        int id = Integer.parseInt(tokeniser.next());
        if (id > Task.numberOfTasks) {
            System.out.println(TextFormat.botReply("Uh-oh... this task does not exist :("));
        } else {
            if (command.equals("mark")) {
                taskList.get(id - 1).markDone();
                if (Task.numberOfTasks == Task.numberOfCompletedTasks) {
                    this.list();
                }
            } else {
                taskList.get(id - 1).markNotDone();}
        }
    }

    public void processInput() {
        while (true) {
            String command = "";
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            Scanner tokeniser = new Scanner(input);
            command = tokeniser.next();

            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                this.list();
                continue;
            } else if (command.equals("mark") || command.equals("unmark")) {
                if (tokeniser.hasNext()) {
                    changeMark(command, tokeniser);
                    continue;
                }
            } else if (command.equals("delete")) {
                if (tokeniser.hasNext()) {
                    int id = Integer.parseInt(tokeniser.next());
                    Task.deleteTask(taskList.get(id - 1));
                    taskList.remove(id - 1);
                    Collections.sort(taskList);
                    continue;
                }
            }
            try {
                Task newTask = Task.addTask(command, tokeniser);
                taskList.add(newTask);
            } catch (IllegalCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void run() throws IllegalCommandException {
        System.out.println(TextFormat.botReply(greet));  // print greet message
        this.processInput();
        System.out.println(TextFormat.botReply(exit));
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}