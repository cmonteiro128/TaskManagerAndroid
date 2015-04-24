package io.madd.taskmanager.utils;

/**
 * Created by poliaf on 4/24/2015.
 */

import com.parse.ParseUser;

public class ParseMangoUser extends ParseUser implements MangoUser {
    public String[] getDevices(){
        getRelation("devices");
        return new String[0];
    }
    public String[] getProjectIDs(){
    }
    public String getUserID(){
        return super.getObjectId();
    }
    public int addDevice(String deviceID){
        return 0;
    }
    public int removeProject(String projectID){
        return 0;
    }
    public int removeDevice(String deviceID){
        return 0;
    }
    public int addProject(String projectID){
        return 0;
    }
}
