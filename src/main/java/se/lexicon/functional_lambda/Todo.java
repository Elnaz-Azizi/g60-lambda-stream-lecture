package se.lexicon.functional_lambda;

public class Todo {
    private String title;      // The name or description of the task
    private int priority;     // The importance level (e.g., 1-5)
    private boolean completed; // The status: true if done, false if pending


    public Todo(String title, int priority, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.completed = completed;
    }

    // --- Getters and Setters (Accessors and Mutators) ---

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    @Override
    public String toString() {
        return "Todo{" +
                "title='" + title + '\'' +
                ", priority=" + priority +
                ", completed=" + completed +
                '}';
    }
}
