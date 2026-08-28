package eva;

import java.util.ArrayList;

import eva.task.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task getTaskAt(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int taskNumber) throws EvaException {
        validateTaskNumber(taskNumber);
        return tasks.remove(taskNumber - 1);
    }

    public Task mark(int taskNumber) throws EvaException {
        validateTaskNumber(taskNumber);
        Task task = tasks.get(taskNumber - 1);
        task.markAsDone();
        return task;
    }

    public Task unmark(int taskNumber) throws EvaException {
        validateTaskNumber(taskNumber);
        Task task = tasks.get(taskNumber - 1);
        task.markAsNotDone();
        return task;
    }

    public TaskList find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.containsKeyword(keyword)) {
                matchingTasks.add(task);
            }
        }

        return new TaskList(matchingTasks);
    }

    private void validateTaskNumber(int taskNumber) throws EvaException {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new EvaException("That task number does not exist.");
        }
    }
}