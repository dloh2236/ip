import java.util.Scanner;

public abstract class Task implements Comparable<Task> {
    protected int id;
    protected String description;
    protected boolean completed;

    public static int numberOfTasks = 0;
    private static int idTracker = 0;
    public static int numberOfCompletedTasks = 0;


    public Task(String d) {
        this.id = idTracker;
        idTracker++;
        this.description = d;
        this.completed = false;
        numberOfTasks++;
    }

    public static Task addTask(String command, Scanner tokeniser) throws IllegalCommandException {
        Task newTask;
        if (!command.equals("todo") && !command.equals("deadline")
            && !command.equals("event")) {
            throw new IllegalCommandException("do that");
        } else if (!tokeniser.hasNext()) {
            throw new IllegalCommandException("process an empty task");
        }
        if (command.equals("todo")) {
            String contents = tokeniser.nextLine();
            if (contents.contains("/by")) {
                throw new IllegalCommandException("do that for a todo," +
                        "are you thinking of a deadline?");
            } else if (contents.contains("/from") || contents.contains("/to")) {
                throw new IllegalCommandException("do that for a todo," +
                        "are you thinking of an event?");
            }
            newTask = new ToDo(contents);
        } else if (command.equals("deadline")){
            String contents = tokeniser.nextLine();
            if (!contents.contains("/by")) {
                throw new IllegalCommandException("set a deadline wihtout a \"/by\"");
            } else if (contents.contains("/from") || contents.contains("/to")) {
                throw new IllegalCommandException("do that for a deadline," +
                        "are you thinking of an event?");
            }
            String[] parts = contents.split("/by", 2);
            newTask = new Deadline(parts[0], parts[1]);
        } else {
            String contents = tokeniser.nextLine();
            if (!contents.contains("/from") || !contents.contains("/to")) {
                throw new IllegalCommandException("set an event wihtout a \"/from\" and/or \"/to\"");
            } else if (contents.contains("/by")) {
                throw new IllegalCommandException("do that for an event," +
                        "are you thinking of a deadline?");
            }
            String[] message = contents.split("/from", 2);
            String[] fromto = message[1].split("/to", 2);
            newTask = new Event(message[0], fromto[0], fromto[1]);
        }
        System.out.println(TextFormat.botReply("Gotchu! noted down: \n" +
                TextFormat.indentLineBy(newTask.toString(), 2) +
                "Now you have " +
                numberOfTasks +
                " tasks in the list!"));
        return newTask;
    }

    public static void deleteTask(Task toDelete) {
        numberOfTasks--;
        if (toDelete.completed)  numberOfCompletedTasks--;
        System.out.println(TextFormat.botReply("Happily scratched this off your list:\n" +
                TextFormat.indentLineBy(toDelete.toString(), 2) +
                "Now you have " +
                numberOfTasks +
                " tasks in the list!"));
    }

    public void markDone() {
        if (this.completed) {
            System.out.println(TextFormat.botReply("That was done already...\n" +
                    "are you sure you wanted to mark that?\n"
                    + this.toString()));
        } else {
            this.completed = true;
            numberOfCompletedTasks++;
            System.out.println(TextFormat.botReply("Yay! One step closer to playing with me!\n"
                    + this.toString()));
        }
    }

    public void markNotDone() {
        if (!this.completed) {
            System.out.println(TextFormat.botReply("Don't worry it's still not done\n" +
                    "What are you doing? Let's get it done now!\n"
                    + this.toString()));
        } else {
            this.completed = false;
            numberOfCompletedTasks--;
            System.out.println(TextFormat.botReply("Oh no... what happened :(\n"
                    + this.toString()));
        }
    }


    @Override
    public abstract String toString();

    @Override
    public int compareTo(Task other) {
        return this.id - other.id;
    }
}
