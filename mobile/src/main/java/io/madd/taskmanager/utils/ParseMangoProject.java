package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/10/2015.
 */

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("Project")
public class ParseMangoProject extends ParseObject implements MangoProject{
    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getAdmin() {
        return getParseUser("admin").getObjectId();
    }

    public String[] getMembers() {
        return getRelation("members");
    }

    public int addMember(String memberID) {
        add("members", memberID);
        return 0;
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String[] getTasks(){
        return getRelation("tasks");
    }

    public void addTask(String task){
        add ("tasks", task);
    }

    public String getProjectID() {
        return super.getObjectId();
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(Object file) {
        put("photo", file);
    }

    public int removeMember(String memberID){
        remove("members");
        return 0;
    }

    public static ParseQuery<ParseMangoProject> getQuery() {
        return ParseQuery.getQuery(ParseMangoProject.class);
    }
}
