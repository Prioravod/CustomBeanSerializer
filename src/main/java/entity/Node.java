package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class Node implements Serializable, Beanable {

    private Instant date;
    private String text;
    private Integer priority;
    private boolean posted;


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

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    @Override
    public String toString() {
        return "Node{" +
                "date=" + date +
                ", text='" + text + '\'' +
                ", priority=" + priority +
                ", posted=" + posted +
                '}';
    }
}
