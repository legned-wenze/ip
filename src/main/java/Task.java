public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    protected String getFileStatus() {
        return isDone ? "1" : "0";
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
