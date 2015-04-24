package io.madd.taskmanager.utils;

import java.util.Date;

/**
 * Created by poliaf on 4/24/2015.
 */
public interface MangoTask {
    public String getTaskID();
    public String getName();
    public void setName(String name);
    public String getDescription();
    public void setDescription(String description);
    public String getCreator();
    public boolean isDone();
    public void setDone(boolean isDone);
    public boolean isDraft();
    public void setDraft(boolean isDraft);
    public void setPriority (int priority);
    public int getPriority();
    public Date getCreationDate();
    public Date getDueDate();
    public Date setDueDate(Date dueDate);
    public int getPoints();
    public void setPoints(int points);
    public Date getTTL();
    public void setTTL(Date TTL);
}
