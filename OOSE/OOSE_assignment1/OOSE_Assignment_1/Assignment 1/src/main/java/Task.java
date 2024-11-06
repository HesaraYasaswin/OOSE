package edu.curtin.wbs;

import java.util.List;
// This code has the getters and setters for task

public class Task {
    private String parentId;
    private String id;
    private String description;
    private Integer effortEstimate;
    private List<Task> subtasks;
    
    // Constructor
    public Task(String parentId, String id, String description, Integer effortEstimate) {
        this.parentId = parentId;
        this.id = id;
        this.description = description;
        this.effortEstimate = effortEstimate;
    }

    // Getters and setters for all fields
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEffortEstimate() {
        return effortEstimate;
    }

    public void setEffortEstimate(Integer effortEstimate) {
        this.effortEstimate = effortEstimate;
    }
    
    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public boolean hasSubtasks() {
        return subtasks != null && !subtasks.isEmpty();
    }
    
    // Override toString() method to provide a string representation of the Task object
    @Override
    public String toString() {
        if (effortEstimate != null) {  // Convert task information to string format
            return id + ": " + description + ", effort = " + effortEstimate;
        } else {
            return id + ": " + description; // Otherwise, exclude effort estimate from the string representation
        }
    }
}

