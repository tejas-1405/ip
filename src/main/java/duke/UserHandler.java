package duke;


import java.io.IOException;
import java.time.LocalDate;

public class UserHandler {

    public static String chat(String input, TaskList taskList, Storage storage) throws DukeException {
        if (input.matches("bye")) {
            return "Press the cross on your console";


        } else if (input.toLowerCase().matches("list")) {
            // Print list
            return taskList.listTasks();


        } else if (input.toLowerCase().matches("\\bmark\\b.*")) {
            // Mark task as done
            String r =  taskList.markTask(input);

            try {
                storage.write("data/duke.txt", taskList.getList());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return r;


        } else if (input.toLowerCase().matches("\\bunmark\\b.*")) {
            // Mark the task as undone

            String r =  taskList.unmarkTask(input);
            try {
                storage.write("data/duke.txt", taskList.getList());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return r;


        } else if (input.toLowerCase().matches("\\bdeadline\\b.*")) {
            // Add deadline task to task list
            if (input.length() <= 9) {
                throw new DukeException("Empty Description");
            }

            String r = taskList.addTask(input);
            InputHandler handler = new InputHandler();
            String[] data = input.split("/");

            LocalDate deadlineDate = handler.formatDeadline(data);

            String task = data[0].substring(9);
            Deadline d = new Deadline(task, deadlineDate);

            try {
                storage.addData("data/duke.txt", d.toString() + System.lineSeparator());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return r;


        } else if (input.toLowerCase().matches("\\bevent\\b.*")) {
            // Add event task to task list
            if (input.length() <= 6) {
                throw new DukeException("Empty Description");
            }
            String r = taskList.addTask(input);
            String[] data = input.split("/");
            String task = data[0].substring(6);

            Event e = new Event(task, data[1].substring(5), data[2].substring(3));

            try {
                storage.addData("data/duke.txt", e.toString() + System.lineSeparator());
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }

            return r;


        } else if (input.toLowerCase().matches("\\btodo\\b.*")) {
            if (input.length() <= 5) {
                throw new DukeException("Empty Description");
            }
            String r = taskList.addTask(input);

            try {
                storage.addData("data/duke.txt", new Todo(input.substring(5)).toString() + System.lineSeparator());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return r;


        } else if (input.toLowerCase().matches("\\bdelete\\b.*")) {
            // Delete tasks in tasks list
            String r = taskList.deleteTask(input);
            try {
                storage.write("data/duke.txt", taskList.getList());
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }

            return r;
        }


         else if (input.toLowerCase().matches("\\bfind\\b.*")) {
            // Implement the find function through keyword
            return taskList.findTask(input);


        } else if (input.trim().isEmpty()) {
            // If by mistake user presses return or space, nothing will happen
            return "";
        }
        return "Unable to process or understand command.";
    }
    }

