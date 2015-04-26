package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/10/2015.
 */

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("Project")
public class ParseMangoProject extends ParseObject implements MangoProject{
    public ParseMangoProject(){
        put("admin", ParseUser.getCurrentUser());
    }

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
        ParseRelation memberList = getRelation("members");
        return null;
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

    public List<MangoTask> getTasks(){
        return getList("tasks");
    }

    public int addTask(MangoTask task){
        add ("tasks", task);
        return 0;
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

    //removes all instances of the memberID in the member list
    public int removeMember(String memberID){
        List<String> memberList = getList("members");
        boolean successful = false;
        while (memberList.contains(memberID)){
            successful = memberList.remove(memberID);
        }
        put("members", memberList);
        if (successful){
            return 0;
        }else{
            return -1;
        }
    }

    public static ParseQuery<ParseMangoProject> getQuery() {
        return ParseQuery.getQuery(ParseMangoProject.class);
    }
}
