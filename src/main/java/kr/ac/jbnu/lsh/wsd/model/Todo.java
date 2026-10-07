package kr.ac.jbnu.lsh.wsd.model;

public class Todo {

    private Long id;
    private String title;
    private boolean completed;
    private int priority;

    // JSON 요청을 받을 때 반드시 필요한 기본 생성자
    public Todo() {
        this.completed = false;
    }

    public Todo(Long id, String title, boolean completed, int priority) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}