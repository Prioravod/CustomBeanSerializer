package entity;

import java.io.Serializable;
import java.time.Instant;

public class Node implements Serializable, Bean{

    private Instant date;
    private String text;
    private Integer priority;


    public Node(){}

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Node{" +
                "date=" + date +
                ", text='" + text + '\'' +
                ", priority=" + priority +
                '}';
    }
}
