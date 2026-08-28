public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toFileString() {
        return "T | " + getFileStatus() + " | " + description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}