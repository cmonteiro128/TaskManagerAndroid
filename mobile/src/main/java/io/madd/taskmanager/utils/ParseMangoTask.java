package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/10/2015.
 */

import java.util.UUID;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Task")
public class ParseMangoTask extends ParseObject implements MangoTask {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getCreator() {
        return getParseUser("author").getObjectId();
    }

    public boolean isDraft() {
        return getBoolean("isDraft");
    }

    public void setDraft(boolean isDraft) {
        put("isDraft", isDraft);
    }

    public String getTaskID() {
        return super.getObjectId();
    }

    public static ParseQuery<ParseMangoTask> getQuery() {
        return ParseQuery.getQuery(ParseMangoTask.class);
    }

    public boolean isDone() {
        return getBoolean("isDone");
    }

    public void setDone(boolean isDone) {
        put("isDone", isDone);
    }

    public void setPriority (int priority){
        put("priority", priority);
    }

    public int getPriority() {
        return getInt("priority");
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        put ("description", description)
    }

    public int getPoints(){
        return getInt("points");
    }

    public void setPoints(int points){
        put ("points", points);
    }
}
