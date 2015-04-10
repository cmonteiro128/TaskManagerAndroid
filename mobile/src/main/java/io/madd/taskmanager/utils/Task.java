package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/10/2015.
 */

import java.util.UUID;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Task")
public class Task extends ParseObject {

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public ParseUser getAuthor() {
        return getParseUser("author");
    }

    public void setAuthor(ParseUser currentUser) {
        put("author", currentUser);
    }

    public boolean isDraft() {
        return getBoolean("isDraft");
    }

    public void setDraft(boolean isDraft) {
        put("isDraft", isDraft);
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid", uuid.toString());
    }

    public String getUuidString() {
        return getString("uuid");
    }

    public static ParseQuery<Task> getQuery() {
        return ParseQuery.getQuery(Task.class);
    }

    public boolean isDone() {
        return getBoolean("isDone");
    }

    public void setDone(boolean isDone) {
        put("isDone", isDone);
    }

    public void setImportance (int importance){
        put("importance", importance);
    }

    public int getImportance() {
        return getInt("importance");
    }
}
