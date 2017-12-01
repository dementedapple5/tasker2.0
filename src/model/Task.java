package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String title;
    private String attendant;
    private String comment;
    private String description;
    private int priority;
    private String creationDate;
    private boolean state;
    private boolean visible;

    public Task(String title, String attendant, String comment, String description, int priority) {
        this.title = title;
        this.attendant = attendant;
        this.comment = comment;
        this.description = description;
        this.priority = priority;
        this.creationDate = setCreationDate();
        this.state = false;
        this.visible = true;
    }

    public Task(String title, String attendant, String comment, String description, int priority, String date, boolean state, boolean visible) {
        this.title = title;
        this.attendant = attendant;
        this.comment = comment;
        this.description = description;
        this.priority = priority;
        this.creationDate = date;
        this.state = state;
        this.visible = visible;
    }

    private static String setCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return sdf.format(date);
    }


    public boolean isVisible() {
        return visible;
    }

    public String getTitle() {
        return title;
    }

    public String getAttendant() {
        return attendant;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public boolean isState() {
        return state;
    }
    
    public void setState(boolean state) {
		this.state = state;
    }

    @Override
    public String toString() {
        return "Task{" + "title=" + title + ", attendant=" + attendant + ", comment=" + comment + ", description=" + description + ", priority=" + priority + ", creationDate=" + creationDate + ", state=" + state + ", visible=" + visible + '}';
    }
    
    
    
}
